package fr.yan36.westerlife.common.objects.character;

public class Permis {

    public enum PermisType {
        PERMIS_A("A", "Permis permettant de conduire des cyclomoteurs", 700f),
        PERMIS_B("B", "Permis permettant de conduire des voitures", 1800f),
        PERMIS_C("C", "Permis permettant de conduire des poids-lourds", 2900f),
        PERMIS_D("D", "Permis permettant de conduire des véhicules affectés au transport de personnes comportant plus de 8 places assises outre le siège du conducteur", 3000f),
        PERMIS_E("E", "Permis permettant d'atteler sur un véhicule de type B, C ou D", 600f),
        PERMIS_G("G", "Permis invisible autorisant à la conduite de véhicules jugés comme essentiels afin de les protéger d'abus", 0f),;

        PermisType(String letterName, String description, Float amount) {
            this.letterName = letterName;
            this.description = description;
            this.amount = amount;
        }

        private String letterName;
        private String description;
        private Float amount;
    };

    private PermisType type;
    private int Points;
    private String obtentionDate;

}
