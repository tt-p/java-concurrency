package com.ttp.concurrency.completablefuture;

import com.ttp.concurrency.util.SimpleDaemonThreadFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.*;

public class CompletableFutureTest {

    private static final Runnable printHello = () -> System.out.println("Hello!");
    private static final Runnable printHi = () -> System.out.println("Hi!");
    private static final Supplier<String> helloSupplier = () -> "Hello!";
    private static final UnaryOperator<String> getLengthOperator = (s) -> String.valueOf(s.length());
    private static final Supplier<String> firstHalfSupplier = () -> "First Half ";
    private static final Supplier<String> secondHalfSupplier = () -> "Second Half";
    private static final BiFunction<String, String, String> concatBiFunction = String::concat;
    private static final Consumer<String> printConsumer = System.out::println;
    private static final BiConsumer<String, String> concatAndPrintBiConsumer = (s1, s2) -> System.out.println(s1.concat(s2));

    private static final Executor runAsyncExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());
    private static final Executor thenRunAsyncExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());
    private static final Executor supplyAsyncExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());
    private static final Executor thenComposeExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());
    private static final Executor thenCombineExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());
    private static final Executor supplyAsyncAndThenAcceptExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());
    private static final Executor supplyAsyncAndThenAcceptBothExecutor = Executors.newSingleThreadExecutor(new SimpleDaemonThreadFactory());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1
        runTest(1,
                CompletableFuture
                        .runAsync(printHello)
        );


        //2
        runTest(2,
                CompletableFuture
                        .runAsync(printHello, runAsyncExecutor)
        );

        //3
        runTest(3,
                CompletableFuture
                        .runAsync(printHello)
                        .thenRun(printHi)
        );

        //4
        runTest(4,
                CompletableFuture
                        .runAsync(printHello, runAsyncExecutor)
                        .thenRunAsync(printHi, thenRunAsyncExecutor)
        );

        //5
        runTest(5,
                CompletableFuture
                        .runAsync(printHello)
                        .thenRunAsync(printHi)
        );

        //6
        runTest(6,
                CompletableFuture
                        .supplyAsync(helloSupplier)
        );

        //7
        runTest(7,
                CompletableFuture
                        .supplyAsync(helloSupplier, supplyAsyncExecutor)
        );

        //8
        runTest(8,
                CompletableFuture
                        .supplyAsync(helloSupplier)
                        .thenApply(getLengthOperator)
        );

        //9
        runTest(9,
                CompletableFuture
                        .supplyAsync(helloSupplier)
                        .thenCompose(result ->
                                CompletableFuture
                                        .supplyAsync(() -> getLengthOperator.apply(result)))
        );

        //10
        runTest(10,
                CompletableFuture
                        .supplyAsync(helloSupplier, supplyAsyncExecutor)
                        .thenCompose(result ->
                                CompletableFuture
                                        .supplyAsync(() -> getLengthOperator.apply(result), thenComposeExecutor))
        );

        //11
        runTest(11,
                CompletableFuture
                        .supplyAsync(firstHalfSupplier)
                        .thenCombine(
                                CompletableFuture.supplyAsync(secondHalfSupplier), concatBiFunction)
        );

        //12
        runTest(12,
                CompletableFuture
                        .supplyAsync(firstHalfSupplier, supplyAsyncExecutor)
                        .thenCombine(
                                CompletableFuture.supplyAsync(secondHalfSupplier, thenCombineExecutor), concatBiFunction)
        );

        //13
        runTest(13,
                CompletableFuture
                        .supplyAsync(helloSupplier)
                        .thenAccept(printConsumer)
        );

        //14
        runTest(14,
                CompletableFuture
                        .supplyAsync(helloSupplier, supplyAsyncAndThenAcceptExecutor)
                        .thenAccept(printConsumer)
        );

        //15
        runTest(15,
                CompletableFuture
                        .supplyAsync(firstHalfSupplier)
                        .thenAcceptBoth(CompletableFuture.supplyAsync(secondHalfSupplier), concatAndPrintBiConsumer)
        );

        //16
        runTest(16,
                CompletableFuture
                        .supplyAsync(firstHalfSupplier, supplyAsyncExecutor)
                        .thenAcceptBoth(CompletableFuture.supplyAsync(secondHalfSupplier, supplyAsyncAndThenAcceptBothExecutor), concatAndPrintBiConsumer)
        );
    }

    private static void runTest(int testNo, CompletableFuture future) throws InterruptedException, ExecutionException {
        System.out.printf("Test %d%n", testNo);
        Object result = future.get();
        if (result != null) {
            System.out.printf("%s%n", result);
        }
        System.out.printf("%n");
    }

}
