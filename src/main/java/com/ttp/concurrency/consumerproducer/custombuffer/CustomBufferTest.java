package com.ttp.concurrency.consumerproducer.custombuffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomBufferTest {

    private static final Buffer buffer = new Buffer();

    public static void main(String[] args) {
        System.out.println("Producer\t\t\t\t\tConsumer");
        System.out.println("--------\t\t\t\t\t--------");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new ProducerTask());
        executor.execute(new ConsumerTask());
        executor.shutdown();
    }

    private static class ProducerTask implements Runnable {
        public void run() {
            try {
                int i = 1;
                while (true) {
                    System.out.println("Producer writes " + i);
                    buffer.write(i++);
                    Thread.sleep((int) (Math.random() * 1000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class ConsumerTask implements Runnable {
        public void run() {
            try {
                while (true) {
                    System.out.println("\t\t\t\t\t\t\tConsumer reads " + buffer.read());
                    Thread.sleep((int) (Math.random() * 1000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}