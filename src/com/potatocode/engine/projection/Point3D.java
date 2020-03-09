package com.potatocode.engine.projection;

public class Point3D {
    private final double x;
    private final double y;
    private final double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Point3D move(Vector3D vector) {
        double newX = this.x + vector.getX();
        double newY = this.y + vector.getY();
        double newZ = this.z + vector.getZ();
        return new Point3D(newX, newY, newZ);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
