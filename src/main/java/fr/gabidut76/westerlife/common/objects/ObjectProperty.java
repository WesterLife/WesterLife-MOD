package fr.gabidut76.westerlife.common.objects;

public class ObjectProperty {
    public enum Type {
        STRING,
        INTEGER,
        FLOAT,
        BOOLEAN
    }
    private String name;
    private String value;
    private ObjectProperty.Type type;

    public ObjectProperty(String name, String value, Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }
}
