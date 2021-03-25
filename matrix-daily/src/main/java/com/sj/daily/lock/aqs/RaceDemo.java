package com.sj.daily.lock.aqs;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class RaceDemo {
    public static void main(String[] args) {
        //
        CyclicBarrier barrier  = new CyclicBarrier(8) ;
        Thread[] player  = new Thread[8] ;
        for (int i = 0; i < player.length; i++) {
            player[i] = new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName()+"准备好了");
                    barrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("选手起跑了："+Thread.currentThread().getName());

            },"player"+i+":");
            player[i].start();
        }
    }
}
