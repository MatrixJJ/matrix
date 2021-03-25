package com.sj.daily.lock.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class Cas1 {

    private static  volatile int m = 0 ;
    private static AtomicInteger atomicInteger1 = new AtomicInteger(0) ;

    public  static  void increase1(){
        m++ ; //三条指令
    }

    public  static  void increase2(){
        atomicInteger1.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] t = new Thread[20] ;
        for (int i = 0; i < 20; i++) {
            t[i] =new Thread(()->{
                Cas1.increase1();
            });
            t[i].start();
            t[i].join(); //join方法  group 线程有了交互性
        }

        System.out.println(m);
        Thread[] tf = new Thread[20] ;
        for (int i = 0; i < 20; i++) {
           tf[i] = new Thread(()->{
                Cas1.increase2();
            });
           tf[i].start();
           tf[i].join();
        }
        System.out.println("atomic"+atomicInteger1);
    }
}
