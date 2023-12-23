package fr.yan36.westerlife.common.objects.corporations;

import com.google.gson.Gson;
import fr.yan36.westerlife.common.objects.character.Character;

import java.util.ArrayList;
import java.util.List;

public class Corporation {

    public static enum PrimaryTypes {
        PUBLIC,PRIVATE,ASSOCIATION
    }

    public static enum SubTypes {
        PUBLIC, SAS, SARL, SELARL, SA;
    }

    public static class EmployeeType {
        String name;
        String functions;
        String primes;
        String salaire;
        int position;
        boolean hasRelativeArmor;
        boolean hasRelativeWeapon;

        List<String> relativeArmors;
        List<String> relativeWeapons;

        //TODO: BANK ACCOUNT

        public EmployeeType(String name, String functions, String primes, String salaire, int position, boolean hasRelativeArmor, boolean hasRelativeWeapon, List<String> relativeArmors, List<String> relativeWeapons) {
            this.name = name;
            this.functions = functions;
            this.primes = primes;
            this.salaire = salaire;
            this.position = position;
            this.hasRelativeArmor = hasRelativeArmor;
            this.hasRelativeWeapon = hasRelativeWeapon;
            this.relativeArmors = relativeArmors;
            this.relativeWeapons = relativeWeapons;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFunctions() {
            return functions;
        }

        public void setFunctions(String functions) {
            this.functions = functions;
        }

        public String getPrimes() {
            return primes;
        }

        public void setPrimes(String primes) {
            this.primes = primes;
        }

        public String getSalaire() {
            return salaire;
        }

        public void setSalaire(String salaire) {
            this.salaire = salaire;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isHasRelativeArmor() {
            return hasRelativeArmor;
        }

        public void setHasRelativeArmor(boolean hasRelativeArmor) {
            this.hasRelativeArmor = hasRelativeArmor;
        }

        public boolean isHasRelativeWeapon() {
            return hasRelativeWeapon;
        }

        public void setHasRelativeWeapon(boolean hasRelativeWeapon) {
            this.hasRelativeWeapon = hasRelativeWeapon;
        }

        public List<String> getRelativeArmors() {
            return relativeArmors;
        }

        public void setRelativeArmors(List<String> relativeArmors) {
            this.relativeArmors = relativeArmors;
        }

        public List<String> getRelativeWeapons() {
            return relativeWeapons;
        }

        public void setRelativeWeapons(List<String> relativeWeapons) {
            this.relativeWeapons = relativeWeapons;
        }
    }


    String id;
    String siret;
    String description;
    Character owner;
    PrimaryTypes primaryType;
    SubTypes subType;
    String name;
    List<Character> linkedCharacters;
    List<EmployeeType> employeesTypes;
    List<String> societyCars;
    float capital;
    List<String> impots;
    float initialCapital;
    String relatedBankAccount;

    public Corporation(String id, String siret, String description, Character owner, PrimaryTypes primaryType, SubTypes subType, String name, List<Character> linkedCharacters, List<EmployeeType> employeesTypes, List<String> societyCars, float capital, List<String> impots, float initialCapital, String relatedBankAccount) {
        this.id = id;
        this.siret = siret;
        this.description = description;
        this.owner = owner;
        this.primaryType = primaryType;
        this.subType = subType;
        this.name = name;
        this.linkedCharacters = linkedCharacters;
        this.employeesTypes = employeesTypes;
        this.societyCars = societyCars;
        this.capital = capital;
        this.impots = impots;
        this.initialCapital = initialCapital;
        this.relatedBankAccount = relatedBankAccount;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public List<String> getAsReadableList() {
        List<String> list = new ArrayList<>();
        list.add("§cID: §4" + id);
        list.add("§cSIRET: §4" + siret);
        list.add("§cDescription: §4" + description);
        list.add("§cOwner: §4" + owner.getLastName() + " " + owner.getFirstNames());
        list.add("§cPrimaryType: §4" + primaryType);
        list.add("§cSubType: §4" + subType);
        list.add("§cName: §4" + name);
        list.add("§cLinkedCharacters: §4" + linkedCharacters.size());
        list.add("§cEmployeesTypes: §4" + employeesTypes.size());
        list.add("§cSocietyCars: §4" + societyCars.size());
        list.add("§cCapital: §4" + capital);
        list.add("§cImpots: §4" + impots.size());
        list.add("§cInitialCapital: §4" + initialCapital);
        list.add("§cRelatedBankAccount: §4" + relatedBankAccount);
        return list;
    }


}
