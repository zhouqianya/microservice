package com.zhou.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhou.course.service.CourseDTO;
import com.zhou.course.service.ICourseService;
import com.zhou.thrift.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/04 16:22
 */
@Controller
@Slf4j
@RequestMapping("/course")
public class CourseController {

    @Reference(version = "1.0.0",timeout = 2000,url = "dubbo://localhost:20880")
    ICourseService iCourseService;


    @RequestMapping(value = "/courseList",method = RequestMethod.GET)
    @ResponseBody
    public List<CourseDTO> courseList(HttpServletRequest request){
        log.info("aaaa");
        UserDto userDto= (UserDto) request.getAttribute("user");
        log.info(userDto.toString());
        return  iCourseService.courseList();
    }
}
