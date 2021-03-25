package com.sj.daily.lock.aqs;

public class Demo01 {
    private MyLock  myLock = new MyLock() ;


    private  int m = 0 ;

    public  int next(){
        myLock.lock();
        try {
            return m++ ;
        }finally {
            myLock.unlock();
        }
    }

    public static void main(String[] args) {

        Demo01 demo01  = new Demo01() ;
        Thread[] th = new Thread[20] ;
        for (int i = 0; i < 20; i++) {
            th[i] = new Thread(()->{
                System.out.println(demo01.next());
            });
            th[i].start();
        }
    }
}
