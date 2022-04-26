package com.ttp.concurrency.matrixsum;

import com.ttp.concurrency.util.Functions;
import com.ttp.concurrency.util.TestResult;

import java.util.Arrays;

public class MatrixSumTest {

    public static final int N = 10_000;

    public static void main(String[] args) {

        double[][] matrix1 = createRandomMatrix();
        double[][] matrix2 = createRandomMatrix();

        MatrixSum sequentialMatrixSum = new SequentialMatrixSum();
        MatrixSum parallelMatrixSum = new ParallelMatrixSum();

        TestResult<double[][]> sequentialResult = Functions.testBiFunction(matrix1, matrix2, sequentialMatrixSum::sum);
        System.out.println("Sequential runtime is " + sequentialResult.runtime() + " milliseconds");

        TestResult<double[][]> parallelResult = Functions.testBiFunction(matrix1, matrix2, parallelMatrixSum::sum);
        System.out.println("Parallel runtime is " + parallelResult.runtime() + " milliseconds");
    }

    private static double[][] createRandomMatrix() {
        double[][] matrix = new double[N][N];
        for (double[] row : matrix) {
            Arrays.fill(row, Math.random());
        }
        return matrix;
    }

}
