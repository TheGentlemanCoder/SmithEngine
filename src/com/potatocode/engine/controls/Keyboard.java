package com.potatocode.engine.controls;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Keyboard {
    private InputMap im;
    private ActionMap am;
    private HashMap<String, Boolean> keymap;

    public Keyboard(InputMap inputMap, ActionMap actionMap) {
        this.im = inputMap;
        this.am = actionMap;
        keymap = new HashMap<>();
        initializeKeyBindings();
    }

    private void initializeKeyBindings() {
        // Programs exit key
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "escape-pressed");
        am.put("escape-pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        // TODO change escape initialization to use already available methods

        // Programs camera movement controls
        initialize(KeyEvent.VK_UP, "up-arrow");
        initialize(KeyEvent.VK_DOWN, "down-arrow");
        initialize(KeyEvent.VK_LEFT, "left-arrow");
        initialize(KeyEvent.VK_RIGHT, "right-arrow");
        initialize(KeyEvent.VK_SPACE, "space");
        initialize(KeyEvent.VK_B, "b");
    }

    private void initialize(int keyEventCode, String nameOfKey) {
        keymap.put(nameOfKey, Boolean.valueOf(false));
        initializePressOn(keyEventCode, nameOfKey);
        initializeReleaseOn(keyEventCode, nameOfKey);
    }

    private void initializePressOn(int keyEventCode, String nameOfKey) {
        String pressName = nameOfKey + "-pressed";
        im.put(KeyStroke.getKeyStroke(keyEventCode, 0, false), pressName);
        am.put(pressName, new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                keymap.put(nameOfKey, Boolean.valueOf(true));
            }
        });
    }

    private void initializeReleaseOn(int keyEventCode, String nameOfKey) {
        String releaseName = nameOfKey + "-released";
        im.put(KeyStroke.getKeyStroke(keyEventCode, 0, true), releaseName);
        am.put(releaseName, new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                keymap.put(nameOfKey, Boolean.valueOf(false));
            }
        });
    }

    public boolean isPressed(String nameOfKey) {
        return keymap.get(nameOfKey).booleanValue();
    }
}
