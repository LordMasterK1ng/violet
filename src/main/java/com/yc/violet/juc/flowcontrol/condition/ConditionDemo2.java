package com.yc.violet.juc.flowcontrol.condition;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 姚琛
 * @description Condition：n.条件; 状态; 状况。
 * 实现生产者消费者模式
 * @date 2021/12/14
 */
public class ConditionDemo2 {
    private Lock lock = new ReentrantLock();
    private Condition p = lock.newCondition();
    private Condition c = lock.newCondition();
    private int capacity = 100;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue(capacity);

    public static void main(String[] args) {
        ConditionDemo2 demo = new ConditionDemo2();
        Producer producer = demo.new Producer();
        Consumer consumer = demo.new Consumer();
        producer.start();
        consumer.start();
    }

    class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == capacity) {
                        try {
                            System.out.println("队列已满，等待消费，生产者等待");
                            p.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    System.out.println("生产成功，当前队列容量为：" + queue.size());
                    c.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        try {
                            System.out.println("队列为空，等待生产，消费者等待");
                            c.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("消费成功，当前队列容量为：" + queue.size());
                    p.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
