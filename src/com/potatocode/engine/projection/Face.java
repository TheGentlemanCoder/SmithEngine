package com.potatocode.engine.projection;

import java.util.*;

public class Face {
    private ArrayList<Point3D> vertices;
    private ArrayList<Line> edges;

    public Face(Point3D ... vertices) {
        ArrayList<Point3D> verticesList = new ArrayList<>(Arrays.asList(vertices));
        this.vertices = verticesList;
        this.edges = generateEdges(this.vertices);
    }

    public Face(ArrayList<Point3D> vertices) {
        this.vertices = vertices;
        this.edges = generateEdges(vertices);
    }

    private ArrayList<Line> generateEdges(ArrayList<Point3D> vertices) {
        ArrayList<Line> edges = new ArrayList<>();

        int initialVertex = 0;
        int finalVertex = vertices.size() - 1;
        Line edge = new Line(vertices.get(finalVertex),
                             vertices.get(initialVertex));

        edges.add(edge);

        for (int indexA = 0, indexB = 1; indexB < vertices.size();
                 indexA++,   indexB++) {
            edge = new Line(vertices.get(indexB),
                            vertices.get(indexA));
            edges.add(edge);
        }

        return edges;
    }

    public void move(Vector3D vector) {
        for (int index = 0; index < vertices.size(); index++) {
            Point3D vertex = vertices.get(index);
            Point3D newVertex = vertex.move(vector);
            this.vertices.set(index, newVertex);
        }

        this.edges = generateEdges(this.vertices);
    }

    public ArrayList<Point3D> getVertices() {
        return this.vertices;
    }

    public ArrayList<Line> getEdges() {
        return this.edges;
    }
}
