package com.sj.daily.lock.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Cas2 {


    private static AtomicInteger atomicInteger1 = new AtomicInteger(100);
    private  static AtomicStampedReference asr = new AtomicStampedReference(100,1) ;

    /**
     * ABA问题 ：检测不到中间值变化
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(()->{
//            System.out.println(atomicInteger1.compareAndSet(100,110) );
//            //System.out.println(atomicInteger1.get());
//
//        }) ;
//        t1.start();
//        Thread t2= new Thread(()->{
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println( atomicInteger1.compareAndSet(110,100) );
//          //  System.out.println(atomicInteger1.get());
//
//        }) ;
//        t2.start();
//        Thread t3= new Thread(()->{
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(atomicInteger1.compareAndSet(100,120) );
//          //  System.out.println("3:"+atomicInteger1.get());
//
//        }) ;
//        t3.start();
       // System.out.println("atomic" + atomicInteger1);
        System.out.println("------------------------------");
        Thread tf1 = new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(asr.compareAndSet(100,110, asr.getStamp(), asr.getStamp()+1));
        }) ;

        Thread tf2 = new Thread(()->{
            //获取到前面到版本
            int stamp = asr.getStamp() ;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(asr.compareAndSet(100,120, stamp, stamp+1));
        }) ;
        tf1.start();
        tf2.start();
    }
}
