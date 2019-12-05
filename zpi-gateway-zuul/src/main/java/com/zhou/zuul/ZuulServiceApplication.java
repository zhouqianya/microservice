package com.zhou.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 12:45
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceApplication.class, args);

    }
}
