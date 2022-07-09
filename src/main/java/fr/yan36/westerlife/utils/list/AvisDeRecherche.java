package fr.yan36.westerlife.utils.list;

public class AvisDeRecherche {

    private int id;
    private String prenom;
    private String nom;
    private String motifs;
    private String description;


    public AvisDeRecherche(int id, String prenom, String nom, String motifs, String description) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.motifs = motifs;
        this.description = description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + ";" + prenom + ";" + nom + ";" + motifs + ";" + description;
    }

    public static AvisDeRecherche fromString(String s) {
        String[] split = s.split(";");

        return new AvisDeRecherche(Integer.parseInt(split[0]), split[1], split[2], split[3], split[4]);
    }
    public String toBeautifulString() {
        return "Antécédent N°" + id + " : " + prenom + " " + nom + " (" + motifs + ")" + " " + description;
    }
}
