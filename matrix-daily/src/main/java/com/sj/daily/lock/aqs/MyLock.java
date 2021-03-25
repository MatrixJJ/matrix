package com.sj.daily.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {

    Helper helper = new Helper() ;

    private   class Helper extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            int state = getState() ;
            if(state ==0){
                //利用cas原理修改state
               if( compareAndSetState(0,arg)){
                   //设置当前线程占用资源
                   setExclusiveOwnerThread(Thread.currentThread());
                   return true ;

               }
            }else if (getExclusiveOwnerThread()==Thread.currentThread()){
                //当前线程是同一线程 重入性
                setState(getState()+arg);
                return true ;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            int state = getState()-arg;
            //判断释放后是否为0
            boolean flag = false ;
            if(state==0){
                setExclusiveOwnerThread(null);
                setState(state);
                return true ;

            }
            setState(state);
            return false;
        }
    }
    @Override
    public void lock() {
        helper.acquire(1) ;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return helper.tryAcquire(1);
    }

    /**
     * 自旋时间
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        helper.tryRelease(1) ;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
