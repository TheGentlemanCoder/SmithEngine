package com.potatocode.engine.projection;

import java.util.*;

public class Camera {
    private Point3D position;
    private Vector3D orientation;
    private Vector3D a;
    private Vector3D b;
    private Angle theta;
    private Angle phi;

    private Angle horizontalFOV;
    private Angle verticalFOV;

    // Defines distance of objects that are too close to see
    public double near = 1.0;
    // Defines distance of objects that are too far to see
    public double far = 1000.0;

    // Constructs the screen as below, where
    // a and b are perpendicular vectors to
    // each other and the orientation vector,
    // and where "*" is the midpoint of the screen.
    //  ______________________
    // |          ^           |
    // |        b |           |
    // |          * - - - - > |
    // |                 a    |
    // |______________________|

    public Camera(Point3D position, Angle theta, Angle phi) {
        this.position = position;
        this.theta = theta;
        this.phi = phi;
        this.horizontalFOV = new Angle(Math.PI/2.0, Angle.RADIANS);
        this.verticalFOV = new Angle(Math.PI/2.0, Angle.RADIANS);
        updateCameraOrientation();
    }

    private void updateCameraOrientation() {
        this.orientation = getUnitVectorOriented(theta, phi);
        this.a = getUnitVectorOriented(theta.add(3.0/2.0*Math.PI), new Angle(0, Angle.RADIANS));

        double theta_b = theta.getAngle();

        if (phi.getAngle() > 0) {
            theta_b += Math.PI;
        }
        this.b = getUnitVectorOriented(new Angle(theta_b, Angle.RADIANS), phi.add(Math.PI/2.0));
    }

    // Theta, relative to the positive x-axis, phi, relative to the negative z-axis.
    private Vector3D getUnitVectorOriented(Angle theta, Angle phi) {
        Vector3D i_vector = new Vector3D(1, 0, 0);
        Vector3D rotated_xaxis_i_vector = CameraProjector.rotateZAxis(i_vector, phi);
        Vector3D rotated_unit_vector = CameraProjector.rotateXAxis(rotated_xaxis_i_vector, theta);

        return rotated_unit_vector;

        /*
        double x = Math.sin(Math.PI/2.0 - phi.getAngle())*Math.cos(theta.getAngle());
        double y = Math.sin(Math.PI/2.0 - phi.getAngle())*Math.sin(theta.getAngle());
        double z = -Math.cos(phi.getAngle());

        return new Vector3D(x, y, z);
        */
    }

    // Returns the nearest unit vector the smallest angle from the camera's orientation
    public Vector3D nearestUnitVector() {
        Vector3D positiveX = new Vector3D(1, 0, 0);
        Vector3D positiveY = new Vector3D(0, 1, 0);
        Vector3D positiveZ = new Vector3D(0, 0, 1);

        Vector3D negativeX = new Vector3D(-1, 0, 0);
        Vector3D negativeY = new Vector3D(0, -1, 0);
        Vector3D negativeZ = new Vector3D(0, 0, -1);

        double anglePositiveX = Math.acos(this.orientation.dot(positiveX));
        double anglePositiveY = Math.acos(this.orientation.dot(positiveY));
        double anglePositiveZ = Math.acos(this.orientation.dot(positiveZ));

        double angleNegativeX = Math.acos(this.orientation.dot(negativeX));
        double angleNegativeY = Math.acos(this.orientation.dot(negativeY));
        double angleNegativeZ = Math.acos(this.orientation.dot(negativeZ));

        double[] angles = new double[6];
        angles[0] = anglePositiveX;
        angles[1] = angleNegativeX;
        angles[2] = anglePositiveY;
        angles[3] = angleNegativeY;
        angles[4] = anglePositiveZ;
        angles[5] = angleNegativeZ;

        // Find the minimum angle
        int minimumAngleIndex = 0;
        for (int i = 1; i < 6; ++i) {
            if (angles[minimumAngleIndex] > angles[i]) {
                minimumAngleIndex = i;
            }
        }

        Vector3D closestUnitVector = new Vector3D(0, 0, 0);

        switch (minimumAngleIndex) {
            case 0:
            closestUnitVector = positiveX;
            break;

            case 1:
            closestUnitVector = negativeX;
            break;

            case 2:
            closestUnitVector = positiveY;
            break;

            case 3:
            closestUnitVector = negativeY;
            break;

            case 4:
            closestUnitVector = positiveZ;
            break;

            case 5:
            closestUnitVector = negativeZ;
            break;
        }

        return closestUnitVector;
    }

    // Assumes a clockwise direction of motion relative
    // to the xy-plane
    public void rotate(double delta_theta) {
        if (this.theta.add(delta_theta).getAngle() > Math.PI) {
            this.theta = this.theta.sub(2*Math.PI);
        } else if (this.theta.add(delta_theta).getAngle() < -Math.PI) {
            this.theta = this.theta.add(2*Math.PI);
        }

        this.theta = this.theta.add(delta_theta);
        updateCameraOrientation();
    }

    public void inclinate(double delta_phi) {
        if (this.phi.add(delta_phi).getAngle() > Math.PI/2.0) {
            this.phi = new Angle( Math.PI/2.0, Angle.RADIANS);
        } else if (this.phi.add(delta_phi).getAngle() < -Math.PI/2.0) {
            this.phi = new Angle(-Math.PI/2.0, Angle.RADIANS);
        } else {
            this.phi = this.phi.add(delta_phi);
        }

        updateCameraOrientation();
    }

    public void move(Vector3D vector) {
        double newX = position.getX() + vector.getX();
        double newY = position.getY() + vector.getY();
        double newZ = position.getZ() + vector.getZ();

        this.position = new Point3D(newX, newY, newZ);
    }

    public Point3D getPosition() {
        return this.position;
    }

    public Vector3D getOrientation() {
        return this.orientation;
    }

    public Vector3D getA() {
        return this.a;
    }

    public Vector3D getB() {
        return this.b;
    }

    public Angle getHorizontalFOV() {
        return this.horizontalFOV;
    }

    public Angle getVerticalFOV() {
        return this.verticalFOV;
    }

    public Angle getTheta() {
        return this.theta;
    }

    public Angle getPhi() {
        return this.phi;
    }
}
