package com.potatocode.engine.projection;

import java.lang.Math;

public class Angle {
    public static int RADIANS = 0;
    public static int DEGREES = 1;

    private final int units;
    private final double angle;

    public Angle(double angle, int units) {
        this.units = units;
        this.angle = angle;
    }

    // TODO implement a method for converting
    // to radians from degrees and another for
    // converting from radians to degrees

    public Angle add(double delta) {
        return new Angle((this.angle + delta), this.units);
    }

    public Angle sub(double delta) {
        return new Angle((this.angle - delta), this.units);
    }

    public Angle div(double divisor) {
        return new Angle((this.angle / divisor), this.units);
    }

    public Angle mod(double modulus) {
        return new Angle((this.angle % modulus), this.units);
    }

    public double getAngle() {
        return this.angle;
    }

    public int getUnits() {
        return this.units;
    }

    // Returns angle in radians
    public static double atan(double a, double b) {
        // Angle with positive X-Axis
        double angle = 0.0;

        // 1st Quadrant
        if (a > 0 && b > 0) {
            angle = Math.atan(b/a);
        }

        // Lies on the positive Y-Axis
        if (a == 0 && b > 0) {
            angle = Math.PI / 2.0;
        }

        // 2nd Quadrant
        if (a < 0 && b > 0) {
            angle = Math.PI + Math.atan(b/a);
        }

        // 3rd Quadrant
        if (a < 0 && b < 0) {
            angle = -Math.PI + Math.atan(b/a);
        }

        // Lies on the negative Z-Axis
        if (a == 0 && b < 0) {
            angle = -Math.PI / 2.0;
        }

        // 4th Quadrant
        if (a > 0 && b < 0) {
            angle = Math.atan(b/a);
        }

        return angle;
    }
}
