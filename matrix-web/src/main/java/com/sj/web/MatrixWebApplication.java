package com.sj.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.sj.*.mapper")
public class MatrixWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(MatrixWebApplication.class, args);
  }

}
