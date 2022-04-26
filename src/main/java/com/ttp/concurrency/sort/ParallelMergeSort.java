package com.ttp.concurrency.sort;


import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends MergeSort {

    @Override
    public void sort(int[] array) {
        parallelMergeSort(array);
    }

    public void parallelMergeSort(int[] array) {
        RecursiveAction sortTask = new SortTask(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(sortTask);
    }

    private class SortTask extends RecursiveAction {
        private final int THRESHOLD = 500;
        private final int[] array;

        SortTask(int[] array) {
            this.array = array;
        }

        @Override
        protected void compute() {
            if (array.length < THRESHOLD) {
                Arrays.sort(array);
            } else {

                int[] firstHalf = new int[array.length / 2];
                System.arraycopy(array, 0, firstHalf, 0, array.length / 2);

                int secondHalfLength = array.length - array.length / 2;
                int[] secondHalf = new int[secondHalfLength];
                System.arraycopy(array, array.length / 2, secondHalf, 0, secondHalfLength);

                invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));

                merge(firstHalf, secondHalf, array);
            }
        }
    }
}
