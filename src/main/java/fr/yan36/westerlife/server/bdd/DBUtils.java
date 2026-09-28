package fr.yan36.westerlife.server.bdd;

import fr.yan36.westerlife.common.objects.IDatabaseResponse;
import fr.yan36.westerlife.common.objects.IDatabaseVariable;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.character.Permis;
import fr.yan36.westerlife.server.ServerProxy;
import net.minecraft.entity.player.EntityPlayer;
import scala.Char;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBUtils {

    //===================================
    // Base De Données - Manager Player
    //===================================

    public static void initDatabaseSchema() {
        try {
            if (ServerProxy.getDatabaseManager() == null || ServerProxy.getDatabaseManager().getWesterLifeDB() == null) {
                return;
            }
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            if (connection == null) return;

            java.sql.DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getColumns(null, null, "players", "account_uuid");
            if (!rs.next()) {
                java.sql.Statement stmt = connection.createStatement();
                try {
                    stmt.executeUpdate("ALTER TABLE `players` ADD COLUMN `account_uuid` VARCHAR(36) NULL AFTER `uuid`");
                    System.out.println("[WesterLife] Added 'account_uuid' column to table 'players'.");
                } catch (Exception ex) {
                    System.out.println("[WesterLife] Note when adding account_uuid: " + ex.getMessage());
                }
                stmt.close();
            }
            rs.close();

            java.sql.Statement stmt = connection.createStatement();
            try {
                stmt.executeUpdate("UPDATE `players` SET `account_uuid` = `uuid` WHERE `account_uuid` IS NULL OR `account_uuid` = ''");
            } catch (Exception ex) {
                System.out.println("[WesterLife] Note when updating account_uuid: " + ex.getMessage());
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println("[WesterLife] Database schema initialization warning: " + e.getMessage());
        }
    }

    public static Character createCharacter(EntityPlayer p, String familyname, String firstnames, String birthdate, String birthplace, String nationality, String sex){
        return createCharacter(p.getUniqueID(), p.getName(), familyname, firstnames, birthdate, birthplace, nationality, sex);
    }

    public static Character createCharacter(UUID accountUuid, String pseudo, String familyname, String firstnames, String birthdate, String birthplace, String nationality, String sex){
        UUID charUuid = UUID.randomUUID();
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement("INSERT INTO `players` (`uuid`, `account_uuid`, `pseudo`, `familyname`, `firstnames`, `birthdate`, `birthplace`, `nationality`, `sex`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, charUuid.toString());
                ps.setString(2, accountUuid.toString());
                ps.setString(3, pseudo);
                ps.setString(4, familyname);
                ps.setString(5, firstnames);
                ps.setString(6, birthdate);
                ps.setString(7, birthplace);
                ps.setString(8, nationality);
                ps.setString(9, sex);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                // Fallback if account_uuid does not exist yet
                ps = connection.prepareStatement("INSERT INTO `players` (`uuid`, `pseudo`, `familyname`, `firstnames`, `birthdate`, `birthplace`, `nationality`, `sex`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, charUuid.toString());
                ps.setString(2, pseudo);
                ps.setString(3, familyname);
                ps.setString(4, firstnames);
                ps.setString(5, birthdate);
                ps.setString(6, birthplace);
                ps.setString(7, nationality);
                ps.setString(8, sex);
                ps.executeUpdate();
                ps.close();
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new Character(charUuid, accountUuid, firstnames, familyname, nationality, Character.Gender.getBySex(sex), birthplace, birthdate);
    }

    public static void createBankAccount(String owner, int account_number, String cb_code, String date, boolean isPersonnal){
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `bank_account` (`account_number`,`owner`, `RIB`, `cb_code`, `creation_date`) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, account_number + "F");
            preparedStatement.setString(2, owner);
            if(isPersonnal){
                preparedStatement.setString(3, "FR769770000001" + account_number + "F10");
            } else {
                preparedStatement.setString(3, "FR769770000001" + account_number + "F11");
            }
            preparedStatement.setString(4, cb_code);
            preparedStatement.setString(5, date);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Character> getCharactersByAccount(UUID accountUuid) {
        List<Character> list = new ArrayList<>();
        if (accountUuid == null) return list;
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `players` WHERE `account_uuid` = ? OR (`account_uuid` IS NULL AND `uuid` = ?)");
            ps.setString(1, accountUuid.toString());
            ps.setString(2, accountUuid.toString());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Character c = parseCharacterFromResultSet(rs, accountUuid);
                if (c != null) {
                    list.add(c);
                }
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Character getActiveCharacter(EntityPlayer player) {
        return fr.yan36.westerlife.server.character.PlayerCharacterManager.getActiveCharacter(player);
    }

    public static boolean getCharacterExists(EntityPlayer p){
        List<Character> chars = getCharactersByAccount(p.getUniqueID());
        return !chars.isEmpty();
    }

    //===================================
    // Base De Données - Remove Data
    //===================================

    public static void removeRow(String table_name, String where, String where_value){
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `" + table_name + "` WHERE `"+ where + "`= ?");
            preparedStatement.setString(1, where_value);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //===================================
    // Base De Données - Set Info
    //===================================

    public static void setInfo(String table_name, String where, String where_value, String setting, String settingsvalue){
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + table_name + " SET " + setting + "='" + settingsvalue + "' WHERE " + where + "= ?");
            preparedStatement.setString(1, where_value);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //===================================
    // Base De Données - Get Info
    //===================================

    public static ArrayList getMultipleInfos(String table_name, String column){
        ArrayList<String> result = new ArrayList<>();
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + column + " FROM " + table_name);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()){
                result.add(rs.getString(1));
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public static int getMaxIntOfColumn(String table_name, String column){
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(" + column +") FROM " + table_name);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                int result = rs.getInt(1);
                connection.close();
                return result;
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static String getStringInfo(String getting, String table_name, String where, String where_value){
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM " + table_name + " WHERE " + where + "=?");

            preparedStatement.setString(1, where_value);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                String result = rs.getString(getting);
                connection.close();
                return result;
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean getBooleanInfo(String getting, String table_name, String where, String where_value){
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM " + table_name + " WHERE " + where + "=?");

            preparedStatement.setString(1, where_value);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                boolean result = rs.getBoolean(getting);
                connection.close();
                return result;
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static int getIntInfo(String getting, String table_name, String where, String where_value){
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM " + table_name + " WHERE " + where + "=?");

            preparedStatement.setString(1, where_value);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                int result = rs.getInt(getting);
                connection.close();
                return result;
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static float getFloatInfo(String getting, String table_name, String where, String where_value){
        try {
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM " + table_name + " WHERE " + where + "=?");

            preparedStatement.setString(1, where_value);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                float result = rs.getFloat(getting);
                connection.close();
                return result;
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            return 0F;
        }
        return 0F;
    }

    // Bande de nulos

    public static void updateToDb(String table, String column, String where, String replaceBy) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveToDB(IDatabaseVariable dbv){
        List<String> values = dbv.getValues();
        StringBuilder litteralValues = new StringBuilder();
        for (String value : values){
            if (values.indexOf(value) == values.size() - 1){
                litteralValues.append("?");
                System.out.println("LAST");
            } else {
                litteralValues.append("?,");
            }
        }


        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `" + dbv.tableName() + "` VALUES (" + litteralValues + ")");

            int i = 1;
            for (String value : values) {
                if(Objects.equals(value, IDatabaseVariable.ID_ROW) && dbv.getIDRow().isDoesIDRowExist()) {
                    System.out.println("ID ROW DOES EXIST");
                    preparedStatement.setInt(i, 0);
                } else {
                    preparedStatement.setString(i, value);
                }

                i++;
            }
            System.out.println(preparedStatement.toString());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e){

            e.printStackTrace();
        }
    }

    public static boolean isRowExistInDatabase(String table, String column, String value) {
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + "=?");
            preparedStatement.setString(1, value);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                connection.close();
                return true;
            } else {
                connection.close();
                return false;
            }

        } catch (SQLException e){
            System.out.println("Error while checking if row exist in database");
            e.printStackTrace();
            return false;
        }
    }

    public static Character getCharacter(UUID uuid) {
        if (uuid == null) return null;
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid`=? OR `account_uuid`=? LIMIT 1");
            preparedStatement.setString(1, String.valueOf(uuid));
            preparedStatement.setString(2, String.valueOf(uuid));
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                Character character = parseCharacterFromResultSet(rs, uuid);
                connection.close();
                return character;
            } else {
                connection.close();
                Character character = new Character();
                character.setUuid(uuid);
                character.setBirthDate("error");
                character.setBirthPlace("error");
                character.setGender(Character.Gender.MALE);
                character.setNationality("error");
                character.setLastName("Vous n'êtes pas enregistré dans la base de données. Contactez un administrateur.");
                character.setFirstNames("error");
                return character;
            }

        } catch (SQLException e){
            System.out.println("Error while checking if row exist in database");
            e.printStackTrace();
            return null;
        }
    }

    public static Character parseCharacterFromResultSet(ResultSet rs, UUID defaultAccountUuid) {
        try {
            UUID charUuid;
            try {
                charUuid = UUID.fromString(rs.getString("uuid"));
            } catch (Exception ex) {
                charUuid = UUID.randomUUID();
            }

            UUID accUuid = defaultAccountUuid;
            try {
                String accStr = rs.getString("account_uuid");
                if (accStr != null && !accStr.isEmpty()) {
                    accUuid = UUID.fromString(accStr);
                }
            } catch (SQLException ignored) {
            }
            if (accUuid == null) {
                accUuid = charUuid;
            }

            String birthdate = getSafeColumnString(rs, "birthdate", null, "01-01-2000");
            String birthplace = getSafeColumnString(rs, "birthplace", null, "Paris");
            String nationality = getSafeColumnString(rs, "nationality", null, "Française");
            String sex = getSafeColumnString(rs, "sex", "sexe", "HOMME");
            String lastName = getSafeColumnString(rs, "familyname", "lastname", "Inconnu");
            String firstName = getSafeColumnString(rs, "firstnames", "firstname", "Inconnu");

            return new Character(charUuid, accUuid, firstName, lastName, nationality, Character.Gender.getBySex(sex), birthplace, birthdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getSafeColumnString(ResultSet rs, String primary, String fallback, String defaultVal) {
        try {
            String val = rs.getString(primary);
            if (val != null) return val;
        } catch (SQLException ignored) {}
        if (fallback != null) {
            try {
                String val = rs.getString(fallback);
                if (val != null) return val;
            } catch (SQLException ignored) {}
        }
        return defaultVal;
    }

    public static Permis getPermis(UUID uuid) {
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `permis` WHERE UUID=?");
            preparedStatement.setString(1, String.valueOf(uuid));
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                Permis character = new Permis(UUID.fromString(rs.getString("uuid")), Permis.deserializePermisList(rs.getString("type")), rs.getString("points"), rs.getString("date"));
                connection.close();
                return character;
            } else {

                Permis character = new Permis(uuid, Collections.singletonList(Permis.PermisType.PERMIS_E), "error", "error");
                connection.close();
                return character;

            }

        } catch (SQLException e){
            System.out.println("Error while checking if row exist in database");
            e.printStackTrace();
            return null;
        }
    }

}
