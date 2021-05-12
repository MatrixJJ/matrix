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
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < size; i++) {
            new Thread(()->{
                try {
                    System.out.println("前"+System.currentTimeMillis());
                    countDownLatch.await();
                    System.out.println("后"+System.currentTimeMillis());
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(3000);
        countDownLatch.countDown();
    }
}

