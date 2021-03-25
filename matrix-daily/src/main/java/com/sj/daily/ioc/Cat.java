package com.sj.daily.ioc;

import org.springframework.stereotype.Component;

@Component
public class Cat implements  Animal{
    @Override
    public void use() {
        System.out.println("猫"+Cat.class.getSimpleName()+"是抓老鼠");
    }
}
