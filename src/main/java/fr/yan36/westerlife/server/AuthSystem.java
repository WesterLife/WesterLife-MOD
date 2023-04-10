package fr.yan36.westerlife.server;

import fr.yan36.westerlife.server.bdd.MethodesBDD;
import fr.yan36.westerlife.common.utils.list.Gendarme;
import fr.yan36.westerlife.common.utils.list.Pompier;

import java.util.List;

public class AuthSystem {

    public static List<Gendarme> gendarmeList;
    public static List<Pompier> pompierList;

    public static void init() {
            gendarmeList = MethodesBDD.getAccountGendarme();
            pompierList = MethodesBDD.getAccountPompier();
    }

    public static boolean loginGendarmerie(String login, String password) {
        return gendarmeList.stream().anyMatch(gendarme -> gendarme.getLogin().equals(login) && gendarme.getPassword().equals(password));
    }

    public static boolean loginPompier(String login, String password) {
        return pompierList.stream().anyMatch(gendarme -> gendarme.getLogin().equals(login) && gendarme.getPassword().equals(password));
    }
}
