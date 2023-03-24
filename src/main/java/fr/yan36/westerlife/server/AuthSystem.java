package fr.yan36.westerlife.server;

import fr.yan36.westerlife.server.bdd.MethodesBDD;
import fr.yan36.westerlife.common.utils.list.Gendarme;
import fr.yan36.westerlife.common.utils.list.Pompier;

import java.util.List;

public class AuthSystem {

    public static boolean loginGendarmerie(String login, String password) {
        List<Gendarme> gendarmeList = MethodesBDD.getAccountGendarme();
        for (Gendarme gendarme : gendarmeList) {

            if (gendarme.getLogin().equals(login) && gendarme.getPassword().equals(password)) {

                return true;
            }
        }
        return false;
    }

    public static boolean loginPompier(String login, String password) {
        List<Pompier> pompierList = MethodesBDD.getAccountPompier();
        for (Pompier pompier : pompierList) {

            if (pompier.getLogin().equals(login) && pompier.getPassword().equals(password)) {

                return true;
            }
        }
        return false;
    }
}
