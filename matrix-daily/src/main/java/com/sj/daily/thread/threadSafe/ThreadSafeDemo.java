package com.sj.daily.thread.threadSafe;

import java.util.concurrent.CountDownLatch;

/**
 * @description: 三个线程同时执行。
 *
 * CountDownLatch：通过一个计数器来实现的，计数器的初始值为线程的数量；
 * 调用await()方法的线程会被阻塞，直到计数器 减到 0 的时候，才能继续往下执行；
 **/

public class ThreadSafeDemo {

    public int count = 0;

    public void add(){
        count ++;
    }

    public static void main(String[] args) throws InterruptedException {
        int size = 3;

        ThreadSafeDemo threadSafeDemo = new ThreadSafeDemo();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < size; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                    System.out.println(System.currentTimeMillis());
//                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(100);
        countDownLatch.countDown();
    }
}

