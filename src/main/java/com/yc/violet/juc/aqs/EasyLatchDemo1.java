package com.yc.violet.juc.aqs;

/**
 * @author 姚琛
 * @description
 * @date 2021/12/21
 */
public class EasyLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        EasyLatch easyLatch = new EasyLatch();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "完成准备工作，开始等待");
                easyLatch.await();
                System.out.println(Thread.currentThread().getName() + "已唤醒，完成后续工作");
            }).start();
        }
        Thread.sleep(3000);
        easyLatch.countDown();
        System.out.println("所有等待线程均已唤醒");
    }
}
