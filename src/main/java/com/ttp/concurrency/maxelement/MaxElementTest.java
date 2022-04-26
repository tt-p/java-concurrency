package com.ttp.concurrency.maxelement;

import com.ttp.concurrency.util.Functions;
import com.ttp.concurrency.util.TestResult;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaxElementTest {

    public static void main(String[] args) {

        final int N = 1_000_000_000;
        int[] array = new int[N];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        TestResult<Integer> sequentialMaxResult = Functions.testFunction(array, MaxElementTest::sequentialMax);
        System.out.println("\nSequentialMax runtime is " + sequentialMaxResult.runtime() + " milliseconds");

        TestResult<Integer> parallelMaxResult = Functions.testFunction(array, MaxElementTest::parallelMax);
        System.out.println("\nParallelMax runtime is " + parallelMaxResult.runtime() + " milliseconds");

        TestResult<Integer> sequentialStreamResult = Functions.testFunction(array, input -> Arrays.stream(input).max().orElse(-1));
        System.out.println("\nSequential Stream runtime is " + sequentialStreamResult.runtime() + " milliseconds");

        TestResult<Integer> parallelStreamResult = Functions.testFunction(array, input -> Arrays.stream(input).parallel().max().orElse(-1));
        System.out.println("\nParallel Stream runtime is " + parallelStreamResult.runtime() + " milliseconds");
    }

    public static int sequentialMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (Integer integer : array) {
            if (integer > max) {
                max = integer;
            }
        }
        return max;
    }

    public static int parallelMax(int[] array) {
        RecursiveTask<Integer> task = new MaxElementTask(array, 0, array.length);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(task);
    }

    private static class MaxElementTask extends RecursiveTask<Integer> {
        private final static int THRESHOLD = 1000;
        private final int[] array;
        private final int low;
        private final int high;

        public MaxElementTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        public Integer compute() {
            if (high - low < THRESHOLD) {
                int max = array[0];
                for (int i = low; i < high; i++)
                    if (array[i] > max)
                        max = array[i];
                return max;
            } else {
                int mid = (low + high) / 2;
                RecursiveTask<Integer> left = new MaxElementTask(array, low, mid);
                RecursiveTask<Integer> right = new MaxElementTask(array, mid, high);

                right.fork();
                left.fork();
                return Math.max(left.join(), right.join());
            }
        }
    }
}
