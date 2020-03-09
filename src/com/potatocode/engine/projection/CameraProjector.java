package com.potatocode.engine.projection;
import com.potatocode.engine.gui.*;

import java.awt.*;
import java.lang.Math;

public class CameraProjector {
    public static ProjectedPoint getProjectedPoint(Canvas2D canvas,
                                              Camera camera,
                                              Point3D point) {
        Vector3D i_vector = new Vector3D(1, 0, 0);
        Vector3D j_vector = new Vector3D(0,-1, 0);
        Vector3D k_vector = new Vector3D(0, 0, 1);

        Dimension screenDimensions = canvas.getDimensions();
        double screenWidth = screenDimensions.width;
        double screenHeight = screenDimensions.height;
        double aspectRatio = screenHeight / screenWidth;

        Point3D cameraPoint = camera.getPosition();
        Vector3D orientation = camera.getOrientation();

        // Translate the camera to the origin and the point accordingly
        Vector3D relativePosition = new Vector3D(point.getX() - cameraPoint.getX(),
                                                 point.getY() - cameraPoint.getY(),
                                                 point.getZ() - cameraPoint.getZ());

        // Find the angle of camera orientation within the XZ plane (horizontal rotation)
        double theta_rotated = Angle.atan(orientation.getZ(), orientation.getX());

        // Find the angle of camera orientation within the YZ plane (vertical rotation)
        double phi_rotated = Angle.atan(orientation.getZ(), orientation.getY());

        // Account for camera orientation by rotating point around Z-Axis
        Vector3D rotatedZAxisPoint = rotateZAxis(relativePosition, camera.getPhi());

        // Account for camera orientation by rotating point around X-Axis
        Vector3D rotatedPoint = rotateXAxis(rotatedZAxisPoint, camera.getTheta());

        // Perform the camera transform to project the 3D point to the 2D screen
        Point3D projectedPoint = cameraTransform(camera, rotatedPoint, aspectRatio);

        // Scale coordinates to screen size
        double screenX = projectedPoint.getX() * screenWidth / 2.0;
        double screenY = projectedPoint.getY() * screenHeight / 2.0;

        ProjectedPoint screenPoint = new ProjectedPoint((int) screenX, (int) screenY,
                                                         projectedPoint.getZ());

        return canvas.translateToOrigin(screenPoint);
    }

    public static Point3D translate(Point3D point, Vector3D translation) {
        return new Point3D(point.getX() + translation.getX(),
                           point.getY() + translation.getY(),
                           point.getZ() + translation.getZ());
    }

