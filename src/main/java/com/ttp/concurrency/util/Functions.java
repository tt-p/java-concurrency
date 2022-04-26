package com.ttp.concurrency.util;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Functions {

    public static <T, U, R> TestResult<R> testBiFunction(T input1, U input2, BiFunction<T, U, R> function) {
        System.gc();
        long startTime = System.currentTimeMillis();
        R result = function.apply(input1, input2);
        long endTime = System.currentTimeMillis();
        return new TestResult<>(result, endTime - startTime);
    }

    public static <T, R> TestResult<R> testFunction(T input, Function<T, R> function) {
        System.gc();
        long startTime = System.currentTimeMillis();
        R result = function.apply(input);
        long endTime = System.currentTimeMillis();
        return new TestResult<>(result, endTime - startTime);
    }

    public static <T, R> TestResult<R> testConsumer(T input, Consumer<T> consumer) {
        System.gc();
        long startTime = System.currentTimeMillis();
        consumer.accept(input);
        long endTime = System.currentTimeMillis();
        return new TestResult<>(null, endTime - startTime);
    }

}
