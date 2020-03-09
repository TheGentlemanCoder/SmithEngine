package com.potatocode.engine.gui;
import com.potatocode.engine.projection.*;
import com.potatocode.engine.controls.*;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ProjectionCanvas3D extends Canvas2D {
    private CameraProjector projector = new CameraProjector();
    private Dimension dimensions;
    private double aspectRatio;

    private ArrayList<Object3D> objects;
    private Camera camera;

    private Keyboard keyboardListener;
    private MouseMovementTracker mouseListener;
    private CameraUpdater cameraUpdater;
    private JLabel axisLabel;

    private boolean debugMode;

    public ProjectionCanvas3D(int refreshRate, Dimension dimensions,
                              Camera camera, ArrayList<Object3D> objects,
                              boolean debug) {
        super(refreshRate, dimensions);
        this.dimensions = dimensions;
        this.aspectRatio = dimensions.height / dimensions.width;
        this.camera = camera;
        this.objects = objects;
        this.debugMode = debug;

        initializeControls();

        axisLabel = new JLabel("Near the " + camera.nearestUnitVector() + " axis.");
        this.add(axisLabel);
    }

    private void initializeControls() {
        initializeKeyboardListener();
        initializeMouseListener();
        this.cameraUpdater = new CameraUpdater(this.dimensions);
    }

    private void initializeKeyboardListener() {
        InputMap im  =  this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();
        this.keyboardListener = new Keyboard(im, am);
    }

    private void initializeMouseListener() {
        this.mouseListener = new MouseMovementTracker(this, this.dimensions);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateCamera();
        paintLabel(g);
        projectObjects(g);

        if (debugMode) {
            drawDebugCoordinateAxes(g);
        }
    }

    public void updateCamera() {
        Vector3D movementVector = cameraUpdater.getMovementVector(camera, keyboardListener);
        camera.move(movementVector);

        Angle delta_theta = cameraUpdater.getCameraRotation(camera, mouseListener);
        Angle delta_phi = cameraUpdater.getCameraInclination(camera, mouseListener);

        camera.rotate(delta_theta.getAngle());
        camera.inclinate(delta_phi.getAngle());
    }

    private void paintLabel(Graphics g) {
        axisLabel.setText("Near the " + camera.nearestUnitVector() + " axis. Camera location: " + camera.getPosition() +
                          ". Camera orientation: " + camera.getOrientation());
    }

    private void projectObjects(Graphics g) {
        for (Object3D object : this.objects) {
            for (Face face : object.getFaces()) {
                for (Line edge : face.getEdges()) {
                    Vector3D pointA = new Vector3D(edge.getA().getX(),
                                                   edge.getA().getY(),
                                                   edge.getA().getZ());

                    Vector3D pointB = new Vector3D(edge.getB().getX(),
                                                   edge.getB().getY(),
                                                   edge.getB().getZ());

                    // Don't draw point if it's meant to be clipped
                    if (CameraProjector.clipPoint(camera, pointA) ||
                        CameraProjector.clipPoint(camera, pointB)) {
                        continue;
                    }

                    ProjectedPoint screenPointA = this.projector.getProjectedPoint(
                        this, this.camera, edge.getA());
                    ProjectedPoint screenPointB = this.projector.getProjectedPoint(
                        this, this.camera, edge.getB());
                            Dimension screenCoordinatesA = screenPointA.getDimensions();
                            Dimension screenCoordinatesB = screenPointB.getDimensions();
                            g.drawLine(screenCoordinatesA.width, screenCoordinatesA.height,
                                       screenCoordinatesB.width, screenCoordinatesB.height);
                }
            }
        }
    }

    public void drawDebugCoordinateAxes(Graphics g) {
        Vector3D i_vector = new Vector3D(1, 0, 0);
        Vector3D j_vector = new Vector3D(0, 1, 0);
        Vector3D k_vector = new Vector3D(0, 0, 1);

        // Angular distance in the XY-Plane from the x-axis
        Angle theta = camera.getTheta();
        // Angular distance in the YZ-Plane from the z-axis
        Angle phi = camera.getPhi();

        // Rotate the unit vectors according to the camera's orientation
        Vector3D partially_rotated_i_vector = CameraProjector.rotateZAxis(new Vector3D(1, 0, 0), phi);
        Vector3D partially_rotated_j_vector = CameraProjector.rotateZAxis(new Vector3D(0, 1, 0), phi);
        Vector3D partially_rotated_k_vector = CameraProjector.rotateZAxis(new Vector3D(0, 0, 1), phi);

        Vector3D rotated_i_vector = CameraProjector.rotateXAxis(partially_rotated_i_vector, theta);
        Vector3D rotated_j_vector = CameraProjector.rotateXAxis(partially_rotated_j_vector, theta);
        Vector3D rotated_k_vector = CameraProjector.rotateXAxis(partially_rotated_k_vector, theta);

        Point3D projected_i_vector = new Point3D(rotated_i_vector.getX(), rotated_i_vector.getY(), rotated_i_vector.getZ());
        Point3D projected_j_vector = new Point3D(rotated_j_vector.getX(), rotated_j_vector.getY(), rotated_j_vector.getZ());
        Point3D projected_k_vector = new Point3D(rotated_k_vector.getX(), rotated_k_vector.getY(), rotated_k_vector.getZ());

        // Define origin of debug axes which will not move as camera rotates
        Dimension origin = new Dimension(50, 50);

        // Use projected points to create screen coordinate pairs for drawing unit vectors
        Dimension screenCoordsXAxis = new Dimension((int) (projected_i_vector.getX() * 20 + origin.width),  // scale and offset x-axis
                                                    (int) (projected_i_vector.getY() * 20 + origin.height));

        Dimension screenCoordsYAxis = new Dimension((int) (projected_j_vector.getX() * 20 + origin.width),  // scale and offset x-axis
                                                    (int) (projected_j_vector.getY() * 20 + origin.height));

        Dimension screenCoordsZAxis = new Dimension((int) (projected_k_vector.getX() * 20 + origin.width),  // scale and offset x-axis
                                                    (int) (projected_k_vector.getY() * 20 + origin.height));

        // Change Graphics object to allow us to change line thickness
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));

        g2d.setColor(Color.RED);
        g2d.drawLine(screenCoordsXAxis.width, screenCoordsXAxis.height, origin.width, origin.height);

        g2d.setColor(Color.GREEN);
        g2d.drawLine(screenCoordsYAxis.width, screenCoordsYAxis.height, origin.width, origin.height);

        g2d.setColor(Color.BLUE);
        g2d.drawLine(screenCoordsZAxis.width, screenCoordsZAxis.height, origin.width, origin.height);
    }

    public void drawPoint(Graphics g, Dimension pointOnScreen) {
        int pointPixelRadius = 3;
        g.fillOval(pointOnScreen.width - pointPixelRadius,
                   pointOnScreen.height - pointPixelRadius,
                   pointOnScreen.width + pointPixelRadius,
                   pointOnScreen.height + pointPixelRadius);
    }
}
