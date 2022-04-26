package com.ttp.concurrency.banking.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WithSyncTest {
    private static final BankAccountWithSync account = new BankAccountWithSync();

    public static void main(String[] args) {
        System.out.println("Deposit Task\t\tWithdraw Task\t\tBalance");
        System.out.println("-----------------------------------------------");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new DepositTask());
        executor.execute(new WithdrawTask());
        executor.shutdown();
    }

    public static class DepositTask implements Runnable {
        public void run() {
            try {
                while (true) {
                    account.deposit((int) (Math.random() * 10) + 1);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class WithdrawTask implements Runnable {
        public void run() {
            while (true) {
                account.withdraw((int) (Math.random() * 10) + 1);
            }
        }
    }

}
