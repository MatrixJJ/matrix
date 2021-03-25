package com.sj.daily.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachePoolDemo {
    public static void main(String[] args) {
        //可变的
        ExecutorService pools= Executors.newCachedThreadPool() ;
        for (int i = 0; i < 10; i++) {
            //创建任务
            Runnable task = new TaskDemo() ;
            //交给pool执行
            pools.execute(task);
        }
        //关闭
        pools.shutdown();
    }

}
