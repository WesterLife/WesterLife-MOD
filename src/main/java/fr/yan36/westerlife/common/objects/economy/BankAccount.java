package fr.yan36.westerlife.common.objects.economy;

public class BankAccount {
    public enum Type { PERSONAL, PROFESSIONAL }

    private Type type;
    private String account_number;
    private String owner;
    private Integer solde;
    private String RIB;
    private String cb_password;
    private String cration_date;

    public BankAccount(Type type, String account_number, String owner, Integer solde, String RIB, String cb_password, String cration_date) {
        this.type = type;
        this.account_number = account_number;
        this.owner = owner;
        this.solde = solde;
        this.RIB = RIB;
        this.cb_password = cb_password;
        this.cration_date = cration_date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getSolde() {
        return solde;
    }

    public void setSolde(Integer solde) {
        this.solde = solde;
    }

    public String getRIB() {
        return RIB;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public String getCbPassword() {
        return cb_password;
    }

    public void setCbPassword(String cb_password) {
        this.cb_password = cb_password;
    }

    public String getCreationDate() {
        return cration_date;
    }

    public void setCreationDate(String cration_date) {
        this.cration_date = cration_date;
    }

    public void saveToDB() {
        System.out.println("Account " + account_number + " saved to DB");
    }
}
