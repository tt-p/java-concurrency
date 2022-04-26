package com.ttp.concurrency.sort;

import com.ttp.concurrency.util.Functions;
import com.ttp.concurrency.util.TestResult;

import java.util.Arrays;

public class SortingTest {

    public static void main(String[] args) {

        final int SIZE = 50_000_000;
        int[] array1 = new int[SIZE];
        int[] array2 = new int[SIZE];
        int[] array3 = new int[SIZE];
        int[] array4 = new int[SIZE];
        int[] array5 = new int[SIZE];
        int[] array6 = new int[SIZE];

        for (int i = 0; i < array1.length; i++) {
            array1[i] = array2[i] = array3[i] = array4[i] = array5[i] = array6[i] = (int) (Math.random() * 10_000_000);
        }

        SortMethod sequentialMergeSort = new SequentialMergeSort();
        SortMethod parallelMergeSort = new ParallelMergeSort();
        SortMethod sequentialQuickSort = new SequentialQuickSort();
        SortMethod parallelQuickSort = new ParallelQuickSort();

        TestResult<Void> mergeSortSequentialResult = Functions.testConsumer(array1, sequentialMergeSort::sort);
        System.out.println("\nMerge Sort sequential runtime is " + mergeSortSequentialResult.runtime() + " milliseconds");

        TestResult<Void> mergeSortParallelResult = Functions.testConsumer(array2, parallelMergeSort::sort);
        System.out.println("\nMerge Sort Parallel runtime is " + mergeSortParallelResult.runtime() + " milliseconds");

        TestResult<Void> quickSortSequentialResult = Functions.testConsumer(array3, sequentialQuickSort::sort);
        System.out.println("\nQuick Sort sequential runtime is " + quickSortSequentialResult.runtime() + " milliseconds");

        TestResult<Void> quickSortParallelResult = Functions.testConsumer(array4, parallelQuickSort::sort);
        System.out.println("\nQuick Sort Parallel runtime is " + quickSortParallelResult.runtime() + " milliseconds");

        TestResult<Object> sequentialStreamResult = Functions.testFunction(array5, input -> Arrays.stream(input).sorted().toArray());
        System.out.println("\nSequential Stream runtime is " + sequentialStreamResult.runtime() + " milliseconds");

        TestResult<Object> parallelStreamResult = Functions.testFunction(array6, input -> Arrays.stream(input).parallel().sorted().toArray());
        System.out.println("\nParallel Stream runtime is " + parallelStreamResult.runtime() + " milliseconds");

    }

}
