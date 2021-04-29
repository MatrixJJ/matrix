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

    private static int inits[] = new int[Max];

    static {
        Random r = new Random();
        for (int i = 1; i <= Max; i++) {
            inits[i - 1] = r.nextInt(1000);
        }
    }

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        MyTask task = new MyTask(inits);
        ForkJoinTask<int[]> taskResult = pool.submit(task);
        int[] ints = new int[0];
        try {
            ints = taskResult.get();
            System.out.println(Arrays.toString(ints));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

        private static int[] joinInts(int array1[], int array2[]){
            int destInts[] = new int[array1.length + array2.length];
            int array1Len = array1.length;
            int array2Len = array2.length;
            int destLen = destInts.length;

            // 只需要以新的集合destInts的长度为标准，遍历一次即可
            for (int index = 0, array1Index = 0, array2Index = 0; index < destLen; index++) {
                int value1 = array1Index >= array1Len ? Integer.MAX_VALUE : array1[array1Index];
                int value2 = array2Index >= array2Len ? Integer.MAX_VALUE : array2[array2Index];
                // 如果条件成立，说明应该取数组array1中的值
                if (value1 < value2) {
                    array1Index++;
                    destInts[index] = value1;
                }
                // 否则取数组array2中的值
                else {
                    array2Index++;
                    destInts[index] = value2;
                }
            }

            return destInts;
        }
    }


}
