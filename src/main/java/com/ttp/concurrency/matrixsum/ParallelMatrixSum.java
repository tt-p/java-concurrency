package com.ttp.concurrency.matrixsum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMatrixSum implements MatrixSum {

    @Override
    public double[][] sum(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        RecursiveAction task = new SumTask(a, b, result);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        return result;
    }

    private class SumTask extends RecursiveAction {
        private final double[][] a;
        private final double[][] b;
        private final double[][] c;

        public SumTask(double[][] a, double[][] b, double[][] c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public void compute() {
            RecursiveAction[] tasks = new RecursiveAction[a.length];
            for (int row = 0; row < a.length; row++) {
                tasks[row] = new AddOneRow(row);
            }

            invokeAll(tasks);
        }

        public class AddOneRow extends RecursiveAction {
            int row;

            public AddOneRow(int row) {
                this.row = row;
            }

            @Override
            public void compute() {
                for (int col = 0; col < a[0].length; col++) {
                    c[row][col] = a[row][col] + b[row][col];
                }
            }
        }
    }
}
