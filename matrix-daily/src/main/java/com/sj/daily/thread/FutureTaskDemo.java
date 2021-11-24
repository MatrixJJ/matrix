package com.sj.daily.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author sj
 * @Date 2021/11/23 10:28 下午
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> call = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                return 1;
            }
        } ;
        FutureTask<Integer> task  =new FutureTask(call) ;

        Thread thread = new Thread(task) ;
        thread.start();
        System.out.println(task.isDone());
        System.out.println("------");
        Integer s = task.get();
        System.out.println(task.isDone());
        System.out.println("----result:" +s);

    }
}
