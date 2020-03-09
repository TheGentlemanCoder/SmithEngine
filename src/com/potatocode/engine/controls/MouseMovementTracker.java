package com.potatocode.engine.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseMovementTracker implements MouseMotionListener, MouseListener {
    private Dimension middleOfScreen;
    private Dimension changeInPosition;
    private Robot mouseMover;

    private boolean delta_x_checked = false;
    private boolean delta_y_checked = false;

    public MouseMovementTracker(JPanel panel, Dimension screenSize) {
        this.middleOfScreen = new Dimension(screenSize.width/2, screenSize.height/2);
        this.changeInPosition = new Dimension(0, 0);

        try {
            initMouseMover();
        } catch (AWTException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        panel.addMouseMotionListener(this);
        mouseMover.mouseMove(this.middleOfScreen.width, this.middleOfScreen.height);
    }

    public void initMouseMover() throws AWTException {
        this.mouseMover = new Robot();
    }

    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int horizontalChange  = mouseX - middleOfScreen.width;
        int verticalChange    = mouseY - middleOfScreen.height;

        //System.out.println(middleOfScreen + " and " + "(" + mouseX + ", " + mouseY + ")");

        this.changeInPosition.setSize(horizontalChange, verticalChange);
        //System.out.println("delta_x: " + this.changeInPosition.width + " delta_y: " + this.changeInPosition.height);

        mouseMover.mouseMove(middleOfScreen.width, middleOfScreen.height+23);
        delta_x_checked = false;
        delta_y_checked = false;
    }

    public int deltaX() {
        if (!delta_x_checked) {
            delta_x_checked = true;
            return this.changeInPosition.width;
        } else {
            return 0;
        }
    }

    public int deltaY() {
        if (!delta_y_checked) {
            delta_y_checked = true;
            return this.changeInPosition.height;
        } else {
            return 0;
        }
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("mouse dragged");
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("mouse released");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("mouse exited");
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("mouse entered");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("mouse pressed");
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
    }
}
