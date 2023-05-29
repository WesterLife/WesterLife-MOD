package fr.yan36.westerlife.common.objects;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ArmorSuperpositionState {
    byte activePart;
    EntityEquipmentSlot activeSlot;
    String fullName;

    public ArmorSuperpositionState(byte activePart, EntityEquipmentSlot activeSlot, String fullName) {
        this.activePart = activePart;
        this.activeSlot = activeSlot;
        this.fullName = fullName;
    }

    public byte getActivePart() {
        return activePart;
    }

    public EntityEquipmentSlot getActiveSlot() {
        return activeSlot;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return activePart + "!" + activeSlot.getName() + "!" + fullName;
    }

    public static ArmorSuperpositionState fromString(String s) {
        String[] split = s.split("!");
        return new ArmorSuperpositionState(Byte.parseByte(split[0]), EntityEquipmentSlot.fromString(split[1]), split[2]);
    }
}
