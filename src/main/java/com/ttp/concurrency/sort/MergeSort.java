package com.ttp.concurrency.sort;

public abstract class MergeSort implements SortMethod {

    protected void merge(int[] firstHalf, int[] secondHalf, int[] temp) {
        int firstIndex = 0;
        int secondIndex = 0;
        int tempIndex = 0;

        while (firstIndex < firstHalf.length && secondIndex < secondHalf.length) {
            if (firstHalf[firstIndex] < secondHalf[secondIndex]) {
                temp[tempIndex++] = firstHalf[firstIndex++];
            } else {
                temp[tempIndex++] = secondHalf[secondIndex++];
            }
        }

        while (firstIndex < firstHalf.length) {
            temp[tempIndex++] = firstHalf[firstIndex++];
        }

        while (secondIndex < secondHalf.length) {
            temp[tempIndex++] = secondHalf[secondIndex++];
        }
    }

}
