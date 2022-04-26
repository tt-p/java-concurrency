package com.ttp.concurrency.sort;

public class SequentialMergeSort extends MergeSort {

    @Override
    public void sort(int[] array) {
        mergeSort(array);
    }

    public void mergeSort(int[] array) {
        if (array.length > 1) {

            int[] firstHalf = new int[array.length / 2];
            System.arraycopy(array, 0, firstHalf, 0, array.length / 2);
            mergeSort(firstHalf);

            int secondHalfLength = array.length - array.length / 2;
            int[] secondHalf = new int[secondHalfLength];
            System.arraycopy(array, array.length / 2, secondHalf, 0, secondHalfLength);
            mergeSort(secondHalf);

            merge(firstHalf, secondHalf, array);
        }
    }
}