    public static Vector3D rotateZAxis(Vector3D vector, Angle theta) {
        double initialFillValue = 0.0;
        Matrix rotationZAxisMatrix = new Matrix(3, 3, initialFillValue);
        Vector3D returnVector = new Vector3D(0, 0, 0);

        try {
            // Fill rotation matrix
            rotationZAxisMatrix.set(0, 0,  Math.cos(theta.getAngle()));
            rotationZAxisMatrix.set(0, 1,  Math.sin(theta.getAngle()));
            rotationZAxisMatrix.set(1, 0, -Math.sin(theta.getAngle()));
            rotationZAxisMatrix.set(1, 1,  Math.cos(theta.getAngle()));
            rotationZAxisMatrix.set(2, 2, 1);

            // Rotate the vector about the x-axis
            returnVector = rotationZAxisMatrix.multiply(vector);
        } catch (MatrixDimensionMismatch ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return returnVector;
    }

    public static Vector3D rotateXAxis(Vector3D vector, Angle phi) {
        double initialFillValue = 0.0;
        Matrix rotationXAxisMatrix = new Matrix(3, 3, initialFillValue);
        Vector3D returnVector = new Vector3D(0, 0, 0);

        try {
            // Fill rotation matrix
            rotationXAxisMatrix.set(0, 0, 1);
            rotationXAxisMatrix.set(1, 1,  Math.cos(phi.getAngle()));
            rotationXAxisMatrix.set(1, 2,  Math.sin(phi.getAngle()));
            rotationXAxisMatrix.set(2, 1, -Math.sin(phi.getAngle()));
            rotationXAxisMatrix.set(2, 2,  Math.cos(phi.getAngle()));

            // Rotate the vector about the z-axis
            returnVector = rotationXAxisMatrix.multiply(vector);
        } catch (MatrixDimensionMismatch ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return returnVector;
    }

    public static Point3D cameraTransform(Camera camera, Vector3D vector, double aspectRatio) {
        // Cache this value to divide the coordinates by later
        double cachedZValue = vector.getZ();

        // Defines distance of objects that are too near to see
        double near = camera.near;
        // Defines distance of objects that are too far to see
        double far = camera.far;

        // Perform the camera transform
        double newX = vector.getX() * aspectRatio * 1.0/Math.tan(camera.getVerticalFOV().getAngle()/2.0);
        double newY = vector.getY() * 1.0/Math.tan(camera.getVerticalFOV().getAngle()/2.0);
        double newZ = vector.getZ() * (far + near) / (far - near) + (2 * far * near) / (far - near);

        if (cachedZValue != 0.0) {
            newX /= -cachedZValue;
            newY /= -cachedZValue;
            newZ /= -cachedZValue;
        }

        return new Point3D(newX, newY, newZ);
    }

    public static boolean clipPoint(Camera camera, Vector3D point) {
        // Unit vectors defining the normals of the camera's clipping planes
        Vector3D below_pyramid = new Vector3D(0, Math.sin(camera.getVerticalFOV().getAngle()/2), -Math.cos(camera.getVerticalFOV().getAngle()/2));
        Vector3D above_pyramid = new Vector3D(0, Math.sin(camera.getVerticalFOV().getAngle()/2), -Math.cos(camera.getVerticalFOV().getAngle()/2));
        Vector3D left_pyramid  = new Vector3D( Math.cos(camera.getHorizontalFOV().getAngle()/2), 0, -Math.sin(camera.getHorizontalFOV().getAngle()/2));
        Vector3D right_pyramid = new Vector3D(-Math.cos(camera.getHorizontalFOV().getAngle()/2), 0, -Math.sin(camera.getHorizontalFOV().getAngle()/2));

        // Find point relative to camera position and orientation
        Vector3D camera_relative_point = point.sub(new Vector3D(camera.getPosition().getX(),
                                                                camera.getPosition().getY(),
                                                                camera.getPosition().getZ()));
        Vector3D xaxis_rotated_point = CameraProjector.rotateXAxis(camera_relative_point, camera.getTheta());
        Vector3D rotated_point = CameraProjector.rotateZAxis(xaxis_rotated_point, camera.getPhi());

        // We don't clip by default
        boolean clipPoint = false;

        // Does the point lie behind the camera?
        if (rotated_point.getZ() > 0) {
            //System.out.println("Clipping " + point + " because it is behind camera.");
            clipPoint = true;
        // Check if point is below camera viewing pyramid
        } else if (rotated_point.dot(below_pyramid) < 0) {
            //System.out.println("Clipping " + point + " because it is below viewing pyramid.");
            clipPoint = true;
        // Check if point is above camera viewing pyramid
        } else if (rotated_point.dot(above_pyramid) < 0) {
            //System.out.println("Clipping " + point + " because it is above viewing pyramid.");
            clipPoint = true;
        // Check if point is to the left of camera viewing pyramid
        } else if (rotated_point.dot(left_pyramid) < 0) {
            //System.out.println("Clipping " + point + " because it is to the left of viewing pyramid.");
            clipPoint = true;
        // Check if point is to the right of camera viewing pyramid
        } else if (rotated_point.dot(right_pyramid) < 0) {
            //System.out.println("Clipping " + point + " because it is to the right of viewing pyramid.");
            clipPoint = true;
        }

        return clipPoint;
    }
}
