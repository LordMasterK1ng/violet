package com.yc.violet.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author 姚琛
 * @description 自定义线程协作工具, CountDownLatch简易实现,仅需要调用一次countDownLatch，即可释放所有阻塞线程
 * 1.state为AQS中的一个标量，不同的线程协作工具中，对它的定义不同（CountDownLatch中为倒数的次数，Semaphore中为令牌总数，ReentrantLock中为锁是否为占用状态和重入次数）
 * 2.AQS中包含控制线程抢锁、配合的FIFO队列
 * 3.相关类中需要去实现自己的协作逻辑，根据需要去重写tryAcquire(tryAcquireShared)/tryRelease(tryReleaseShared)方法.
 * @date 2021/12/21
 */
public class EasyLatch {

    private Sync sync;

    public EasyLatch() {
        sync = new Sync();
    }

    public EasyLatch(int count) {
        sync = new Sync(count);
    }

    public void await() {
        sync.acquireShared(0);
    }

    public void countDown() {
        sync.releaseShared(0);
    }

    static class Sync extends AbstractQueuedSynchronizer {

        public Sync() {

        }

        public Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(0);
            return true;
//            for (; ; ) {
//                int state = getState();
//                if (state == 0)
//                    return false;
//                int newValue = state - 1;
//                if (compareAndSetState(state, newValue))
//                    return newValue == 0;
//            }
        }
    }
}
