package fr.yan36.westerlife.common.objects.entreprises.types;

public class Rank {
    private int id;
    private int companyId;
    private String name;
    private String description;
    private float salary;
    private int level;
    private boolean canHire;
    private boolean canFire;
    private boolean canWithdraw;

    public Rank(String name, String description, float salary, int level) {
        this(0, 0, name, description, salary, level, level >= 50, level >= 50, level >= 80);
    }

    public Rank(String name, int level, float salary, boolean canHire, boolean canFire, boolean canWithdraw) {
        this(0, 0, name, "", salary, level, canHire, canFire, canWithdraw);
    }

    public Rank(int id, int companyId, String name, String description, float salary, int level, boolean canHire, boolean canFire, boolean canWithdraw) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.description = description != null ? description : "";
        this.salary = salary;
        this.level = level;
        this.canHire = canHire;
        this.canFire = canFire;
        this.canWithdraw = canWithdraw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isCanHire() {
        return canHire;
    }

    public void setCanHire(boolean canHire) {
        this.canHire = canHire;
    }

    public boolean isCanFire() {
        return canFire;
    }

    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }

    public boolean isCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }
}
