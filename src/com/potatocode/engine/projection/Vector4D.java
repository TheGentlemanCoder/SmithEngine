package com.potatocode.engine.projection;

public class Vector4D {
    private final double x;
    private final double y;
    private final double z;
    private final double w;

    public Vector4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4D(double x, double y, double z) {
        this(x, y, z, 1.0);
    }

    //         -------->
    // Vector: Tail-Head
    public Vector4D(Point3D head, Point3D tail) {
        this(head.getX() - tail.getX(),
             head.getY() - tail.getY(),
             head.getZ() - tail.getZ(),
             1.0);
    }

    public double dot(Vector4D otherVector) {
        return this.x * otherVector.getX() +
               this.y * otherVector.getY() +
               this.z * otherVector.getZ() +
               this.w * otherVector.getW();
    }

    public Vector4D multiply(double scalar) {
        return new Vector4D(this.x * scalar,
                            this.y * scalar,
                            this.z * scalar,
                            this.w * scalar);
    }

    public Vector4D add(Vector4D otherVector) {
        return new Vector4D(this.x + otherVector.getX(),
                            this.y + otherVector.getY(),
                            this.z + otherVector.getZ(),
                            this.w + otherVector.getW());
    }

    public Vector4D sub(Vector4D otherVector) {
        return new Vector4D(this.x - otherVector.getX(),
                            this.y - otherVector.getY(),
                            this.z - otherVector.getZ(),
                            this.w - otherVector.getZ());
    }

    public Vector4D normalize() {
        return this.multiply(1.0/this.w);
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

    public double getW() {
        return this.w;
    }

    public double mag() {
        return Math.sqrt(this.x * this.x +
                         this.y * this.y +
                         this.z * this.z +
                         this.w * this.w);
    }

    @Override
    public String toString() {
        return "< " + this.x + ", "
                    + this.y + ", "
                    + this.z + ", "
                    + this.w + " >";
    }

    @Override
    public boolean equals(Object object) {
        Vector4D vector = (Vector4D) object;

        return (this.x == vector.getX() &&
                this.y == vector.getY() &&
                this.z == vector.getZ() &&
                this.w == vector.getW());
    }
}
