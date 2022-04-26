package com.ttp.concurrency.synchronizedset;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SynchronizedSetTest {
    private static final Set<Integer> hashSet = Collections.synchronizedSet(new HashSet<>());

    private static final int N = 5;

    public static void main(String[] args) {
        new Thread(new Task1()).start();
        new Thread(new Task2()).start();
    }

    static class Task1 implements Runnable {
        public void run() {
            for (int i = 0; i < N; i++) {

                System.out.println("Thread 1");

                hashSet.add(i);
                System.out.println("Hashset updated\n");

                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    static class Task2 implements Runnable {

        public void run() {
            do {
                System.out.println("Thread 2");

                synchronized (hashSet) {
                    hashSet.forEach(System.out::println);
                }
                System.out.println("Hashset printed\n");

                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (hashSet.size() < N);
        }
    }


}
