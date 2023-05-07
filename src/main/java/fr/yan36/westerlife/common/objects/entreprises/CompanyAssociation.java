package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.economy.BankAccount;

public class CompanyAssociation {
    private CompanyBase company;
    private String associationNumber;
    private String socialLocation;
    private String objective;
    private float membershipFee;
    private BankAccount bankAccount;


    public CompanyAssociation(CompanyBase company, String associationNumber, String socialLocation, String objective, float membershipFee) {
        this.company = company;
        this.associationNumber = associationNumber;
        this.socialLocation = socialLocation;
        this.objective = objective;
        this.membershipFee = membershipFee;
        bankAccount = new BankAccount(BankAccount.Type.PROFESSIONAL, "123456789", "Yan", 1000, "123456789", "1234", "01/01/2020");



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

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
