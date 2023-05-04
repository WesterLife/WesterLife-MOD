package fr.yan36.westerlife.common.objects.economy;

public enum Taxes {
    TVA_NORMAL(Type.TAXES, "TVA normale", "Prélevée sur la majorité des produits et services", 20f),
    TVA_INTERMEDIAIRE(Type.TAXES, "TVA intermédiaire", "Prélevée notamment dans la restauration, la vente de produits alimentaires préparés, les transports...", 10f),
    TVA_REDUITE(Type.TAXES, "TVA réduite", "Prélevée sur les produits de première nécessité", 5.5f),
    TVA_SUPER_REDUITE(Type.TAXES, "TVA super réduite", "Prélevée sur les produits de première nécessité", 2.1f),
    IMPOT_SUR_LE_REVENUS(Type.TAXES, "Impôt sur le revenu", "Prélevé sur les revenus des personnes physiques", 10f),
    IMPOT_SUR_LES_SOCIETES(Type.TAXES, "Impôt sur les sociétés", "Prélevé sur les revenus des personnes morales", 25f);

    public enum Type { DUES, TAXES };

    private Type type;
    private String name;
    private String description;
    private Float amountPercentage;

    Taxes(Type type, String name, String description, Float amountPercentage) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.amountPercentage = amountPercentage;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getAmountPercentage() {
        return amountPercentage;
    }

}
