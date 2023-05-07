package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.economy.BankAccount;
import fr.yan36.westerlife.common.objects.entreprises.types.BuisnessInfos;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.ArrayList;
import java.util.List;

public class CompanySAS implements ICorporation, IBuisness {
    private final CompanyBase company;
    private String associationNumber;
    private final BankAccount bankAccount;
    private List<Rank> ranks;
    private List<String> impots;
    private List<String> vehicles;
    private String siret;


    public CompanySAS(CompanyBase company, String associationNumber, List<Rank> ranks, List<String> impots, List<String> vehicles) {
        this.company = company;
        this.associationNumber = associationNumber;
        bankAccount = new BankAccount(BankAccount.Type.PROFESSIONAL, "123456789", "Yan", 1000, "123456789", "1234", "01/01/2020");
        this.ranks = ranks;
        this.impots = impots;
        this.vehicles = vehicles;
    }

    public CompanyBase getBaseCompany() {
        return company;
    }

    public String getAssociationNumber() {
        return associationNumber;
    }

    public void setAssociationNumber(String associationNumber) {
        this.associationNumber = associationNumber;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    @Override
    public Float getMinimumCapital() {
        return 1f;
    }

    @Override
    public List<Rank> getRanks() {
        List<Rank> ranks = new ArrayList<>();
        ranks.add(new Rank("Président", "Le président est le représentant légal de la société.", 1000, 1));
        ranks.add(new Rank("Salarié", "Un salarié travaille pour l'entreprise.", 1000, 2));
        return ranks;
    }

    @Override
    public List<String> getImpots() {
        return impots;
    }

    @Override
    public List<String> getVehicles() {
        return vehicles;
    }

    @Override
    public Float getCreationCost() {
        return 334f;
    }

    @Override
    public BuisnessInfos.Type getType() {
        return BuisnessInfos.Type.SAS;
    }

    @Override
    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }

    @Override
    public void setImpots(List<String> impots) {
        this.impots = impots;
    }

    @Override
    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String getSiret() {
        return siret;
    }

    @Override
    public void setSiret(String siret) {
        this.siret = siret;
    }

    @Override
    public List<String> impots() {
        return impots;
    }
}
