package fr.yan36.westerlife.common.objects.entreprises.types;

public class Rank {
    private final String name;
    private final String description;
    private final float salary;
    private final int level;

    public Rank(String name, String description, float salary, int level) {
        this.name = name;
        this.description = description;
        this.salary = salary;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getSalary() {
        return salary;
    }

    public int getLevel() {
        return level;
    }
}
