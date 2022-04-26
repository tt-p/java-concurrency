package com.ttp.concurrency.sort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends QuickSort {

    @Override
    public void sort(int[] array) {
        RecursiveAction sortTask = new SortTask(array, 0, array.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(sortTask);
    }

    private class SortTask extends RecursiveAction {
        private final int THRESHOLD = 500;
        private final int[] array;
        private final int first;
        private final int last;

        SortTask(int[] array, int first, int last) {
            this.array = array;
            this.first = first;
            this.last = last;
        }

        @Override
        protected void compute() {
            if (array.length < THRESHOLD) {
                Arrays.sort(array);
            } else if (last > first) {
                int pivotIndex = partition(array, first, last);
                invokeAll(new SortTask(array, first, pivotIndex - 1), new SortTask(array, pivotIndex + 1, last));
            }
        }
    }
}
