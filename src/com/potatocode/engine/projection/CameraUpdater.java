package com.potatocode.engine.projection;
import com.potatocode.engine.controls.*;

import java.awt.*;

public class CameraUpdater {
    private Dimension screenSize;

    public CameraUpdater(Dimension screenSize) {
        this.screenSize = screenSize;
    }

    public Vector3D getMovementVector(Camera camera, Keyboard keyboardListener) {
        double step_size = 0.1;

        Vector3D orientation = camera.getOrientation();
        Vector3D a = camera.getA();
        Vector3D k_vector = new Vector3D(0, 0, 1);

        if (keyboardListener.isPressed("up-arrow")) {
            return orientation.multiply(step_size);
        }
        if (keyboardListener.isPressed("down-arrow")) {
            return orientation.multiply(-step_size);
        }

        if (keyboardListener.isPressed("left-arrow")) {
            return a.multiply(-step_size);
        }
        if (keyboardListener.isPressed("right-arrow")) {
            return a.multiply(step_size);
        }

        if (keyboardListener.isPressed("space")) {
            return k_vector.multiply(step_size);
        }
        if (keyboardListener.isPressed("b")) {
            return k_vector.multiply(-step_size);
        }

        return new Vector3D(0, 0, 0);
    }

    public Angle getCameraRotation(Camera camera, MouseMovementTracker mouseListener) {
        Angle horizontalFOV = camera.getHorizontalFOV();
        double screenWidth = screenSize.width;
        double delta_x = mouseListener.deltaX();

        return new Angle(delta_x * 2 * (horizontalFOV.getAngle() / screenWidth), Angle.RADIANS);
    }

    public Angle getCameraInclination(Camera camera, MouseMovementTracker mouseListener) {
        Angle horizontalFOV = camera.getHorizontalFOV();
        double screenWidth = screenSize.width;
        double delta_y = mouseListener.deltaY();

        return new Angle(delta_y * 2 * (horizontalFOV.getAngle() / screenWidth), Angle.RADIANS);
    }
}
