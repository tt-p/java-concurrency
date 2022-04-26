package com.ttp.concurrency.banking.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountWithLock {
    private static final Lock lock = new ReentrantLock();
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        lock.lock();

        try {
            int newBalance = balance + amount;

            Thread.sleep(5);

            balance = newBalance;

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
