package com.potatocode.engine.projection;

public class Vector3D {
    private final double x;
    private final double y;
    private final double z;

    //         -------->
    // Vector: Tail-Head
    public Vector3D(Point3D tail, Point3D head) {
        this.x = head.getX() - tail.getX();
        this.y = head.getY() - tail.getY();
        this.z = head.getZ() - tail.getZ();
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dot(Vector3D otherVector) {
        return this.x * otherVector.getX() +
               this.y * otherVector.getY() +
               this.z * otherVector.getZ();
    }

    public Vector3D cross(Vector3D otherVector) {
        double i_component =  this.y*otherVector.getZ()-this.z*otherVector.getY();
        double j_component = -this.x*otherVector.getZ()-this.z*otherVector.getX();
        double k_component =  this.x*otherVector.getY()-this.y*otherVector.getX();

        return new Vector3D(i_component, j_component, k_component);
    }

    public Vector3D multiply(double scalar) {
        return new Vector3D(this.x * scalar,
                            this.y * scalar,
                            this.z * scalar);
    }

    public Vector3D add(Vector3D otherVector) {
        return new Vector3D(this.x + otherVector.getX(),
                            this.y + otherVector.getY(),
                            this.z + otherVector.getZ());
    }

    public Vector3D sub(Vector3D otherVector) {
        return new Vector3D(this.x - otherVector.getX(),
                            this.y - otherVector.getY(),
                            this.z - otherVector.getZ());
    }

    public Vector3D projectedOnto(Vector3D otherVector) {
        double numeratorDotProduct = this.dot(otherVector);
        double denominatorDotProduct = otherVector.dot(otherVector);
        double scalarComponent = numeratorDotProduct / denominatorDotProduct;

        return otherVector.multiply(scalarComponent);
    }

    public double mag() {
        return Math.sqrt(this.x*this.x +
                         this.y*this.y +
                         this.z*this.z);
    }

    public Vector3D unit() {
        double magnitude = this.mag();
        return this.multiply(1.0/magnitude);
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

    // Theta, relative to the positive x-axis, phi, relative to the negative z-axis.
    public static Vector3D getUnitVectorOriented(Angle theta, Angle phi) {
        Vector3D i_vector = new Vector3D(1, 0, 0);
        Vector3D rotated_xaxis_i_vector = CameraProjector.rotateZAxis(i_vector, phi);
        Vector3D rotated_unit_vector = CameraProjector.rotateXAxis(rotated_xaxis_i_vector, theta);

        return rotated_unit_vector;
    }

    @Override
    public String toString() {
        return "< " + this.x + ", "
                    + this.y + ", "
                    + this.z + " >";
    }

    @Override
    public boolean equals(Object object) {
        Vector3D vector = (Vector3D) object;

        return (this.x == vector.getX() &&
                this.y == vector.getY() &&
                this.z == vector.getZ());
    }
}
