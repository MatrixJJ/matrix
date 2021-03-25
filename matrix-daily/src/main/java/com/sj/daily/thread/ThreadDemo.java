package com.sj.daily.thread;

import java.util.concurrent.Executor;

public class ThreadDemo {
    private Executor poor = new Executor() {
        @Override
        public void execute(Runnable command) {

        }
    };
}
