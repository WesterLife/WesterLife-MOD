package fr.yan36.westerlife.common.objects.entreprises;


public class CompanyBase {
    private String companyIdentifiant;
    private String name;
    private Integer creationDate;
    private String creator;

    public CompanyBase(String companyIdentifiant, String name, Integer creationDate, String creator) {
        this.companyIdentifiant = companyIdentifiant;
        this.name = name;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    public String getCompanyIdentifiant() {
        return companyIdentifiant;
    }

    public void setCompanyIdentifiant(String companyIdentifiant) {
        this.companyIdentifiant = companyIdentifiant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
