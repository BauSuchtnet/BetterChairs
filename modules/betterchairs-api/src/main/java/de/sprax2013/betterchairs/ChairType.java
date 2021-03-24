package de.sprax2013.betterchairs;

public enum ChairType {
    STAIR(0.5),
    SLAB(0.5),
    FURNITURE(0.18),
    CUSTOM(0);

    private final double yOffset;

    ChairType(double yOffset) {
        this.yOffset = yOffset;
    }

    public double getYOffset() {
        return yOffset;
    }
}