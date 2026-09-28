package fr.yan36.westerlife.common.objects.entreprises;

public enum CompanyType {
    SARL("SARL", "Société à Responsabilité Limitée", 1.0f),
    SAS("SAS", "Société par Actions Simplifiée", 1.0f),
    SA("SA", "Société Anonyme", 37000.0f),
    SELARL("SELARL", "Société d'Exercice Libéral à Responsabilité Limitée", 1.0f),
    ASSOCIATION("Association", "Association Loi 1901", 0.0f),
    PUBLIQUE("Publique", "Établissement Public", 0.0f),
    MICRO("Micro", "Micro-Entreprise", 0.0f);

    private final String shortName;
    private final String fullName;
    private final float minCapital;

    CompanyType(String shortName, String fullName, float minCapital) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.minCapital = minCapital;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public float getMinCapital() {
        return minCapital;
    }

    public static CompanyType fromString(String name) {
        if (name == null || name.trim().isEmpty()) {
            return SARL;
        }
        String clean = name.trim().toUpperCase();
        for (CompanyType type : values()) {
            if (type.name().equalsIgnoreCase(clean) || type.shortName.equalsIgnoreCase(clean)) {
                return type;
            }
        }
        if (clean.contains("ASSOC")) return ASSOCIATION;
        if (clean.contains("PUB")) return PUBLIQUE;
        if (clean.contains("MIC")) return MICRO;
        return SARL;
    }
}
