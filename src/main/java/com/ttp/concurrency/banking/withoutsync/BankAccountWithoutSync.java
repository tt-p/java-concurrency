package com.ttp.concurrency.banking.withoutsync;

public class BankAccountWithoutSync {
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        int newBalance = balance + amount;
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        balance = newBalance;
    }
}
