package com.yc.violet.juc.flowcontrol.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 姚琛
 * @description Semaphore：n.信号标; 旗语;
 * 可理解为“许可证”,对于某些重量级业务，耗时长。对此，不希望同时有过多的线程同时访问，可使用semaphore来做限制。
 * semaphore的aquire()和release()，没有严格限制必须同一线程来调用。
 * “轻量级的CountDownLatch”：Thread1调用aquire()，Thread2调用relase()，合理设置参数，使得Thread2执行完毕后，交由Thread1执行。
 * @date 2021/12/13
 */
public class SemaphoreDemo1 {
    static Semaphore semaphore = new Semaphore(3);
    private static final int THREAD_COUNT = 50;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.submit(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "正在执行慢操作");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "慢操作执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }
    }
}
