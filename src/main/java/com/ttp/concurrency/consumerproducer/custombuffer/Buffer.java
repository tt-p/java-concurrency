package com.ttp.concurrency.consumerproducer.custombuffer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private static final int CAPACITY = 1;
    private static final Lock lock = new ReentrantLock();
    private static final Condition notEmpty = lock.newCondition();
    private static final Condition notFull = lock.newCondition();
    private final LinkedList<Integer> queue = new LinkedList<>();

    public void write(int value) {
        lock.lock();

        try {

            while (queue.size() == CAPACITY) {
                System.out.println("Wait for notFull condition");
                notFull.await();
            }

            queue.offer(value);
            notEmpty.signal();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int read() {
        int value = 0;
        lock.lock();

        try {

            while (queue.isEmpty()) {
                System.out.println("\t\t\t\t\t\t\tWait for notEmpty condition");
                notEmpty.await();
            }

            value = queue.remove();
            notFull.signal();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return value;
    }
}
