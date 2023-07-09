package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.economy.BankAccount;
import fr.yan36.westerlife.common.objects.entreprises.types.Employee;

import java.util.Collections;
import java.util.List;

public class CompanyPublic implements ICorporation {

    public enum Type { NATIONAL_INSTITUTION, TERRITORIAL_COMMUNITY, PUBLIC_ETABLISHMENT }
    private CompanyBase company;
    private BankAccount bankAccount;
    private List<String> vehicles;
    private List<Employee> employees;
    private String siret;



    public CompanyPublic(CompanyBase company) {
        this.company = company;
        company.setCompanyIdentifiant("123456789");
        bankAccount = new BankAccount(BankAccount.Type.PROFESSIONAL, "123456789", "Yan", 1000, "123456789", "1234", "01/01/2020");



    }

    public CompanyBase getBaseCompany() {
        return company;
    }

    @Override
    public String getSiret() {
        return null;
    }

    @Override
    public void setSiret(String siret) {
        this.siret = siret;
    }

    @Override
    public List<String> impots() {
        return Collections.singletonList("AUCUNE");
    }

}
