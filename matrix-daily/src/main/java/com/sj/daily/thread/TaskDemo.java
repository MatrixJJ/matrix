package com.sj.daily.thread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TaskDemo implements  Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"    isRunning" );
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
