package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.ArrayList;
import java.util.List;

public class CompanyPublic extends Company {

    public enum Type { NATIONAL_INSTITUTION, TERRITORIAL_COMMUNITY, PUBLIC_ETABLISHMENT }

    public CompanyPublic(CompanyBase company) {
        super(company.getId(), company.getSiret(), company.getName(), CompanyType.PUBLIQUE, company.getOwnerUuid(), company.getAccountNumber(), company.getCapital(), company.getCreationDate());
        setRanks(getDefaultRanks());
    }

    private static List<Rank> getDefaultRanks() {
        List<Rank> list = new ArrayList<>();
        list.add(new Rank("Directeur", "Directeur de l'établissement public", 0f, 100));
        list.add(new Rank("Responsable de service", "Responsable", 0f, 50));
        list.add(new Rank("Agent", "Agent de la fonction publique", 0f, 10));
        return list;
    }

    public CompanyBase getBaseCompany() {
        return new CompanyBase(getSiret(), getName(), 0, getOwnerUuid() != null ? getOwnerUuid().toString() : "");
    }
}
