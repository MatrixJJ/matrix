package com.sj.daily.single;

/**
 * 实现了懒加载
 */
public class EnumDemo {
//    Instance ;
//    public  static  EnumDemo getInstance(){
//        return Instance ;
//    }
    private EnumDemo() {
    }

    private enum EnumHolder {
        INSTANCE;
        private EnumDemo instance;

        EnumHolder() {
            this.instance = new EnumDemo();
        }

        private EnumDemo getInstance() {
            return instance;
        }

    }


    private EnumDemo getInstance() {
        return EnumHolder.INSTANCE.getInstance();
    }

}
