package fr.yan36.westerlife.server.bdd;

import fr.yan36.westerlife.server.ServerProxy;
import javafx.scene.input.DataFormat;
import net.minecraft.entity.player.EntityPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DBUtils {

    //===================================
    // Base De Données - Manager Player
    //===================================

    public static void createCharacter(EntityPlayer p, String familyname, String firstnames, String birthdate, String birthplace, String nationality, String sex){
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `players` (`pseudo`,`uuid`, `familyname`, `firstnames`, `birthdate`, `birthplace`, `nationality`, `sex`) VALUES ('"+p.getDisplayNameString()+"','"+p.getUniqueID().toString()+"','"+familyname+"','"+firstnames+"','"+birthdate+"','"+birthplace+"','"+nationality+"','"+sex+"')");
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
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

    public static boolean getCharacterExists(EntityPlayer p){
        boolean exists = false;
        try{
            Connection connection = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM players WHERE uuid= ?");
            preparedStatement.setString(1, p.getUniqueID().toString());
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                exists = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return exists;
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
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public static int getMaxOfColumn(String table_name, String column){
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
}
