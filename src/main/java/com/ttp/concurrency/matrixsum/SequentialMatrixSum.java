package com.ttp.concurrency.matrixsum;

public class SequentialMatrixSum implements MatrixSum {

    @Override
    public double[][] sum(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];

        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < a[0].length; col++) {
                result[row][col] = a[row][col] + b[row][col];
            }
        }

        return result;
    }
}
