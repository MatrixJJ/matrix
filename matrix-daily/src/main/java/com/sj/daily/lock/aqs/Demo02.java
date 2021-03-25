package com.sj.daily.lock.aqs;

public class Demo02 {
    private MyLock myLock = new MyLock();
    public void a() {
        myLock.lock();
        System.out.println("a");
        b();
        myLock.unlock();
    }

    public void b() {
        //同一个锁 会等待释放后才加锁
        myLock.lock();
        System.out.println("b");
        myLock.unlock();
    }

    /**
     * a 和b是同一个锁 递归调用 使用mylock 只会打印b
     * @param args
     */
    public static void main(String[] args) {
        Demo02 demo02 = new Demo02();
        new Thread(() -> {
            demo02.a();
        }).start();
    }

}
