package com.ttp.concurrency.banking.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WithLockTest {

    private static final BankAccountWithLock account = new BankAccountWithLock();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            executor.execute(new AddAPennyTask());
        }

        executor.shutdown();

        while (!executor.isTerminated()) { }

        System.out.println("balance: " + account.getBalance());
    }

    private static class AddAPennyTask implements Runnable {
        public void run() {
            account.deposit(1);
        }
    }

}
