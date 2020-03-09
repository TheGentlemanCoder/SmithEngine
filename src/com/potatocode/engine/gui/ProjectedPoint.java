package com.potatocode.engine.gui;

import java.awt.Dimension;

public class ProjectedPoint {
    private final int x;
    private final int y;

    // This is the normalized z value that can be used
    // when deciding which polygons to draw before others
    private final double z;

    public ProjectedPoint(int x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Dimension getDimensions() {
        return new Dimension(this.x, this.y);
    }

    public double getNormalizedZ() {
        return this.z;
    }
}
