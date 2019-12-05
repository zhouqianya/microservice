package com.zhou.course;

import com.zhou.course.filter.CourseFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.FilterRegistration;
import java.util.ArrayList;
import java.util.List;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 12:45
 */
@SpringBootApplication
public class CourseEdgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseEdgeServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        CourseFilter courseFilter = new CourseFilter();
        filterRegistrationBean.setFilter(courseFilter);

        List<String> url = new ArrayList<>();
        url.add("/*");

        filterRegistrationBean.setUrlPatterns(url);

        return filterRegistrationBean;
    }

}
