package com.sj.daily.ioc;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Appconfig.class ) ;
        Person person  = ctx.getBean(BussinessPersion.class) ;
        person.services();
    }
}
