package com.sj.daily.single;

public class HoonSingleDemo {

    private  static  HoonSingleDemo instance =null ;

    private HoonSingleDemo(){

    }

    public  static  HoonSingleDemo getInstance(){
        if(null==instance){
            synchronized (HoonSingleDemo.class){
                if(null==instance){
                    instance = new HoonSingleDemo() ;
                }
            }
        }
        return instance ;
    }

    /**
     * happen- before 指令重排  实例化对象顺序会指令重排， 引起空指针异常
     * private  volatile static  HoonSingleDemo instance =null ;
     */




}
