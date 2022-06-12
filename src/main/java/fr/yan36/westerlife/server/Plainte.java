package fr.yan36.westerlife.server;

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
}
