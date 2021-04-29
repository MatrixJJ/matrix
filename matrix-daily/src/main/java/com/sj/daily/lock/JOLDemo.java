package com.sj.daily.lock;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author ShengJie.Wang
 * @date 2021-04-29 14:02 JOL包：查看对象工具
 */
public class JOLDemo {

  public static void main(String[] args) {
    Object o = new Object();
    System.out.println(ClassLayout.parseInstance(0).toPrintable());

//        -XX:UseBiasedLocking : 是否打开偏向锁。默认不打开的。
//        -XX:BiasedLockingStartupDelay  默认是4秒。
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Object o2 = new Object();
    System.out.println(ClassLayout.parseInstance(o2).toPrintable());

//        synchronized (o){
//            System.out.println(ClassLayout.parseInstance(o).toPrintable());
//        }
  }

}
