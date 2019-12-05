package com.zhou.course.filter;

import com.zhou.thrift.user.dto.UserDto;
import com.zhou.user.LoginFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/04 17:25
 */
@Slf4j
public class CourseFilter extends LoginFilter {

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDto userDto) {
        log.info("CourseFilter login={}",userDto);
        request.setAttribute("user",userDto);
    }
}
