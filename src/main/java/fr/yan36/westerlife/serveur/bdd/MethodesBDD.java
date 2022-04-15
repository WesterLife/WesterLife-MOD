package fr.yan36.westerlife.serveur.bdd;

import net.minecraft.entity.player.EntityPlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MethodesBDD {
    static SQLUtils instance = new SQLUtils();
    public static void addplayer(EntityPlayer p, String prenom, String nom,String date,String sex){
        instance.execute("INSERT INTO `players` (`uuid`,`prenom`, `nom`, `date`, `sex`) VALUES ('"+p.getUniqueID()+"','"+prenom+"','"+nom+"','"+date+"','"+sex+"')");

    }
    public static boolean getPlayerExist(EntityPlayer p){
        boolean exists = false;
        String uuid = p.getUniqueID().toString();
        QueryResult qr = instance.getData("SELECT uuid FROM players WHERE uuid= ?", uuid);
        //System.out.println(uuid);
        try {
            //System.out.println(qr.getResultAsArray());
            //System.out.println(qr.getValue(0, 0));
            exists = p.getUniqueID().toString().equals(qr.getValue(0, 0));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return exists;

    }

    public static void setArgent(EntityPlayer p, double Argent){
        String uuid = p.getUniqueID().toString();
        instance.execute("UPDATE players SET Argent = ? WHERE uuid = ?", Argent, uuid);
    }
    public static Double getArgent(EntityPlayer p){
        String uuid = p.getUniqueID().toString();
        QueryResult qr = instance.getData("SELECT Argent FROM players WHERE uuid= ?", uuid);
        try {
            return Double.valueOf(qr.getValue(0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void createPlainte(String plaignant, String contre, String deposition){
        instance.execute("INSERT INTO `gendarmerie_plainte` (`Plaignant`,`Contre`, `Deposition`) VALUES ('"+plaignant+"','"+contre+"','"+deposition+"')");
    }

    public static String getPlainte(){
        String job = null;
        QueryResult qr = instance.getData("SELECT * FROM gendarmerie_plainte");
        job = qr.getValue(0,0);
        return job;
    }

    public static String getNom(EntityPlayer p){
        String job = null;
        QueryResult qr = instance.getData("SELECT nom FROM players WHERE uuid= ?", p.getUniqueID());
        job = qr.getValue(0,0);
        return job;
    }
    public static String getPrenom(EntityPlayer p){
        String job = null;
        QueryResult qr = instance.getData("SELECT prenom FROM players WHERE uuid= ?", p.getUniqueID());
        job = qr.getValue(0,0);
        return job;
    }
    public static String getSex(EntityPlayer p){
        String job = null;
        QueryResult qr = instance.getData("SELECT sex FROM players WHERE uuid= ?", p.getUniqueID());
        job = qr.getValue(0,0);
        return job;
    }
    public static String getDate(EntityPlayer p){
        String job = null;
        QueryResult qr = instance.getData("SELECT date FROM players WHERE uuid= ?", p.getUniqueID());
        job = qr.getValue(0,0);
        return job;
    }
}
