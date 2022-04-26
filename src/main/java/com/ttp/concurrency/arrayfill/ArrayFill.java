package com.ttp.concurrency.arrayfill;

import com.ttp.concurrency.util.Functions;
import com.ttp.concurrency.util.TestResult;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ArrayFill {

    public static void main(String[] args) {

        final int N = 500_000_000;
        double[] array = new double[N];

        TestResult<Void> sequentialResult = Functions.testConsumer(array, ArrayFill::sequentialAssignValues);
        System.out.println("Sequential runtime is " + sequentialResult.runtime() + " milliseconds");

        TestResult<Void> parallelResult = Functions.testConsumer(array, ArrayFill::parallelAssignValues);
        System.out.println("Parallel runtime is " + parallelResult.runtime() + " milliseconds");
    }

    public static void sequentialAssignValues(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.random() * 100000;
        }
    }

    public static void parallelAssignValues(double[] array) {
        RecursiveAction task = new ParallelAssign(array, 0, array.length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
    }

    public static class ParallelAssign extends RecursiveAction {
        private final static int THRESHOLD = 10000;
        private final double[] list;
        private final int low;
        private final int high;

        public ParallelAssign(double[] list, int low, int high) {
            this.list = list;
            this.low = low;
            this.high = high;
        }

        @Override
        public void compute() {
            Random rand = new Random();
            if (high - low < THRESHOLD) {
                for (int i = low; i < high; i++) {
                    list[i] = rand.nextDouble();
                }
            } else {
                int mid = (low + high) / 2;
                invokeAll(new ParallelAssign(list, low, mid), new ParallelAssign(list, mid, high));
            }
        }
    }
}
