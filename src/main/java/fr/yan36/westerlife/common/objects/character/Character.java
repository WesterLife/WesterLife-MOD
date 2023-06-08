package fr.yan36.westerlife.common.objects.character;

import fr.yan36.westerlife.common.objects.IDatabaseResponse;
import fr.yan36.westerlife.common.objects.IDatabaseVariable;
import fr.yan36.westerlife.common.objects.justice.Conviction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Character implements IDatabaseVariable, IDatabaseResponse {
    @Override
    public String tableName() {
        return "players";
    }

    @Override
    public List<String> getValues() {
        List<String> vars = new ArrayList<>();
        vars.add(IDatabaseVariable.ID_ROW);
        vars.add(uuid.toString());
        vars.add(firstNames);
        vars.add(lastName);
        vars.add(nationality);
        vars.add(gender.getSex());
        vars.add(birthPlace);
        vars.add(birthDate);
        return vars;
    }


    @Override
    public RowDetails getIDRow() {
        return new RowDetails("id", true, true);
    }

    @Override
    public void assingValues(List<String> list) {
        uuid = UUID.fromString(list.get(0));
        firstNames = list.get(1);
        lastName = list.get(2);
        nationality = list.get(3);
        gender = Gender.getBySex(list.get(4));
        birthPlace = list.get(5);
        birthDate = list.get(6);
    }

    public enum Gender {
        MALE("HOMME"), FEMALE("FEMME");

        private final String sex;
        Gender(String sex) {
            this.sex = sex;
        }

        public String getSex() {
            return sex;
        }
        public static Gender getBySex(String s) {
            for (Gender value : Gender.values()) {
                if(Objects.equals(value.getSex(), s)) {
                    return value;
                }
            }
            return Gender.MALE;
        }
    };
    private UUID uuid;
    private String firstNames;
    private String lastName;
    private String nationality;
    private Gender gender;
    private String birthPlace;
    private String birthDate;

    public Character(UUID uuid, String firstNames, String lastName, String nationality, Gender gender, String birthPlace, String birthDate) {
        this.uuid = uuid;
        this.firstNames = firstNames;
        this.lastName = lastName;
        this.nationality = nationality;
        this.gender = gender;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
    }

    public Character() {
        this.uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        this.firstNames = "null";
        this.lastName = "null";
        this.nationality = "null";
        this.gender = Gender.MALE;
        this.birthPlace = "null";
        this.birthDate = "null";
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String toString() {
        return uuid.toString() + ";" + firstNames + ";" + lastName + ";" + nationality + ";" + gender.getSex() + ";" + birthPlace + ";" + birthDate;
    }

    public static Character fromString(String s) {
        String[] split = s.split(";");

        return new Character(UUID.fromString(split[0]), split[1], split[2], split[3], Gender.getBySex(split[4]), split[5], split[6]);
    }
}
