package com.potatocode.engine.projection;

public class Line {
    private final Point3D a;
    private final Point3D b;

    public Line(Point3D a, Point3D b) {
        this.a = a;
        this.b = b;
    }

    public Point3D getA() {
        return a;
    }

    public Point3D getB() {
        return b;
    }
}
