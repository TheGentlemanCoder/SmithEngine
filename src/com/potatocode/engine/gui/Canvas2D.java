package com.potatocode.engine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas2D extends JPanel {
    // In frames per second (FPS)
    private Dimension origin;
    private int refreshRate;
    protected Dimension dimensions;

    public Canvas2D(int refreshRate, Dimension dimensions) {
        this.refreshRate = refreshRate;
        this.dimensions = dimensions;

        Dimension origin = new Dimension(dimensions.width/2,
                                         dimensions.height/2);
        setOriginTo(origin);
        initializeCanvasRepainter();
    }

    private void initializeCanvasRepainter() {
        ActionListener repaintListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                repaint();
            }
        };

        Timer repaintTimer = new Timer(1000/refreshRate, repaintListener);
        repaintTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    protected void setOriginTo(Dimension screenCoordinates) {
        this.origin = screenCoordinates;
    }

    public ProjectedPoint translateToOrigin(ProjectedPoint point) {
        Dimension screenCoordinates = point.getDimensions();
        int newWidth = screenCoordinates.width + origin.width;
        int newHeight = screenCoordinates.height + origin.height;
        return new ProjectedPoint(newWidth, newHeight, point.getNormalizedZ());
    }

    public Dimension getDimensions() {
        return this.dimensions;
    }
}
