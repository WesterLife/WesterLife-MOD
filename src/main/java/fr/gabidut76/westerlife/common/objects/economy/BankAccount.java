package fr.gabidut76.westerlife.common.objects.economy;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class BankAccount extends SerializablePacket implements INBTSerializable<NBTTagCompound> {

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

    public BankAccount() {
        this.id = "nobankaccount";
        this.money = "0";
        this.type = BankAccountType.PERSONAL;
        this.owner = "noowner";
        this.rib = "norib";
        this.accountpassword = "noaccountpassword";
        this.creationDate = System.currentTimeMillis();

    }

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

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("id", this.id);
        nbt.setString("money", this.money);
        nbt.setString("type", this.type.name());
        nbt.setString("owner", this.owner);
        nbt.setString("rib", this.rib);
        nbt.setString("accountpassword", this.accountpassword);
        nbt.setLong("creationDate", this.creationDate);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.id = nbt.getString("id");
        this.money = nbt.getString("money");
        this.type = BankAccountType.valueOf(nbt.getString("type"));
        this.owner = nbt.getString("owner");
        this.rib = nbt.getString("rib");
        this.accountpassword = nbt.getString("accountpassword");
        this.creationDate = nbt.getLong("creationDate");

    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{id, money, type, owner, rib, accountpassword, creationDate};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        this.id = (String) objects[0];
        this.money = (String) objects[1];
        this.type = (BankAccountType) objects[2];
        this.owner = (String) objects[3];
        this.rib = (String) objects[4];
        this.accountpassword = (String) objects[5];
        this.creationDate = (long) objects[6];
    }
}
