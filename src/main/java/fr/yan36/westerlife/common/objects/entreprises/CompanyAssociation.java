package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.ArrayList;
import java.util.List;

public class CompanyAssociation extends Company {
    private String associationNumber;
    private String socialLocation;
    private String objective;
    private float membershipFee;

    public CompanyAssociation(CompanyBase company, String associationNumber, String socialLocation, String objective, float membershipFee) {
        super(company.getId(), company.getSiret(), company.getName(), CompanyType.ASSOCIATION, company.getOwnerUuid(), company.getAccountNumber(), company.getCapital(), company.getCreationDate());
        this.associationNumber = associationNumber;
        this.socialLocation = socialLocation;
        this.objective = objective;
        this.membershipFee = membershipFee;
        setRanks(getDefaultRanks());
    }

    private static List<Rank> getDefaultRanks() {
        List<Rank> list = new ArrayList<>();
        list.add(new Rank("Président", "Président de l'association", 0f, 100));
        list.add(new Rank("Secrétaire", "Secrétaire de l'association", 0f, 50));
        list.add(new Rank("Trésorier", "Trésorier de l'association", 0f, 50));
        list.add(new Rank("Bénévole", "Membre bénévole", 0f, 10));
        return list;
    }

    public CompanyBase getBaseCompany() {
        return new CompanyBase(getSiret(), getName(), 0, getOwnerUuid() != null ? getOwnerUuid().toString() : "");
    }

    public String getAssociationNumber() {
        return associationNumber;
    }

    public void setAssociationNumber(String associationNumber) {
        this.associationNumber = associationNumber;
    }

    public String getSocialLocation() {
        return socialLocation;
    }

    public void setSocialLocation(String socialLocation) {
        this.socialLocation = socialLocation;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public float getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(float membershipFee) {
        this.membershipFee = membershipFee;
    }
}
