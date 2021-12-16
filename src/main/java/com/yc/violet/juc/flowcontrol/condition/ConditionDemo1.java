package com.yc.violet.juc.flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 姚琛
 * @description Condition用于线程之间的通讯，某条件不满足时调用await进入阻塞，待其他线程处理完毕，调用signall(all)进行唤醒
 * @date 2021/12/14
 */
public class ConditionDemo1 {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo1 demo1 = new ConditionDemo1();
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                demo1.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        demo1.method1();

    }

    private void method1() {
        lock.lock();
        try {
            System.out.println("条件不满足，开始等待");
            condition.await();
            System.out.println("等待线程已唤醒，完成后续工作");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void method2() {
        lock.lock();
        try {
            System.out.println("数据处理完毕，条件满足，唤醒等待线程");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
