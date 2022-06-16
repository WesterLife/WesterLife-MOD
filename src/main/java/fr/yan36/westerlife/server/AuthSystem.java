package fr.yan36.westerlife.server;

import fr.yan36.westerlife.server.bdd.MethodesBDD;

import java.util.List;

public class AuthSystem {

    public static boolean loginGendarmerie(String login, String password) {
        List<Gendarme> gendarmeList = MethodesBDD.getAccountGendarme();
        for (Gendarme gendarme : gendarmeList) {

            System.out.println(login + " : " + password);
            System.out.println(gendarme.getLogin() + " = " + gendarme.getPassword());
            if (gendarme.getLogin().equals(login) && gendarme.getPassword().equals(password)) {

                return true;
            }
        }
        return false;
    }

    public static boolean loginPompier(String login, String password) {
        return true;
    }
}
