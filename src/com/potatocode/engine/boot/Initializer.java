package com.potatocode.engine.boot;

import com.potatocode.engine.gui.*;

public class Initializer {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // The "true" field initializes the window in debug mode
                new Window("3D Projection using the Smith Engine", true);
            }
        });
    }
}
