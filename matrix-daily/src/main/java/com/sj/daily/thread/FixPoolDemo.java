package com.sj.daily.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 */
public class FixPoolDemo {
    public static void main(String[] args) {
        //固定大小线程池
        ExecutorService pool = Executors.newFixedThreadPool(5) ;
        for (int i = 0; i < 10; i++) {
            //创建任务
            Runnable task = new TaskDemo() ;
            //交给pool执行
            pool.execute(task);
        }
        //关闭
        pool.shutdown();
    }
}
