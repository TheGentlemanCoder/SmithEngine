package com.potatocode.engine.projection;

import java.awt.*;

class MatrixDimensionMismatch extends Exception {
    public MatrixDimensionMismatch(int matrixRows, int matrixCols,
                                   Dimension indices) {
        super("Exception: MatrixDimensionMismatch, matrix dimensions of " +
               matrixRows + " x " + matrixCols + " cannot be indexed by " +
               indices.width + " row, " + indices.height + " column.");
    }

    public MatrixDimensionMismatch(int matrixRows, int matrixCols,
                                   int input2DArrayRows, int input2DArrayCols) {
        super("Exception: MatrixDimensionMismatch, matrix dimensions of " +
               matrixRows + " x " + matrixCols + " cannot be filled by a 2D " +
               "array of dimension " + input2DArrayRows + " x " + input2DArrayCols + ".");
    }

    public MatrixDimensionMismatch(int matrixRows, int matrixCols, int vectorLength) {
        super("Exception: MatrixDimensionMismatch, matrix dimensions of " +
               matrixRows + " x " + matrixCols + " is not compatible for " +
               "multiplication with the vector length " + vectorLength + ".");
    }
}

public class Matrix {
    private double m[][];
    private int rows;
    private int cols;

    public Matrix(int rows, int cols, double initial) {
        this.rows = rows;
        this.cols = cols;
        this.m = new double[rows][cols];

        fill(initial);
    }

    public void fill(double value) {
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                this.m[i][j] = value;
            }
        }
    }

    public void fill(double[][] prefilledMatrix, int input2DArrayRows, int input2DArrayCols)
        throws MatrixDimensionMismatch {
        if (input2DArrayRows != this.rows || input2DArrayCols != this.cols) {
            throw new MatrixDimensionMismatch(this.rows, this.cols,
                                              input2DArrayCols, input2DArrayCols);
        }

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                this.m[i][j] = prefilledMatrix[i][j];
            }
        }
    }

    public Vector3D multiply(Vector3D vector) throws MatrixDimensionMismatch {
        // Since we're only using 3D vectors right now, our matrix must be 3x3
        if (this.rows != this.cols || this.rows != 3) {
            throw new MatrixDimensionMismatch(this.rows, this.cols, 3);
        }

        double newX = this.m[0][0] * vector.getX() + this.m[0][1] * vector.getY() + this.m[0][2] * vector.getZ();
        double newY = this.m[1][0] * vector.getX() + this.m[1][1] * vector.getY() + this.m[1][2] * vector.getZ();
        double newZ = this.m[2][0] * vector.getX() + this.m[2][1] * vector.getY() + this.m[2][2] * vector.getZ();

        return new Vector3D(newX, newY, newZ);
    }

    public void set(int row, int col, double value) throws MatrixDimensionMismatch {
        if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) {
            throw new MatrixDimensionMismatch(this.rows, this.cols, new Dimension(row, col));
        }

        this.m[row][col] = value;
    }
}
