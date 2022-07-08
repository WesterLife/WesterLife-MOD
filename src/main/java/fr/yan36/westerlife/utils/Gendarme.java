package fr.yan36.westerlife.utils;

public class Gendarme {

    private int id;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String grade;
    private String qualification;
    private String creationDate;
    private boolean isAdmin;

    public Gendarme(int id, String login, String password, String nom, String prenom, String grade, String qualification, String creationDate, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.qualification = qualification;
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

    public String getQualification() {
        return qualification;
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

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return id + ";" + login + ";" + "password" + ";" + nom + ";" + prenom + ";" + grade + ";" + qualification + ";" + creationDate;
    }

    public static Gendarme fromString(String s) {
        String[] split = s.split(";");
        return new Gendarme(Integer.parseInt(split[0]), split[1], "password", split[3], split[4], split[5], split[6], split[7], Boolean.parseBoolean(split[8]));
    }

    public static String toStringNice(Gendarme g) {

        return "";
    }
}
