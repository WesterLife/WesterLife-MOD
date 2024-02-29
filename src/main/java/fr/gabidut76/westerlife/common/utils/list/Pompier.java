package fr.gabidut76.westerlife.common.utils.list;

public class Pompier {

    private int id;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String grade;
    private String creationDate;
    private boolean isAdmin;

    public Pompier(int id, String login, String password, String nom, String prenom, String grade, String creationDate, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.creationDate = creationDate;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getGrade() {
        return grade;
    }


    public String getCreationDate() {
        return creationDate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return id + ";" + login + ";" + nom + ";" + prenom + ";" + grade + ";" + creationDate;
    }

    public static Pompier fromString(String s) {
        String[] split = s.split(";");
        return new Pompier(Integer.parseInt(split[0]), split[1], split[2], split[3], split[4], split[5], split[6], Boolean.parseBoolean(split[7]));
    }

    public static String toStringNice(Pompier g) {

        return "";
    }
}
