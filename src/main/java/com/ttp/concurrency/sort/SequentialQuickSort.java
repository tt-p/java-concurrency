package com.ttp.concurrency.sort;

public class SequentialQuickSort extends QuickSort {

    @Override
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(array, first, last);
            quickSort(array, first, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, last);
        }
    }
}
