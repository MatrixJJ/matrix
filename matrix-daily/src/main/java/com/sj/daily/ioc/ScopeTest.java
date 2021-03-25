package com.sj.daily.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScopeTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Appconfig.class) ;
        ScopeBean bean1 = ctx.getBean(ScopeBean.class);
        ScopeBean bean2 = ctx.getBean(ScopeBean.class);
        System.out.println(bean1 ==bean2);
    }
}
