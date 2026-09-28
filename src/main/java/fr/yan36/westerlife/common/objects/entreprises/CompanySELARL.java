package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.ArrayList;
import java.util.List;

public class CompanySELARL extends Company {

    public CompanySELARL(CompanyBase company, String associationNumber, List<Rank> ranks, List<String> impots, List<String> vehicles) {
        super(company.getId(), company.getSiret(), company.getName(), CompanyType.SELARL, company.getOwnerUuid(), company.getAccountNumber(), company.getCapital(), company.getCreationDate());
        setRanks(ranks != null ? ranks : getDefaultRanks());
        setImpots(impots);
        setVehicles(vehicles);
    }

    private static List<Rank> getDefaultRanks() {
        List<Rank> list = new ArrayList<>();
        list.add(new Rank("Gérant Associé", "Gérant de la SELARL", 0f, 100));
        list.add(new Rank("Salarié", "Salarié de l'entreprise", 0f, 10));
        return list;
    }

    public CompanyBase getBaseCompany() {
        return new CompanyBase(getSiret(), getName(), 0, getOwnerUuid() != null ? getOwnerUuid().toString() : "");
    }
}
