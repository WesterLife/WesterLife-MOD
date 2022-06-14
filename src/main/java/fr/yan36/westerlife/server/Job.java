package fr.yan36.westerlife.server;

public class Job {

    private int id;
    private String name;
    private String description;
    private String title;
    private int salary;
    private int isParticular;
    private Boolean isFDLJob;
    private Boolean isGovernmentJob;

    public Job(int id, String name, String title, String description, int isParticular, int salary, Boolean isFDLJob,Boolean isGovernmentJob) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.isParticular = isParticular;
        this.isFDLJob = isFDLJob;
        this.isGovernmentJob = isGovernmentJob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getIsParticular() {
        return isParticular;
    }

    public void setIsParticular(int isParticular) {
        this.isParticular = isParticular;
    }

    public Boolean getIsFDLJob() {
        return isFDLJob;
    }

    public void setIsFDLJob(Boolean isFDLJob) {
        this.isFDLJob = isFDLJob;
    }

    public Boolean getIsGovernmentJob() {
        return isGovernmentJob;
    }

    public void setIsGovernmentJob(Boolean isGovernmentJob) {
        this.isGovernmentJob = isGovernmentJob;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + title + ";" + description + ";" + isParticular + ";" + salary + ";" + isFDLJob + ";" + isGovernmentJob;
    }
    public static Job fromString(String s){
        String[] split = s.split(";");
        return new Job(Integer.parseInt(split[0]), split[1], split[2], split[3], Integer.parseInt(split[4]), Integer.parseInt(split[5]), Boolean.valueOf(split[6]), Boolean.valueOf((split[7])));
    }
    public String toBeautifulString() {
        return "Métier N°" + id + " : " + name + " (" + description + ")";
    }
}
