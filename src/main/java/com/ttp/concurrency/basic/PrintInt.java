package com.ttp.concurrency.basic;

public class PrintInt implements Runnable {
    private final int lastNum;

    public PrintInt(int n) {
        lastNum = n;
    }

    public void run() {
        Thread thread4 = new Thread(new PrintChar('c', 40));
        thread4.start();
        try {
            for (int i = 1; i <= lastNum; i++) {
                System.out.print(" " + i);
                if (i == 50) thread4.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
