package com.ttp.concurrency.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExecutor {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new PrintChar('a', 100));
        executor.execute(new PrintChar('b', 100));
        executor.execute(new PrintInt(100));

        executor.shutdown();
    }
}