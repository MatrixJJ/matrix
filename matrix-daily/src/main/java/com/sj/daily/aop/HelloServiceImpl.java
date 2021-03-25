package com.sj.daily.aop;


import org.apache.commons.lang3.StringUtils;

public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
        if(StringUtils.isBlank(name)){
            throw new RuntimeException("name is null !") ;
        }
        System.out.println("hello"+name);
    }
}
