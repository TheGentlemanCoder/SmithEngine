package com.potatocode.engine.projection;

import java.util.*;

public class Object3D {
    private ArrayList<Face> faces;

    public Object3D(ArrayList<Face> faces) {
        this.faces = faces;
    }

    public void move(Vector3D vector) {
        for (Face face : this.faces) {
            face.move(vector);
        }
    }

    public List<Face> getFaces() {
        return this.faces;
    }
}
