package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.ArrayList;
import java.util.List;

public class CompanySA extends Company {

    public CompanySA(CompanyBase company, String associationNumber, List<Rank> ranks, List<String> impots, List<String> vehicles) {
        super(company.getId(), company.getSiret(), company.getName(), CompanyType.SA, company.getOwnerUuid(), company.getAccountNumber(), company.getCapital(), company.getCreationDate());
        setRanks(ranks != null ? ranks : getDefaultRanks());
        setImpots(impots);
        setVehicles(vehicles);
    }

    private static List<Rank> getDefaultRanks() {
        List<Rank> list = new ArrayList<>();
        list.add(new Rank("Directeur Général", "Représentant légal de la SA", 0f, 100));
        list.add(new Rank("Salarié", "Salarié de l'entreprise", 0f, 10));
        return list;
    }

    public CompanyBase getBaseCompany() {
        return new CompanyBase(getSiret(), getName(), 0, getOwnerUuid() != null ? getOwnerUuid().toString() : "");
    }
}
