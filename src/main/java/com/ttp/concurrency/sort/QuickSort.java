package com.ttp.concurrency.sort;

public abstract class QuickSort implements SortMethod {

    protected int partition(int[] array, int first, int last) {
        int pivot = array[first];
        int low = first + 1;
        int high = last;

        while (high > low) {
            while (low <= high && array[low] <= pivot) {
                low++;
            }

            while (low <= high && array[high] > pivot) {
                high--;
            }

            if (high > low) {
                int temp = array[high];
                array[high] = array[low];
                array[low] = temp;
            }
        }

        while (high > first && array[high] >= pivot) {
            high--;
        }


        if (pivot > array[high]) {
            array[first] = array[high];
            array[high] = pivot;
            return high;
        } else {
            return first;
        }
    }
}
