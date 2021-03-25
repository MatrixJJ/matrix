package com.sj.daily.aop;

public class HelloTest {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService, new MyInterceptor());
        proxy.sayHello("JJ");
        System.out.println("#####name is null ######");
        proxy.sayHello(null);
    }
}




























