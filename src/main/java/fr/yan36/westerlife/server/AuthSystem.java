package fr.yan36.westerlife.server;

import fr.yan36.westerlife.server.bdd.MethodesBDD;

import java.util.List;

public class AuthSystem {

    public static boolean loginGendarmerie(String login, String password) {
        MethodesBDD.getAccountGendarme();

        List<Gendarme> gendarmeList = MethodesBDD.getAccountGendarme();
        for (Gendarme gendarme : gendarmeList) {

            if (gendarme.getLogin().equals(login) && gendarme.getPassword().equals(password)) {
                int id = gendarme.getId();
                String identifiant = gendarme.getLogin();
                String nom = gendarme.getNom();
                String prenom = gendarme.getPrenom();
                String date = gendarme.getCreationDate();
                String grade = gendarme.getGrade();
                String qualification = gendarme.getQualification();
                Boolean isAdmin = gendarme.isAdmin();

                return true;
            } else {
                return false;
            }
        }


        return false;
    }

    public static boolean loginPompier(String login, String password) {
        return true;
    }
}
