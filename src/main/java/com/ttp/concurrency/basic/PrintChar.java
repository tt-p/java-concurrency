package com.ttp.concurrency.basic;

public class PrintChar implements Runnable {

    private final char charToPrint;
    private final int times;

    public PrintChar(char c, int t) {
        charToPrint = c;
        times = t;
    }

    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.print(" " + i + charToPrint);
        }
    }
}
