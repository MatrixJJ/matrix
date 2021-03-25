package com.sj.daily.ioc;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = "com.sj.demo.*" ,  lazyInit = true) 延迟加载
@ComponentScan
public class Appconfig {

}
