package fr.yan36.westerlife.server.bdd;

import fr.yan36.westerlife.server.Gendarme;
import fr.yan36.westerlife.server.Job;
import fr.yan36.westerlife.server.Plainte;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static boolean getPlayerExistUUID(UUID uuid){
        boolean exists = false;
        QueryResult qr = instance.getData("SELECT uuid FROM players WHERE uuid= ?", uuid);
        //System.out.println(uuid);
        try {
            //System.out.println(qr.getResultAsArray());
            //System.out.println(qr.getValue(0, 0));
            exists = uuid.equals(qr.getValue(0, 0));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return exists;
    }

    public static void removePlayer(UUID uuid) {
        if(MethodesBDD.getPlayerExistUUID(uuid)){
            instance.execute("DELETE FROM players WHERE uuid= ?", uuid);

        }
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

    public static List<Plainte> getPlainte(){
        QueryResult qr = instance.getData("SELECT * FROM gendarmerie_plainte");
        List<Plainte> plaintes = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            plaintes.add(new Plainte(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3)));
        }
        return plaintes;
    }

    public static List<Gendarme> getAccountGendarme(){
        QueryResult qr = instance.getData("SELECT * FROM gendarmerie_user");
        List<Gendarme> gendarmes = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            gendarmes.add(new Gendarme(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4), qr.getValue(i, 5), qr.getValue(i, 6), qr.getValue(i, 7), Boolean.parseBoolean(qr.getValue(i, 8))));
            System.out.println("GGD"+qr.getValue(i, 8));
        }

        return gendarmes;
    }

    // jobs
    public static List<Job> getJobs(){
        QueryResult qr = instance.getData("SELECT * FROM france_travail");
        List<Job> jobsList = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            jobsList.add(new Job(
                Integer.parseInt(qr.getValue(i, 0)),
                qr.getValue(i, 1),
                qr.getValue(i, 2),
                qr.getValue(i, 3),
                Integer.parseInt(qr.getValue(i, 4)),
                Integer.parseInt(qr.getValue(i, 5)),
                Boolean.valueOf(qr.getValue(i, 6)),
                Boolean.valueOf(qr.getValue(i, 7))
            ));
        }
        return jobsList;
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
