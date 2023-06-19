package fr.yan36.westerlife.common.utils;

public enum Animation {
    STAND_AT("Garde à vous", 2),
    NONE("", 5),
    RIGHT_ARM_UP("Bras droit levé",3),
    HANDS_UP("Mains levées", 4),
    SITTED("Assis", 1),
    POINTING_FINGER("Pointer du doigt", 7),
    MENOTTE("Menotter", -1),
    HELLO("Saluer", 8),
    HANDS_BEHIND("Mains derrière le dos", 6),
    SLEEP("Couché", 9);

    private final String name;
    private final int id;

    Animation(String name, int i) {
        this.name = name;
        this.id = i;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static Animation getAnimationById(int id) {
        for(Animation a : Animation.values()) {
            if(a.getId() == id) {
                return a;
            }
        }
        return null;
    }
}
