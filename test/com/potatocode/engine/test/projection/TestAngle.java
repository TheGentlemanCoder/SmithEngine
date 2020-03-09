package com.potatocode.engine.test.projection;

import com.potatocode.engine.projection.Angle;
import java.lang.Math;

class TestAngle {
    public static void main(String[] args) {
        Test_atan();
    }

    public static void Test_atan() {
        System.out.println("Case #1: a = 20 * sqrt(3) / 2, b = 10");
        System.out.println("Expected: " + (Math.PI/6.0) + ".");
        System.out.println("Received: " + Angle.atan(20 * Math.sqrt(3)/2.0, 10) + ".\n");

        System.out.println("Case #2: a = 20, b = 20");
        System.out.println("Expected: " + (Math.PI/4.0) + ".");
        System.out.println("Received: " + Angle.atan(20, 20) + ".\n");

        System.out.println("Case #3: a = 10, b = 20 * sqrt(3) / 2");
        System.out.println("Expected: " + (Math.PI/3.0) + ".");
        System.out.println("Received: " + Angle.atan(10, 20 * Math.sqrt(3)/2.0) + ".\n");

        System.out.println("Case #4: a = 0, b = 20");
        System.out.println("Expected: " + (Math.PI/2.0) + ".");
        System.out.println("Received: " + Angle.atan(0, 20) + ".\n");

        System.out.println("Case #5: a = -20, b = 20");
        System.out.println("Expected: " + (3.0*Math.PI/4.0) + ".");
        System.out.println("Received: " + Angle.atan(-20, 20) + ".\n");

        System.out.println("Case #6: a = -20 * sqrt(3) / 2, b = -10");
        System.out.println("Expected: " + (-5.0*Math.PI/6.0) + ".");
        System.out.println("Received: " + Angle.atan(-20 * Math.sqrt(3)/2.0, -10) + ".\n");

        System.out.println("Case #7: a = -20, b = -20");
        System.out.println("Expected: " + (-3.0*Math.PI/4.0) + ".");
        System.out.println("Received: " + Angle.atan(-20, -20) + ".\n");

        System.out.println("Case #8: a = -10, b = -20 * sqrt(3) / 2");
        System.out.println("Expected: " + (-2.0*Math.PI/3.0) + ".");
        System.out.println("Received: " + Angle.atan(-10, -20 * Math.sqrt(3) / 2.0) + ".\n");

        System.out.println("Case #9: a = 0, b = -20");
        System.out.println("Expected: " + -Math.PI/2.0 + ".");
        System.out.println("Received: " + Angle.atan(0, -20) + ".\n");

        System.out.println("Case #10: a = 20, b = -20");
        System.out.println("Expected: " + (-Math.PI/4.0) + ".");
        System.out.println("Received: " + Angle.atan(20, -20) + ".\n");
    }
}
