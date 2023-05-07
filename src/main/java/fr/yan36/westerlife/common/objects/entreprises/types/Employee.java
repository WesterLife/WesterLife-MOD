package fr.yan36.westerlife.common.objects.entreprises.types;

import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class Employee {
    private EntityPlayer player;
    private List<String> ranks;
    private List<String> functions;
    private List<Prime> primes;
    private float salary;

    public Employee(EntityPlayer player, List<String> ranks, List<String> functions, List<Prime> primes, float salary) {
        this.player = player;
        this.ranks = ranks;
        this.functions = functions;
        this.primes = primes;
        this.salary = salary;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public List<String> getRanks() {
        return ranks;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public List<Prime> getPrimes() {
        return primes;
    }

    public float getSalary() {
        return salary;
    }

    public class Prime {
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
