package fr.gabidut76.westerlife.common.utils;

public enum CarControls {
    ENGINE_STARTED(1),
    ACCELERATING(2),
    REVERSING(4),
    TURNING_LEFT(8),
    TURNING_RIGHT(16),
    HANDBRAKING(32);

    private final int value;

    CarControls(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}