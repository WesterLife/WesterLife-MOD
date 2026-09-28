package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.economy.BankAccount;
import fr.yan36.westerlife.common.objects.entreprises.types.BuisnessInfos;
import fr.yan36.westerlife.common.objects.entreprises.types.Employee;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Company implements ICorporation, IBuisness {
    private int id;
    private String siret;
    private String name;
    private CompanyType type;
    private UUID ownerUuid;
    private String accountNumber;
    private double capital;
    private String creationDate;

    private List<Rank> ranks = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<String> vehicles = new ArrayList<>();
    private List<String> impots = new ArrayList<>();

    public Company() {
        this(0, "", "Entreprise", CompanyType.SARL, UUID.randomUUID(), "0", 0.0, "01/01/2026");
    }

    public Company(int id, String siret, String name, CompanyType type, UUID ownerUuid, String accountNumber, double capital, String creationDate) {
        this.id = id;
        this.siret = siret != null ? siret : "";
        this.name = name != null ? name : "Entreprise";
        this.type = type != null ? type : CompanyType.SARL;
        this.ownerUuid = ownerUuid;
        this.accountNumber = accountNumber != null ? accountNumber : "0";
        this.capital = capital;
        this.creationDate = creationDate != null ? creationDate : "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getSiret() {
        return siret;
    }

    @Override
    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyType getCompanyType() {
        return type;
    }

    public void setCompanyType(CompanyType type) {
        this.type = type;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isOwner(UUID uuid) {
        return ownerUuid != null && ownerUuid.equals(uuid);
    }

    public boolean hasEmployee(UUID uuid) {
        return getEmployee(uuid) != null;
    }

    public Employee getEmployee(UUID uuid) {
        if (uuid == null) return null;
        for (Employee e : employees) {
            if (uuid.equals(e.getCharacterUuid())) {
                return e;
            }
        }
        return null;
    }

    public void addEmployee(Employee employee) {
        if (employee == null) return;
        employees.removeIf(e -> e.getCharacterUuid() != null && e.getCharacterUuid().equals(employee.getCharacterUuid()));
        employees.add(employee);
    }

    public void removeEmployee(UUID uuid) {
        if (uuid == null) return;
        employees.removeIf(e -> uuid.equals(e.getCharacterUuid()));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = (employees != null) ? employees : new ArrayList<>();
    }

    public Rank getRank(String rankName) {
        if (rankName == null) return null;
        for (Rank r : ranks) {
            if (r.getName().equalsIgnoreCase(rankName)) {
                return r;
            }
        }
        return null;
    }

    public Rank getRankOf(UUID uuid) {
        if (isOwner(uuid)) {
            Rank highest = getHighestRank();
            if (highest != null) return highest;
        }
        Employee emp = getEmployee(uuid);
        if (emp != null) {
            return getRank(emp.getRankName());
        }
        return null;
    }

    public boolean canManage(UUID uuid) {
        if (isOwner(uuid)) return true;
        Rank rank = getRankOf(uuid);
        return rank != null && (rank.isCanHire() || rank.isCanFire() || rank.getLevel() >= 50);
    }

    public Rank getHighestRank() {
        Rank highest = null;
        for (Rank r : ranks) {
            if (highest == null || r.getLevel() > highest.getLevel()) {
                highest = r;
            }
        }
        return highest;
    }

    public Rank getLowestRank() {
        Rank lowest = null;
        for (Rank r : ranks) {
            if (lowest == null || r.getLevel() < lowest.getLevel()) {
                lowest = r;
            }
        }
        return lowest;
    }

    public void addRank(Rank rank) {
        if (rank == null) return;
        ranks.removeIf(r -> r.getName().equalsIgnoreCase(rank.getName()));
        ranks.add(rank);
    }

    public void removeRank(String rankName) {
        if (rankName == null) return;
        ranks.removeIf(r -> r.getName().equalsIgnoreCase(rankName));
    }

    public BankAccount getBankAccount() {
        return new BankAccount(BankAccount.Type.PROFESSIONAL, accountNumber, name, (int) capital, "FR769770000001" + accountNumber + "F11", "0000", creationDate);
    }

    // IBuisness implementation
    @Override
    public Float getMinimumCapital() {
        return (type != null) ? type.getMinCapital() : 1.0f;
    }

    @Override
    public List<Rank> getRanks() {
        return ranks;
    }

    @Override
    public void setRanks(List<Rank> ranks) {
        this.ranks = (ranks != null) ? ranks : new ArrayList<>();
    }

    @Override
    public List<String> getImpots() {
        return impots;
    }

    @Override
    public void setImpots(List<String> impots) {
        this.impots = (impots != null) ? impots : new ArrayList<>();
    }

    @Override
    public List<String> getVehicles() {
        return vehicles;
    }

    @Override
    public void setVehicles(List<String> vehicles) {
        this.vehicles = (vehicles != null) ? vehicles : new ArrayList<>();
    }

    @Override
    public Float getCreationCost() {
        return 0f;
    }

    @Override
    public BuisnessInfos.Type getType() {
        if (type == null) return BuisnessInfos.Type.SARL;
        try {
            return BuisnessInfos.Type.valueOf(type.name());
        } catch (Exception e) {
            return BuisnessInfos.Type.SARL;
        }
    }

    @Override
    public List<String> impots() {
        return impots.isEmpty() ? Collections.singletonList("AUCUNE") : impots;
    }
}
