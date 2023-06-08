package fr.yan36.westerlife.common.objects.character;

import fr.yan36.westerlife.common.objects.IDatabaseVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Permis implements IDatabaseVariable {

    @Override
    public String tableName() {
        return "permis";
    }

    @Override
    public List<String> getValues() {
        List<String> vars = new ArrayList<>();
        vars.add(IDatabaseVariable.ID_ROW);
        vars.add(uuid.toString());
        vars.add(serializePermisList(type));
        vars.add(obtentionDate);
        return null;
    }

    @Override
    public RowDetails getIDRow() {
        return new RowDetails("id", true, true);
    }

    public static String serializePermisList(List<PermisType> permisTypes) {
        return permisTypes.stream().map(Enum::name).collect(Collectors.joining(","));
    }

    public static List<PermisType> deserializePermisList(String permisTypes) {
        List<PermisType> permisTypeList = new ArrayList<>();
        for (String permisType : permisTypes.split(",")) {
            permisTypeList.add(PermisType.valueOf(permisType));
        }
        return permisTypeList;
    }

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

        public Float getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public String getLetterName() {
            return letterName;
        }
    };

    private UUID uuid;
    private List<PermisType> type;
    private String points;
    private String obtentionDate;

    public Permis(UUID uuid, List<PermisType> type, String points, String obtentionDate) {
        this.uuid = uuid;
        this.type = type;
        this.points = points;
        this.obtentionDate = obtentionDate;
    }
    public Permis() {
        this.uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        this.type = Collections.singletonList(PermisType.PERMIS_E);
        this.points = "0";
        this.obtentionDate = "null";

    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPoints() {
        return points;
    }



    public String getObtentionDate() {
        return obtentionDate;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }



    public void setPoints(String points) {
        this.points = points;
    }

    public void setObtentionDate(String obtentionDate) {
        this.obtentionDate = obtentionDate;
    }


    public List<PermisType> getType() {
        return type;
    }

    public void setType(List<PermisType> type) {
        this.type = type;
    }

    @Override
    public String toString() {

        return uuid.toString() + ";" + serializePermisList(type) + ";" + points + ";" + obtentionDate;
    }

    public static Permis fromString(String s) {
        String[] split = s.split(";");

        return new Permis(UUID.fromString(split[0]), deserializePermisList(split[1]), split[2], split[3]);


    }
}
