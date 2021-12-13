package com.yc.violet.juc.flowcontrol.countdownlatch;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 姚琛
 * @description 顾名思义 "倒数 门闩",初始化count作为总数,线程调用countDown使总数减一，调用await方法使当前线程进入阻塞状态，
 * 当计数倒数至0时，调用过await方法的线程即可被唤醒
 * 此示例模拟跑步比赛，使用两种逻辑
 * 1. 所有运动员需要在起点准备，同时等待裁判的开始信号.(多等一,运动员await，裁判countDown)
 * 2. 比赛开始后，工作人员需要等待最后一名运动员到达终点，比赛结束.(一等多，运动员countDown，比赛或工作人员await)
 * 实际使用场景中会出现更复杂的情况，如多对多，根据业务合理使用countDown，await即可
 * @date 2021/12/13
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            pool.submit(() -> {
                try {
                    Thread.sleep(RandomUtil.randomLong(1000, 3000));
                    System.out.println("No." + no + "运动员准备就绪");
                    begin.await();//准备完毕等待信号枪响||当前线程进入阻塞状态
                    System.out.println("No." + no + "运动员开始跑步");
                    Thread.sleep(RandomUtil.randomLong(7000, 10000));
                    System.out.println("No." + no + "运动员到达终点");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();//当前运动员到达终点
                }

            });
        }
        System.out.println("裁判综合检查中....");
        Thread.sleep(5000);
        System.out.println("比赛开始");
        begin.countDown();//检查就绪，所有人准备就绪，裁判枪响，比赛开始||倒计时结束，唤醒所有阻塞线程
        end.await();//等待最后一名运动员到达终点，比赛结束||当前线程进入阻塞状态，等待CountDownLatch倒数完毕
        System.out.println("比赛结束");//倒数完毕，当前线程被唤醒
    }
}
