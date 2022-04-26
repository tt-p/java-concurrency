package com.ttp.concurrency.banking.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WithSemaphoreTest {

    private static final BankAccountWithSemaphore account = new BankAccountWithSemaphore();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            executor.execute(new AddAPennyTask());
        }

        executor.shutdown();

        while (!executor.isTerminated()) { }

        System.out.println("balance: " + account.getBalance());
    }

    public static class AddAPennyTask implements Runnable {
        public void run() {
            account.deposit(1);
        }
    }
}
