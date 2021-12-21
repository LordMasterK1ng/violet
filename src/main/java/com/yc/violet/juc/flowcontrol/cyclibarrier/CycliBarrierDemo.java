package com.yc.violet.juc.flowcontrol.cyclibarrier;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 姚琛
 * @description 与CountDownLatch用法类似，所有相关线程均需要等待至条件满足（CountDownLatch倒数至0，CyclicBarrier有一定数量的线程执行await）,
 * 在此之前，所有线程均会进入阻塞状态。
 * 示例：举行会议时，需要所有人均到场且在规定时间点才能展开，在条件满足前，其他人都需要等待
 * @date 2021/12/20
 */
public class CycliBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("会议相关人员全部到齐，会议稍后开始开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < 5; i++) {
            new Thread(new Member(i, cyclicBarrier)).start();
        }
    }

    static class Member implements Runnable {
        private int i;
        private CyclicBarrier cyclicBarrier;

        public Member(int i, CyclicBarrier cyclicBarrier) {
            this.i = i;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("成员" + i + "开始出发");
            try {
                Thread.sleep(RandomUtil.randomLong(1000, 3000));
                System.out.println("成员" + i + "到达会议地址");
                cyclicBarrier.await();
                System.out.println("成员" + i + "开始参与会议");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
