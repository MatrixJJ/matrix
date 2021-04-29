package com.sj.daily.thread.threadSafe;

import java.util.concurrent.Semaphore;

/**

 * @description: 三个线程交替执行
 *
 * Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。
 *
 * Semaphore的主要方法摘要：
 *
 * 　　void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
 *
 * 　　void release():释放一个许可，将其返回给信号量。
 *
 * 　　int availablePermits():返回此信号量中当前可用的许可数。
 *
 * 　　boolean hasQueuedThreads():查询是否有线程正在等待获取。
 **/

public class OrderThread {
    //利用信号量来限制
    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(1);
    private static Semaphore s3 = new Semaphore(1);

    public static void main(String[] args) {

        try {
            //首先调用s2为 acquire状态
            s1.acquire();
            s2.acquire();
//			s2.acquire();  调a用s1或者s2先占有一个
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        new Thread(()->{
            while(true) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                s2.release();
            }
        }).start();

        new Thread(()->{
            while(true) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                s3.release();
            }
        }).start();

        new Thread(()->{
            while(true) {
                try {
                    s3.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
                s1.release();
            }
        }).start();
    }
}
