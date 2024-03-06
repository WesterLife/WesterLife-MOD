package fr.gabidut76.westerlife.common.objects.character;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.gabidut76.westerlife.common.objects.IDatabaseResponse;
import fr.gabidut76.westerlife.common.objects.IDatabaseVariable;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class Character extends SerializablePacket implements INBTSerializable<NBTTagCompound> {


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
                if(Objects.equals(value.getSex(), s.toUpperCase(Locale.ROOT))) {
                    return value;
                }
            }
            return Gender.MALE;
        }
    };
    private String id;
    private UUID uuid;
    private String firstNames;
    private String lastName;
    private String nationality;
    private Gender gender;
    private String birthPlace;
    private String birthDate;
    private Permis permis;
    private BankAccount relatedBankAccount;

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("id", id);
        nbt.setString("uuid", uuid.toString());
        nbt.setString("firstNames", firstNames);
        nbt.setString("lastName", lastName);
        nbt.setString("nationality", nationality);
        nbt.setString("gender", gender.name());
        nbt.setString("birthPlace", birthPlace);
        nbt.setString("birthDate", birthDate);
        nbt.setTag("permis", permis.serializeNBT());
        if(Objects.nonNull(relatedBankAccount)) {
            nbt.setTag("relatedBankAccount", relatedBankAccount.serializeNBT());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.id = nbt.getString("id");
        this.uuid = UUID.fromString(nbt.getString("uuid"));
        this.firstNames = nbt.getString("firstNames");
        this.lastName = nbt.getString("lastName");
        this.nationality = nbt.getString("nationality");
        this.gender = Gender.valueOf(nbt.getString("gender"));
        this.birthPlace = nbt.getString("birthPlace");
        this.birthDate = nbt.getString("birthDate");
        NBTTagCompound permisNBT = nbt.getCompoundTag("permis");
        this.permis = new Permis();
        this.permis.deserializeNBT(permisNBT);
        if(!nbt.getCompoundTag("relatedBankAccount").isEmpty()) {
            NBTTagCompound bankAccountNBT = nbt.getCompoundTag("relatedBankAccount");
            this.relatedBankAccount = new BankAccount();
            this.relatedBankAccount.deserializeNBT(bankAccountNBT);
        } else {
            this.relatedBankAccount = new BankAccount();
        }
    }

    public Character(String id, UUID uuid, String firstNames, String lastName, String nationality, Gender gender, String birthPlace, String birthDate, Permis permis) {
        this(
                id,
                uuid,
                firstNames,
                lastName,
                nationality,
                gender,
                birthPlace,
                birthDate,
                permis,
                null
        );
    }

    public Character(String id, UUID uuid, String firstNames, String lastName, String nationality, Gender gender, String birthPlace, String birthDate, Permis permis, BankAccount relatedBankAccount) {
        this.id = id;
        this.uuid = uuid;
        this.firstNames = firstNames;
        this.lastName = lastName;
        this.nationality = nationality;
        this.gender = gender;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.permis = permis;
        this.relatedBankAccount = relatedBankAccount;
    }

    public Character() {
        this.id = "null";
        this.uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        this.firstNames = "null";
        this.lastName = "null";
        this.nationality = "null";
        this.gender = Gender.MALE;
        this.birthPlace = "null";
        this.birthDate = "null";
        this.permis = new Permis();
        this.relatedBankAccount = null;
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

    public Permis getPermis() {
        return permis;
    }

    public void setPermis(Permis permis) {
        this.permis = permis;
    }

    public BankAccount getRelatedBankAccount() {
        return relatedBankAccount;
    }

    public void setRelatedBankAccount(BankAccount relatedBankAccount) {
        this.relatedBankAccount = relatedBankAccount;
    } 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        System.out.println(id);
        System.out.println(uuid.toString());
        System.out.println(firstNames);
        System.out.println(lastName);
        System.out.println(nationality);
        System.out.println(gender);
        System.out.println(birthPlace);
        System.out.println(birthDate);

        return id + ";" + uuid.toString() + ";" + firstNames + ";" + lastName + ";" + nationality + ";" + gender.getSex() + ";" + birthPlace + ";" + birthDate + ";" + permis.toString() + ";" + relatedBankAccount.toString();
    }

    public static Character fromString(String s) {
        String[] split = s.split(";");

        return new Character(split[0], UUID.fromString(split[1]), split[2], split[3], split[4], Gender.getBySex(split[5]), split[6], split[7], Permis.fromString(split[8]), BankAccount.fromString(split[9]));
    }

    public List<String> getAsReadableList() {
        List<String> list = new ArrayList<>();
        list.add("§cID: §4" + id);
        list.add("§cUUID: §4" + uuid.toString());
        list.add("§cFirst names: §4" + firstNames);
        list.add("§cLast name: §4" + lastName);
        list.add("§cNationality §4" + nationality);
        list.add("§cGender §4" + gender);
        list.add("§cBirthPlace §4" + birthPlace);
        list.add("§cBirthDate §4" + birthDate);
        list.add("§cPermis §4" + permis.toString());
        if(Objects.isNull(relatedBankAccount)) {
            list.add("§cRelatedBankAccount §4null");
        } else {
            list.add("§cRelatedBankAccount §4" + relatedBankAccount.toString());
        }



        return list;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{id, uuid.toString(), firstNames, lastName, nationality, gender, birthPlace, birthDate, permis, relatedBankAccount};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        this.id = (String) objects[0];
        this.uuid = UUID.fromString((String) objects[1]);
        this.firstNames = (String) objects[2];
        this.lastName = (String) objects[3];
        this.nationality = (String) objects[4];
        this.gender = (Gender) objects[5];
        this.birthPlace = (String) objects[6];
        this.birthDate = (String) objects[7];
        this.permis = (Permis) objects[8];
        this.relatedBankAccount = (BankAccount) objects[9];
     }
}
