package com.kovizone.mybatispp.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kovizone.mybatispp.demo.mapper")
@SpringBootApplication
public class MybatisPpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPpDemoApplication.class, args);
    }
}
