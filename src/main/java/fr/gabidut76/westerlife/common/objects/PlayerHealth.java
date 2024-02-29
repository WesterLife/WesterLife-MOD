package fr.gabidut76.westerlife.common.objects;

import java.util.List;

public class PlayerHealth {

    List<Problems> problems;
    List<PlayerParts> brokenParts;

    public PlayerHealth(List<Problems> problems, List<PlayerParts> brokenParts) {
        this.problems = problems;
        this.brokenParts = brokenParts;
    }

    public List<Problems> getProblems() {
        return problems;
    }

    public void setProblems(List<Problems> problems) {
        this.problems = problems;
    }

    public List<PlayerParts> getBrokenParts() {
        return brokenParts;
    }

    public void setBrokenParts(List<PlayerParts> brokenParts) {
        this.brokenParts = brokenParts;
    }

    public void addProblem(Problems problem) {
        this.problems.add(problem);
    }

    public void addBrokenPart(PlayerParts brokenPart) {
        this.brokenParts.add(brokenPart);
    }



    public enum Problems {
        MALAISE,
        FATIGUE,
        SOIF,
        TOUX,
        SOMMEIL;

        public static List<Problems> getFromString(String s) {
            String[] parts = s.split(",");
            List<Problems> problems = null;
            for (String part : parts) {
                if (part.startsWith("problems=")) {
                    problems = Problems.getFromString(part.substring(9));
                }
            }
            return problems;
        }
    }

    public enum PlayerParts {
        TETE,
        BRAS_GAUCHE,
        BRAS_DROIT,
        CORP,
        JAMBE_GAUCHE,
        JAMBE_DROITE;

        public static List<PlayerParts> getFromString(String s) {
            String[] parts = s.split(",");
            List<PlayerParts> brokenParts = null;
            for (String part : parts) {
                if (part.startsWith("brokenParts=")) {
                    brokenParts = PlayerParts.getFromString(part.substring(12));
                }
            }
            return brokenParts;
        }
    }

    @Override
    public String toString() {
        return "problems=" + problems + ",brokenParts=" + brokenParts;
    }

    public static PlayerHealth getFromString(String s) {
        String[] parts = s.split(",");
        List<Problems> problems = null;
        List<PlayerParts> brokenParts = null;
        for (String part : parts) {
            if (part.startsWith("problems=")) {
                problems = Problems.getFromString(part.substring(9));
            }
            if (part.startsWith("brokenParts=")) {
                brokenParts = PlayerParts.getFromString(part.substring(12));
            }
        }
        return new PlayerHealth(problems, brokenParts);
    }
}
