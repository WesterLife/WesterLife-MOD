package fr.yan36.westerlife.server.entreprises;

import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.entreprises.Company;
import fr.yan36.westerlife.common.objects.entreprises.CompanyType;
import fr.yan36.westerlife.common.objects.entreprises.types.Employee;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;
import fr.yan36.westerlife.server.ServerProxy;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CompanyManager {
    private static final CompanyManager INSTANCE = new CompanyManager();

    private final Map<Integer, Company> companiesById = new ConcurrentHashMap<>();
    private final Map<String, Company> companiesBySiret = new ConcurrentHashMap<>();
    private final Map<String, Company> companiesByName = new ConcurrentHashMap<>();
    private final Map<UUID, Company> employeeCompanyMap = new ConcurrentHashMap<>();

    private boolean initialized = false;

    private CompanyManager() {}

    public static CompanyManager getInstance() {
        return INSTANCE;
    }

    public synchronized void init() {
        if (initialized) return;
        DBUtils.initDatabaseSchema();
        loadAll();
        initialized = true;
    }

    public synchronized void loadAll() {
        companiesById.clear();
        companiesBySiret.clear();
        companiesByName.clear();
        employeeCompanyMap.clear();

        if (ServerProxy.getDatabaseManager() == null || ServerProxy.getDatabaseManager().getWesterLifeDB() == null) {
            System.err.println("[WesterLife] BDD non disponible, impossible de charger les entreprises.");
            return;
        }

        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn == null) return;

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM `companies`")) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String siret = rs.getString("siret");
                    String name = rs.getString("name");
                    CompanyType type = CompanyType.fromString(rs.getString("type"));
                    UUID ownerUuid;
                    try {
                        ownerUuid = UUID.fromString(rs.getString("owner_uuid"));
                    } catch (Exception ex) {
                        ownerUuid = UUID.randomUUID();
                    }
                    String accountNumber = rs.getString("account_number");
                    double capital = rs.getDouble("capital");
                    String creationDate = rs.getString("creation_date");

                    Company company = new Company(id, siret, name, type, ownerUuid, accountNumber, capital, creationDate);
                    registerCompanyInMemory(company);
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM `company_ranks`")) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int companyId = rs.getInt("company_id");
                    String name = rs.getString("name");
                    String desc = rs.getString("description");
                    int level = rs.getInt("level");
                    float salary = rs.getFloat("salary");
                    boolean canHire = rs.getBoolean("can_hire");
                    boolean canFire = rs.getBoolean("can_fire");
                    boolean canWithdraw = rs.getBoolean("can_withdraw");

                    Company comp = companiesById.get(companyId);
                    if (comp != null) {
                        Rank rank = new Rank(id, companyId, name, desc, salary, level, canHire, canFire, canWithdraw);
                        comp.addRank(rank);
                    }
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM `company_employees`")) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int companyId = rs.getInt("company_id");
                    UUID charUuid;
                    try {
                        charUuid = UUID.fromString(rs.getString("character_uuid"));
                    } catch (Exception ex) {
                        continue;
                    }
                    String rankName = rs.getString("rank_name");
                    String joinedDate = rs.getString("joined_date");

                    Company comp = companiesById.get(companyId);
                    if (comp != null) {
                        Employee emp = new Employee(id, companyId, charUuid, rankName, joinedDate, 0f);
                        comp.addEmployee(emp);
                        employeeCompanyMap.put(charUuid, comp);
                    }
                }
            }

            System.out.println("[WesterLife] " + companiesById.size() + " entreprise(s) chargée(s) depuis la base de données.");
        } catch (SQLException e) {
            System.err.println("[WesterLife] Erreur lors du chargement des entreprises : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void registerCompanyInMemory(Company company) {
        companiesById.put(company.getId(), company);
        if (company.getSiret() != null && !company.getSiret().isEmpty()) {
            companiesBySiret.put(company.getSiret().toLowerCase(), company);
        }
        if (company.getName() != null && !company.getName().isEmpty()) {
            companiesByName.put(company.getName().toLowerCase(), company);
        }
        if (company.getOwnerUuid() != null) {
            employeeCompanyMap.put(company.getOwnerUuid(), company);
        }
        for (Employee emp : company.getEmployees()) {
            if (emp.getCharacterUuid() != null) {
                employeeCompanyMap.put(emp.getCharacterUuid(), company);
            }
        }
    }

    public synchronized Company createCompany(String name, CompanyType type, UUID ownerUuid, double initialCapital) {
        if (name == null || name.trim().isEmpty() || ownerUuid == null) {
            return null;
        }
        name = name.trim();
        if (type == null) type = CompanyType.SARL;

        String siret = generateUniqueSiret();
        String accountNumberDigits = generateUniqueAccountNumber();
        String accountNumber = accountNumberDigits + "F";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        DBUtils.createBankAccount(ownerUuid.toString(), Integer.parseInt(accountNumberDigits), "0000", date, false);
        DBUtils.setAccountBalance(accountNumber, initialCapital);

        int companyId = 0;
        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn == null) return null;

            String sql = "INSERT INTO `companies` (`siret`, `name`, `type`, `owner_uuid`, `account_number`, `capital`, `creation_date`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, siret);
                ps.setString(2, name);
                ps.setString(3, type.name());
                ps.setString(4, ownerUuid.toString());
                ps.setString(5, accountNumber);
                ps.setDouble(6, initialCapital);
                ps.setString(7, date);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        companyId = keys.getInt(1);
                    }
                }
            }

            if (companyId <= 0) {
                return null;
            }

            Company company = new Company(companyId, siret, name, type, ownerUuid, accountNumber, initialCapital, date);

            List<Rank> defaultRanks = new ArrayList<>();
            defaultRanks.add(new Rank(0, companyId, "Patron", "Dirigeant de l'entreprise", 0f, 100, true, true, true));
            defaultRanks.add(new Rank(0, companyId, "Manager", "Gestionnaire d'équipe", 0f, 50, true, true, false));
            defaultRanks.add(new Rank(0, companyId, "Employé", "Salarié de l'entreprise", 0f, 10, false, false, false));
            defaultRanks.add(new Rank(0, companyId, "Stagiaire", "Stagiaire", 0f, 1, false, false, false));

            String rankSql = "INSERT INTO `company_ranks` (`company_id`, `name`, `description`, `level`, `salary`, `can_hire`, `can_fire`, `can_withdraw`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            for (Rank r : defaultRanks) {
                try (PreparedStatement ps = conn.prepareStatement(rankSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, companyId);
                    ps.setString(2, r.getName());
                    ps.setString(3, r.getDescription());
                    ps.setInt(4, r.getLevel());
                    ps.setFloat(5, r.getSalary());
                    ps.setBoolean(6, r.isCanHire());
                    ps.setBoolean(7, r.isCanFire());
                    ps.setBoolean(8, r.isCanWithdraw());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            r.setId(keys.getInt(1));
                        }
                    }
                }
                company.addRank(r);
            }

            String empSql = "INSERT INTO `company_employees` (`company_id`, `character_uuid`, `rank_name`, `joined_date`) VALUES (?, ?, ?, ?)";
            int empId = 0;
            try (PreparedStatement ps = conn.prepareStatement(empSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, companyId);
                ps.setString(2, ownerUuid.toString());
                ps.setString(3, "Patron");
                ps.setString(4, date);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        empId = keys.getInt(1);
                    }
                }
            }

            Employee ownerEmp = new Employee(empId, companyId, ownerUuid, "Patron", date, 0f);
            company.addEmployee(ownerEmp);

            registerCompanyInMemory(company);
            return company;
        } catch (SQLException e) {
            System.err.println("[WesterLife] Erreur lors de la création de l'entreprise : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public synchronized boolean deleteCompany(Company company) {
        if (company == null) return false;

        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `company_employees` WHERE `company_id` = ?")) {
                    ps.setInt(1, company.getId());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `company_ranks` WHERE `company_id` = ?")) {
                    ps.setInt(1, company.getId());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `companies` WHERE `id` = ?")) {
                    ps.setInt(1, company.getId());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `bank_account` WHERE `account_number` = ?")) {
                    ps.setString(1, company.getAccountNumber());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        companiesById.remove(company.getId());
        if (company.getSiret() != null) companiesBySiret.remove(company.getSiret().toLowerCase());
        if (company.getName() != null) companiesByName.remove(company.getName().toLowerCase());

        for (Employee emp : company.getEmployees()) {
            if (emp.getCharacterUuid() != null) {
                employeeCompanyMap.remove(emp.getCharacterUuid());
            }
        }
        if (company.getOwnerUuid() != null) {
            employeeCompanyMap.remove(company.getOwnerUuid());
        }

        return true;
    }

    public synchronized boolean hireEmployee(Company company, UUID charUuid, String rankName) {
        if (company == null || charUuid == null) return false;
        if (company.hasEmployee(charUuid)) return false;

        if (rankName == null || company.getRank(rankName) == null) {
            Rank lowest = company.getLowestRank();
            rankName = (lowest != null) ? lowest.getName() : "Employé";
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int empId = 0;
        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO `company_employees` (`company_id`, `character_uuid`, `rank_name`, `joined_date`) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, company.getId());
                    ps.setString(2, charUuid.toString());
                    ps.setString(3, rankName);
                    ps.setString(4, date);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) empId = keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        Employee emp = new Employee(empId, company.getId(), charUuid, rankName, date, 0f);
        company.addEmployee(emp);
        employeeCompanyMap.put(charUuid, company);
        return true;
    }

    public synchronized boolean fireEmployee(Company company, UUID charUuid) {
        if (company == null || charUuid == null) return false;
        if (company.isOwner(charUuid)) return false; // Cannot fire owner

        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `company_employees` WHERE `company_id` = ? AND `character_uuid` = ?")) {
                    ps.setInt(1, company.getId());
                    ps.setString(2, charUuid.toString());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        company.removeEmployee(charUuid);
        employeeCompanyMap.remove(charUuid);
        return true;
    }

    public synchronized boolean setEmployeeRank(Company company, UUID charUuid, String rankName) {
        if (company == null || charUuid == null || rankName == null) return false;
        Rank rank = company.getRank(rankName);
        if (rank == null) return false;

        Employee emp = company.getEmployee(charUuid);
        if (emp == null) return false;

        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE `company_employees` SET `rank_name` = ? WHERE `company_id` = ? AND `character_uuid` = ?")) {
                    ps.setString(1, rank.getName());
                    ps.setInt(2, company.getId());
                    ps.setString(3, charUuid.toString());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        emp.setRankName(rank.getName());
        return true;
    }

    public synchronized boolean setCompanyOwner(Company company, UUID newOwnerUuid) {
        if (company == null || newOwnerUuid == null) return false;

        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE `companies` SET `owner_uuid` = ? WHERE `id` = ?")) {
                    ps.setString(1, newOwnerUuid.toString());
                    ps.setInt(2, company.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        company.setOwnerUuid(newOwnerUuid);
        if (!company.hasEmployee(newOwnerUuid)) {
            hireEmployee(company, newOwnerUuid, "Patron");
        } else {
            setEmployeeRank(company, newOwnerUuid, "Patron");
        }
        return true;
    }

    public synchronized boolean addRank(Company company, String rankName, int level, float salary, boolean canHire, boolean canFire, boolean canWithdraw) {
        if (company == null || rankName == null || rankName.trim().isEmpty()) return false;
        rankName = rankName.trim();
        if (level < 1) level = 1;
        if (level > 99) level = 99;

        int rankId = 0;
        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO `company_ranks` (`company_id`, `name`, `description`, `level`, `salary`, `can_hire`, `can_fire`, `can_withdraw`) VALUES (?, ?, '', ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, company.getId());
                    ps.setString(2, rankName);
                    ps.setInt(3, level);
                    ps.setFloat(4, salary);
                    ps.setBoolean(5, canHire);
                    ps.setBoolean(6, canFire);
                    ps.setBoolean(7, canWithdraw);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) rankId = keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        Rank rank = new Rank(rankId, company.getId(), rankName, "", salary, level, canHire, canFire, canWithdraw);
        company.addRank(rank);
        return true;
    }

    public synchronized boolean removeRank(Company company, String rankName) {
        if (company == null || rankName == null) return false;
        if (rankName.equalsIgnoreCase("Patron")) return false;

        Rank target = company.getRank(rankName);
        if (target == null) return false;

        try (Connection conn = ServerProxy.getDatabaseManager().getWesterLifeDB().getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `company_ranks` WHERE `company_id` = ? AND `name` = ?")) {
                    ps.setInt(1, company.getId());
                    ps.setString(2, target.getName());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        company.removeRank(target.getName());
        Rank lowest = company.getLowestRank();
        String fallbackRank = (lowest != null) ? lowest.getName() : "Employé";

        for (Employee emp : company.getEmployees()) {
            if (emp.getRankName().equalsIgnoreCase(target.getName())) {
                setEmployeeRank(company, emp.getCharacterUuid(), fallbackRank);
            }
        }
        return true;
    }

    public double getCompanyBalance(Company company) {
        if (company == null) return 0.0;
        return DBUtils.getAccountBalance(company.getAccountNumber());
    }

    public void setCompanyBalance(Company company, double balance) {
        if (company == null) return;
        DBUtils.setAccountBalance(company.getAccountNumber(), balance);
    }

    public boolean deposit(Company company, UUID charUuid, UUID accountUuid, double amount) {
        if (company == null || amount <= 0) return false;
        String personalAcc = DBUtils.getPersonalBankAccount(charUuid, accountUuid);
        if (personalAcc == null) return false;

        return DBUtils.transferBankMoney(personalAcc, company.getAccountNumber(), amount);
    }

    public boolean withdraw(Company company, UUID charUuid, UUID accountUuid, double amount) {
        if (company == null || amount <= 0) return false;
        String personalAcc = DBUtils.getPersonalBankAccount(charUuid, accountUuid);
        if (personalAcc == null) return false;

        return DBUtils.transferBankMoney(company.getAccountNumber(), personalAcc, amount);
    }

    public String paySalaries(Company company, MinecraftServer server) {
        if (company == null) return "Entreprise introuvable.";

        double totalRequired = 0.0;
        Map<Employee, Float> salariesToPay = new HashMap<>();

        for (Employee emp : company.getEmployees()) {
            Rank r = company.getRank(emp.getRankName());
            if (r != null && r.getSalary() > 0) {
                salariesToPay.put(emp, r.getSalary());
                totalRequired += r.getSalary();
            }
        }

        if (salariesToPay.isEmpty()) {
            return "§eAucun salaire configuré à verser pour cette entreprise.";
        }

        double companyBalance = getCompanyBalance(company);
        if (companyBalance < totalRequired) {
            return "§cFonds insuffisants sur le compte de l'entreprise ! Solde requis : §e" + totalRequired + "€ §c(Disponible : §e" + companyBalance + "€§c)";
        }

        int paidCount = 0;
        for (Map.Entry<Employee, Float> entry : salariesToPay.entrySet()) {
            Employee emp = entry.getKey();
            float salary = entry.getValue();

            String personalAcc = DBUtils.getPersonalBankAccount(emp.getCharacterUuid(), null);
            if (personalAcc != null) {
                if (DBUtils.transferBankMoney(company.getAccountNumber(), personalAcc, salary)) {
                    paidCount++;
                    if (server != null) {
                        EntityPlayerMP p = server.getPlayerList().getPlayerByUUID(emp.getCharacterUuid());
                        if (p != null) {
                            p.sendMessage(new TextComponentString("§6[WesterLife] §aVous avez reçu votre salaire de §e" + salary + "€ §apour l'entreprise §b" + company.getName()));
                        }
                    }
                }
            }
        }

        return "§aSalaires versés avec succès à §e" + paidCount + "/" + salariesToPay.size() + " §aemployés pour un total de §e" + totalRequired + "€§a.";
    }

    public Company getCompany(int id) {
        ensureLoaded();
        return companiesById.get(id);
    }

    public Company getCompanyBySiret(String siret) {
        if (siret == null) return null;
        ensureLoaded();
        return companiesBySiret.get(siret.toLowerCase());
    }

    public Company getCompanyByName(String name) {
        if (name == null) return null;
        ensureLoaded();
        return companiesByName.get(name.toLowerCase());
    }

    public Company findCompany(String query) {
        if (query == null || query.trim().isEmpty()) return null;
        ensureLoaded();
        query = query.trim();


        try {
            int id = Integer.parseInt(query);
            Company c = companiesById.get(id);
            if (c != null) return c;
        } catch (NumberFormatException ignored) {}

        Company c = companiesBySiret.get(query.toLowerCase());
        if (c != null) return c;

        c = companiesByName.get(query.toLowerCase());
        if (c != null) return c;

        for (Company comp : companiesById.values()) {
            if (comp.getName().toLowerCase().contains(query.toLowerCase())) {
                return comp;
            }
        }

        return null;
    }

    public Company getCompanyByEmployee(UUID charUuid) {
        if (charUuid == null) return null;
        ensureLoaded();
        return employeeCompanyMap.get(charUuid);
    }

    public List<Company> getCompaniesByOwner(UUID ownerUuid) {
        if (ownerUuid == null) return Collections.emptyList();
        ensureLoaded();
        List<Company> list = new ArrayList<>();
        for (Company c : companiesById.values()) {
            if (c.isOwner(ownerUuid)) {
                list.add(c);
            }
        }
        return list;
    }

    public Collection<Company> getAllCompanies() {
        ensureLoaded();
        return Collections.unmodifiableCollection(companiesById.values());
    }

    public void ensureLoaded() {
        if (!initialized) {
            init();
        }
    }

    private String generateUniqueSiret() {
        Random rand = new Random();
        while (true) {
            long siren = 100000000L + rand.nextInt(900000000);
            int nic = 10000 + rand.nextInt(90000);
            String siret = String.valueOf(siren) + String.valueOf(nic);
            if (!companiesBySiret.containsKey(siret.toLowerCase())) {
                return siret;
            }
        }
    }

    private String generateUniqueAccountNumber() {
        Random rand = new Random();
        while (true) {
            int num = 100000 + rand.nextInt(900000);
            String candidate = String.valueOf(num) + "F";
            boolean exists = false;
            for (Company c : companiesById.values()) {
                if (c.getAccountNumber().equalsIgnoreCase(candidate)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return String.valueOf(num);
            }
        }
    }
}
