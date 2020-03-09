package com.potatocode.engine.gui;
import com.potatocode.engine.projection.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private GridBagConstraints constraints;
    private int width;
    private int height;
    private boolean debugMode;

    public Window(String title, boolean debug) {
        this.debugMode = debug;

        setFullscreen();
        // TODO setLayout();
        findScreenDimensions();
        hideCursor();

        this.setTitle(title);
        this.setVisible(true);

        createPanel();
    }

    private void setFullscreen() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
    }

    private void findScreenDimensions() {
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenDimensions.width;
        this.height = screenDimensions.height;
    }

    public void updateWindowDimensions() {
        this.width = this.getBounds().width;
        this.height = this.getBounds().height;
    }

    public int getWindowWidth() {
        updateWindowDimensions();
        return this.width;
    }

    public int getWindowHeight() {
        updateWindowDimensions();
        return this.height;
    }

    private void hideCursor() {
        BufferedImage cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImage, new Point(0, 0), "blank cursor");
        this.getContentPane().setCursor(blankCursor);
    }

    private void createPanel() {
        int refreshRate = 40;

        Camera camera = new Camera(new Point3D(0, 0, 0),
                                   new Angle(0, Angle.RADIANS),
                                   new Angle(0, Angle.RADIANS));

        Dimension dimensions = new Dimension(this.width, this.height);

        ArrayList<Object3D> objectList = new ArrayList<>();
        // C
        Object3D C1 = getCube(new Point3D(-100, 0, -20), 10.0);
        Object3D C2 = getCube(new Point3D(-110, 0, -20), 10.0);
        Object3D C3 = getCube(new Point3D(-110, -10, -20), 10.0);
        Object3D C4 = getCube(new Point3D(-110, -20, -20), 10.0);
        Object3D C5 = getCube(new Point3D(-110, -30, -20), 10.0);
        Object3D C6 = getCube(new Point3D(-100, -30, -20), 10.0);

        objectList.add(C1);
        objectList.add(C2);
        objectList.add(C3);
        objectList.add(C4);
        objectList.add(C5);
        objectList.add(C6);

        // O
        Object3D O11 = getCube(new Point3D(-70, 0, -20), 10.0);
        Object3D O12 = getCube(new Point3D(-80, 0, -20), 10.0);
        Object3D O13 = getCube(new Point3D(-80, -10, -20), 10.0);
        Object3D O14 = getCube(new Point3D(-80, -20, -20), 10.0);
        Object3D O15 = getCube(new Point3D(-80, -30, -20), 10.0);
        Object3D O16 = getCube(new Point3D(-70, -30, -20), 10.0);
        Object3D O17 = getCube(new Point3D(-60, -30, -20), 10.0);
        Object3D O18 = getCube(new Point3D(-60, -20, -20), 10.0);
        Object3D O19 = getCube(new Point3D(-60, -10, -20), 10.0);
        Object3D O110 = getCube(new Point3D(-60, 0, -20), 10.0);

        objectList.add(O11);
        objectList.add(O12);
        objectList.add(O13);
        objectList.add(O14);
        objectList.add(O15);
        objectList.add(O16);
        objectList.add(O17);
        objectList.add(O18);
        objectList.add(O19);
        objectList.add(O110);

        // O
        Object3D O21 = getCube(new Point3D(-30, 0, -20), 10.0);
        Object3D O22 = getCube(new Point3D(-40, 0, -20), 10.0);
        Object3D O23 = getCube(new Point3D(-40, -10, -20), 10.0);
        Object3D O24 = getCube(new Point3D(-40, -20, -20), 10.0);
        Object3D O25 = getCube(new Point3D(-40, -30, -20), 10.0);
        Object3D O26 = getCube(new Point3D(-30, -30, -20), 10.0);
        Object3D O27 = getCube(new Point3D(-20, -30, -20), 10.0);
        Object3D O28 = getCube(new Point3D(-20, -20, -20), 10.0);
        Object3D O29 = getCube(new Point3D(-20, -10, -20), 10.0);
        Object3D O210 = getCube(new Point3D(-20, 0, -20), 10.0);

        objectList.add(O21);
        objectList.add(O22);
        objectList.add(O23);
        objectList.add(O24);
        objectList.add(O25);
        objectList.add(O26);
        objectList.add(O27);
        objectList.add(O28);
        objectList.add(O29);
        objectList.add(O210);

        // L
        Object3D L1 = getCube(new Point3D(0, 0, -20), 10.0);
        Object3D L2 = getCube(new Point3D(0, -10, -20), 10.0);
        Object3D L3 = getCube(new Point3D(0, -20, -20), 10.0);
        Object3D L4 = getCube(new Point3D(0, -30, -20), 10.0);
        Object3D L5 = getCube(new Point3D(10, 0, -20), 10.0);
        Object3D L6 = getCube(new Point3D(20, 0, -20), 10.0);

        objectList.add(L1);
        objectList.add(L2);
        objectList.add(L3);
        objectList.add(L4);
        objectList.add(L5);
        objectList.add(L6);

        //Object3D cube2 = getCube(new Point3D(0, 0, 30), 20.0);
        //Object3D cube3 = getCube(new Point3D(50, 50, 50), 40.0);
        //Object3D cube4 = getCube(new Point3D(10, -10, -50), 20.0);
        //objectList.add(cube1);
        //objectList.add(cube2);
        //objectList.add(cube3);
        //objectList.add(cube4);

        ProjectionCanvas3D canvas = new ProjectionCanvas3D(refreshRate, dimensions,
                                                           camera, objectList, this.debugMode);
        this.add(canvas);
    }

    private Object3D getCube(Point3D location, double sideLength) {
        ArrayList<Face> faces = new ArrayList<>();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        Point3D v1 = new Point3D(x-sideLength/2, y-sideLength/2, z-sideLength/2);
        Point3D v2 = new Point3D(x-sideLength/2, y-sideLength/2, z+sideLength/2);
        Point3D v3 = new Point3D(x-sideLength/2, y+sideLength/2, z+sideLength/2);
        Point3D v4 = new Point3D(x-sideLength/2, y+sideLength/2, z-sideLength/2);
        Point3D v5 = new Point3D(x+sideLength/2, y-sideLength/2, z-sideLength/2);
        Point3D v6 = new Point3D(x+sideLength/2, y-sideLength/2, z+sideLength/2);
        Point3D v7 = new Point3D(x+sideLength/2, y+sideLength/2, z+sideLength/2);
        Point3D v8 = new Point3D(x+sideLength/2, y+sideLength/2, z-sideLength/2);

                                                 //           v7 * - - - - - - - * v6
        Face faceA = new Face(v1, v2, v3, v4);   //             /|              /|
        Face faceB = new Face(v1, v5, v8, v4);   //            / |       FC    / |
        Face faceC = new Face(v5, v6, v7, v8);   //           /  |  FD        /  |
        Face faceD = new Face(v6, v7, v3, v2);   //       v3 /   |        v2 /   |
        Face faceE = new Face(v1, v5, v6, v2);   //         * - - - - - - - *    |
        Face faceF = new Face(v4, v8, v7, v3);   //         | FF | v8       | FE |
                                                 //         |    * - - - - -|- - * v5
        faces.add(faceA);                        //         |   /           |   /
        faces.add(faceB);                        //         |  /        FB  |  /
        faces.add(faceC);                        //         | /  FA         | /
        faces.add(faceD);                        //         |/              |/
        faces.add(faceE);                        //         * - - - - - - - *
        faces.add(faceF);                        //        v4               v1

        return new Object3D(faces);
    }
}
