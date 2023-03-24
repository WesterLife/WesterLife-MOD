package fr.yan36.westerlife.server.bdd;

import fr.yan36.westerlife.server.ServerProxy;
import net.minecraft.entity.player.EntityPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    // Base De Données - Set Info
    //===================================

    private static void setInfo(String table_name, String where, String where_value, String setting, String settingsvalue){
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

    private static String getStringInfo(String getting, String table_name, String where, String where_value){
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

    private static boolean getBooleanInfo(String getting, String table_name, String where, String where_value){
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

    private static int getIntInfo(String getting, String table_name, String where, String where_value){
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

    private static float getFloatInfo(String getting, String table_name, String where, String where_value){
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
