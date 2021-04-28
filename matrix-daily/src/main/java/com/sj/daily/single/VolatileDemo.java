package com.sj.daily.single;

import java.util.concurrent.TimeUnit;

/**
 * 保证了线程到可见性
 */
public class VolatileDemo {
    /**
     * 不加volatile 则线程感知不到flag到变化，线程while不会停止
     */
    public static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (flag) {

            }
            System.out.println("--------End of" + Thread.currentThread().getName() + "--------");
        }).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "------flag turn off");
        flag = false;
    }
}
