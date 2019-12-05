package com.zhou.course;

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
@MapperScan("com.zhou.course.mapper")
public class CourseServiceApplication {

    public static void main(String[] args) {

//        System.setProperty("java.net.preferIPv4Stack", "true");
        SpringApplication.run(CourseServiceApplication.class, args);
    }
}
