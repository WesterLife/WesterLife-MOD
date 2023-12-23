package fr.yan36.westerlife.common.objects.economy;

public class BankAccount {
    public enum BankAccountType {
        PERSONAL,
        ORGANIZATION
    }
    private String id;
    private String money;
    private BankAccountType type;
    public String owner;
    private String rib;
    private String accountpassword;
    private long creationDate;

    public BankAccount(String id, String money, BankAccountType type, String owner, String rib, String accountpassword, long creationDate) {
        this.id = id;
        this.money = money;
        this.type = type;
        this.owner = owner;
        this.rib = rib;
        this.accountpassword = accountpassword;
        this.creationDate = creationDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public BankAccountType getType() {
        return type;
    }

    public void setType(BankAccountType type) {
        this.type = type;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getAccountpassword() {
        return accountpassword;
    }

    public void setAccountpassword(String accountpassword) {
        this.accountpassword = accountpassword;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public static BankAccount fromString(String s) {
        String[] split = s.split(":");
        return new BankAccount(split[0], split[1], BankAccountType.valueOf(split[2]), split[3], split[4], split[5], Long.parseLong(split[6]));
    }

    public String toString() {
        return id + ":" + money + ":" + type + ":" + owner + ":" + rib + ":" + accountpassword + ":" + creationDate;
    }
}
