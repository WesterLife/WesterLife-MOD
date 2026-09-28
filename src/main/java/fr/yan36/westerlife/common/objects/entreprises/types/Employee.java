package fr.yan36.westerlife.common.objects.entreprises.types;

import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Employee {
    private int id;
    private int companyId;
    private UUID characterUuid;
    private String rankName;
    private String joinedDate;
    private float salary;
    private List<Prime> primes = new ArrayList<>();

    public Employee(UUID characterUuid, String rankName, String joinedDate) {
        this(0, 0, characterUuid, rankName, joinedDate, 0f);
    }

    public Employee(int id, int companyId, UUID characterUuid, String rankName, String joinedDate, float salary) {
        this.id = id;
        this.companyId = companyId;
        this.characterUuid = characterUuid;
        this.rankName = rankName != null ? rankName : "Employé";
        this.joinedDate = joinedDate != null ? joinedDate : "";
        this.salary = salary;
    }

    // Legacy constructor
    public Employee(EntityPlayer player, List<String> ranks, List<String> functions, List<Prime> primes, float salary) {
        this.characterUuid = (player != null) ? player.getUniqueID() : UUID.randomUUID();
        this.rankName = (ranks != null && !ranks.isEmpty()) ? ranks.get(0) : "Employé";
        this.joinedDate = "01/01/2026";
        this.primes = (primes != null) ? primes : new ArrayList<>();
        this.salary = salary;
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

    public UUID getCharacterUuid() {
        return characterUuid;
    }

    public void setCharacterUuid(UUID characterUuid) {
        this.characterUuid = characterUuid;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public List<String> getRanks() {
        return Collections.singletonList(rankName);
    }

    public List<String> getFunctions() {
        return Collections.emptyList();
    }

    public List<Prime> getPrimes() {
        return primes;
    }

    public String getDisplayName(MinecraftServer server) {
        if (characterUuid == null) return "Inconnu";
        try {
            Character c = DBUtils.getCharacter(characterUuid);
            if (c != null && c.getFullName() != null && !c.getFullName().isEmpty() && !c.getFullName().contains("error")) {
                return c.getFullName();
            }
        } catch (Exception ignored) {}

        if (server != null) {
            EntityPlayerMP p = server.getPlayerList().getPlayerByUUID(characterUuid);
            if (p != null) {
                return p.getName();
            }
        }
        return characterUuid.toString().substring(0, 8);
    }

    public static class Prime {
        private final String name;
        private final float amount;
        private final Integer date;
        private final String reason;

        public Prime(String name, float amount, Integer date, String reason) {
            this.name = name;
            this.amount = amount;
            this.date = date;
            this.reason = reason;
        }

        public String getName() {
            return name;
        }

        public float getAmount() {
            return amount;
        }

        public Integer getDate() {
            return date;
        }

        public String getReason() {
            return reason;
        }
    }
}
