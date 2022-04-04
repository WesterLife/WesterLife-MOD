package fr.yan36.westerlife.client;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketGetArgent;
import fr.yan36.westerlife.common.network.PacketSyncPlayer;
import net.minecraft.client.Minecraft;

public class Profil {
    private static String prenom,nom,sex,date;
    private static Double bank;

    public static String getPrenom() {
        return prenom;
    }

    public static String getNom() {
        return nom;
    }

    public static String getSex() {
        return sex;
    }

    public static String getDate() {
        return date;
    }

    public static void setPrenom(String prenom) {
        Profil.prenom = prenom;
    }

    public static void setNom(String nom) {
        Profil.nom = nom;
    }

    public static void setSex(String sex) {
        Profil.sex = sex;
    }

    public static void setDate(String date) {
        Profil.date = date;
    }


    public static Double getBank() {
        return bank;
    }

    public static void setBank(Double bank) {
        Profil.bank = bank;
    }
}

