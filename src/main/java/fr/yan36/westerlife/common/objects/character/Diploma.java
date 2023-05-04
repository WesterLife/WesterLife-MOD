package fr.yan36.westerlife.common.objects.character;

public class Diploma {

    public enum DiplomaType {
        BREVET,
        CAP,
        BACCALAUREATE,
        LICENCE,
        MASTER,
        DOCTORATE,
        BUT,
        BTS,
        OTHER
    };

    private DiplomaType diplomaType;
    private String name;
    private String description;
    private String obtentionDate;

    Diploma(DiplomaType diplomaType, String name, String description, String obtentionDate) {
        this.diplomaType = diplomaType;
        this.name = name;
        this.description = description;
        this.obtentionDate = obtentionDate;
    }

    public DiplomaType getType() {
        return diplomaType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getObtentionDate() {
        return obtentionDate;
    }

}
