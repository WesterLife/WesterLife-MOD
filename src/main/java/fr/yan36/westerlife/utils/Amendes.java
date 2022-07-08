package fr.yan36.westerlife.utils;

public class Amendes {

    private int id;
    private String prenom;
    private String nom;
    private String motifs;
    private int prix;


    public Amendes(int id, String prenom, String nom, String motifs, int prix) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.motifs = motifs;
        this.prix = prix;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotifs() {
        return motifs;
    }

    public void setMotifs(String motifs) {
        this.motifs = motifs;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return id + ";" + prenom + ";" + nom + ";" + motifs + ";" + prix;
    }

    public static Amendes fromString(String s) {
        String[] split = s.split(";");

        return new Amendes(Integer.parseInt(split[0]), split[1], split[2], split[3], Integer.parseInt(split[4]));
    }
    public String toBeautifulString() {
        return "Antécédent N°" + id + " : " + prenom + " " + nom + " (" + motifs + ")" + " " + prix;
    }
}
