package fr.yan36.westerlife.server;

import java.util.Arrays;
import java.util.Base64;

public class Plainte {

    private int id;
    private String plaigant;
    private String contre;
    private String deposition;

    public Plainte(int id, String plaigant, String contre, String deposition) {
        this.id = id;
        this.plaigant = plaigant;
        this.contre = contre;
        this.deposition = deposition;
    }

    public int getId() {
        return id;
    }

    public String getPlaigant() {
        return plaigant;
    }

    public String getContre() {
        return contre;
    }

    public String getDeposition() {
        return deposition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlaigant(String plaigant) {
        this.plaigant = plaigant;
    }

    public void setContre(String contre) {
        this.contre = contre;
    }

    public void setDeposition(String deposition) {
        this.deposition = deposition;
    }

    @Override
    public String toString() {
        return id + ";" + plaigant + ";" + contre + ";" + deposition;
    }

    public static Plainte fromString(String s) {
        String[] split = s.split(";");
        return new Plainte(Integer.parseInt(split[0]), split[1], split[2], split[3]);
    }

    public static String toStringNice(Plainte p) {

        return "Plainte n°" + p.id + " : " + p.plaigant + " contre " + p.contre + " (" + p.deposition + ")";
    }

}
