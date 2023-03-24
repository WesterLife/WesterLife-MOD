package fr.yan36.westerlife.server.bdd;

import fr.yan36.westerlife.common.utils.list.*;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MethodesBDD {

    //New methods for V2.0

    static SQLUtils instance = new SQLUtils();

    //Création d'un personnage rôle-play
    public static void createCharacter(EntityPlayer p, String familyname, String firstnames, String birthdate, String birthplace, String nationality, String sex){
        instance.execute("INSERT INTO `players` (`pseudo`,`uuid`, `familyname`, `firstnames`, `birthdate`, `birthplace`, `nationality`, `sex`) VALUES ('"+p.getDisplayNameString()+"','"+p.getUniqueID().toString()+"','"+familyname+"','"+firstnames+"','"+birthdate+"','"+birthplace+"','"+nationality+"','"+sex+"')");
    }

    //Vérifie si un personne rôle-play existe pour un joueur
    public static boolean getCharacterExists(EntityPlayer p){
        boolean exists = false;
        String uuid = p.getUniqueID().toString();
        QueryResult qr = instance.getData("SELECT uuid FROM players WHERE uuid= ?", uuid);
        try {
            exists = p.getUniqueID().toString().equals(qr.getValue(0, 0));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return exists;
    }

    //Old methods

    public static void addplayer(EntityPlayer p, String prenom, String nom,String date,String sex, String rib){
        instance.execute("INSERT INTO `players` (`uuid`,`prenom`, `nom`, `date`, `sex`, `rib`) VALUES ('"+p.getUniqueID()+"','"+prenom+"','"+nom+"','"+date+"','"+sex+"','"+rib+"')");
    }

    public static void addwarp(String name, int x, int y, int z){
        instance.execute("INSERT INTO `admin_warp` (`name`,`x`, `y`, `z`) VALUES ('"+name+"','"+x+"','"+y+"','"+z+"')");
    }

    public static boolean getRibExist(String rib){
        boolean exists = false;
        QueryResult qr = instance.getData("SELECT rib FROM players WHERE rib= ?", rib);
        //System.out.println(uuid);
        try {
            //System.out.println(qr.getResultAsArray());
            //System.out.println(qr.getValue(0, 0));
            exists = rib.toString().equals(qr.getValue(0, 0));
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

    public static void removePlainte(int id){
        instance.execute("DELETE FROM plainte WHERE id= ?", id);
    }

    public static void setArgent(EntityPlayer p, double Argent){
        String uuid = p.getUniqueID().toString();
        instance.execute("UPDATE players SET Argent = ? WHERE uuid = ?", Argent, uuid);
    }

    public static void setArgentByRIB(String rib, double montant){
        instance.execute("UPDATE players SET Argent = ? WHERE rib = ?", montant, rib);
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

    public static Double getArgentByRIB(String rib){
        QueryResult qr = instance.getData("SELECT Argent FROM players WHERE rib= ?", rib);
        try {
            return Double.valueOf(qr.getValue(0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void createPlainte(String plaignant, String contre, String deposition){
        instance.execute("INSERT INTO `gendarmerie_plainte` (`Plaignant`,`Contre`,`Deposition`) VALUES ('"+plaignant+"','"+contre+"','"+deposition+"');");
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

        }

        return gendarmes;
    }

    public static List<TAJ> getTAJ(){
        QueryResult qr = instance.getData("SELECT * FROM gendarmerie_taj");
        List<TAJ> taj = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            taj.add(new TAJ(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4)));

        }

        return taj;
    }

    public static List<Amendes> getAmendes(){
        QueryResult qr = instance.getData("SELECT * FROM gendarmerie_amendes");
        List<Amendes> amendes = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            amendes.add(new Amendes(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), Integer.parseInt(qr.getValue(i, 4))));

        }

        return amendes;
    }


    public static List<AvisDeRecherche> getAvisDeRecherche(){
        QueryResult qr = instance.getData("SELECT * FROM gendarmerie_avisderecherche");
        List<AvisDeRecherche> avisderecherche = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            avisderecherche.add(new AvisDeRecherche(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4)));
        }
        return avisderecherche;
    }
    public static List<Warp> getWarps(){
        QueryResult qr = instance.getData("SELECT * FROM admin_warps");
        List<Warp> warps = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            warps.add(new Warp(qr.getValue(i, 0), Integer.parseInt(qr.getValue(i, 1)), Integer.parseInt(qr.getValue(i, 2)), Integer.parseInt(qr.getValue(i, 3))));
        }
        return warps;
    }
    public static List<Pompier> getAccountPompier(){
        QueryResult qr = instance.getData("SELECT * FROM pompier_user");
        List<Pompier> pompiers = new ArrayList<>();
        for (int i = 0; i < qr.getRowsCount(); i++) {
            pompiers.add(new Pompier(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4), qr.getValue(i, 5), qr.getValue(i, 6), Boolean.parseBoolean(qr.getValue(i, 7))));

        }

        return pompiers;
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

    public static String getRIB(EntityPlayer p){
        String job = null;
        QueryResult qr = instance.getData("SELECT rib FROM players WHERE uuid= ?", p.getUniqueID());
        job = qr.getValue(0,0);
        return job;
    }

    public static String getCodeCB(EntityPlayer p){
        String job = null;
        QueryResult qr = instance.getData("SELECT codeCB FROM players WHERE uuid= ?", p.getUniqueID());
        job = qr.getValue(0,0);
        return job;
    }

    public static void setCodeCB(EntityPlayer p, String Code){
        String uuid = p.getUniqueID().toString();
        instance.execute("UPDATE players SET codeCB = ? WHERE uuid = ?", Code, uuid);
        System.out.println("Code CB updated for " + p.getName() + " : " + Code);
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
