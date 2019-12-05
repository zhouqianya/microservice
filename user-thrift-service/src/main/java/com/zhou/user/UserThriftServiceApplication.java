package com.zhou.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 12:45
 */
@SpringBootApplication
@MapperScan("com.zhou.user.mapper")
public class UserThriftServiceApplication {


    public static void main(String[] args) {

        SpringApplication.run(UserThriftServiceApplication.class, args);
    }
}
