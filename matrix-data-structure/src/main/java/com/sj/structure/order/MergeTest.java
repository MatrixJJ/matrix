package com.sj.structure.order;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 如何对一个字符串（很长）快速进行排序
 * juc包下的 Fork/Join框架
 * <p>
 * 拆分汇总
 */
public class MergeTest {

    private static int Max = 100;

    private static int ints[] = new int[100];

    static {
        Random r = new Random();
        for (int i = 0; i <= Max; i++) {
            ints[i - 1] = r.nextInt(1000);
        }
    }

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        MyTask task = new MyTask(ints);
        ForkJoinTask<int[]> taskResult = pool.submit(task);
        int[] ints = new int[0];
        try {
            ints = taskResult.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(ints));

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - beginTime));

    }


    static class MyTask extends RecursiveTask<int[]> {
        private int source[];

        public MyTask(int source[]) {
            this.source = source;
        }

        @Override
        protected int[] compute() {
            int sourceLen = source.length;
            if (sourceLen > 2) {
                int midIndex = sourceLen / 2;
                //拆分两个任务
                MyTask task1 = new MyTask(Arrays.copyOf(source, midIndex));
                task1.fork();
                MyTask task2 = new MyTask(Arrays.copyOfRange(source, midIndex, sourceLen));
                task2.fork();
                //合并两个有序数组
                int result1[] = task1.join();
                int result2[] = task2.join();
                int mer[] = joinInts(result1, result2);
                return mer;
            } else {
                if (sourceLen == 1 || source[0] <= source[1]) {
                    return source;
                } else {
                    int targetp[] = new int[sourceLen];
                    targetp[0] = source[1];
                    targetp[1] = source[0];
                    return targetp;
                }

            }
        }

        private static int[] joinInts(int arr1[], int arr2[]){
            int destInts[] = new int [arr1.length+arr2.length] ;
            int arr1Len = arr1.length ;
            int arr2Len = arr2.length ;
            return destInts ;
        }
    }


}
