package com.ttp.concurrency.banking.semaphore;

import java.util.concurrent.Semaphore;

public class BankAccountWithSemaphore {
    private static final Semaphore semaphore = new Semaphore(1);
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {

        try {
            semaphore.acquire();

            int newBalance = balance + amount;

            Thread.sleep(5);

            balance = newBalance;

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}