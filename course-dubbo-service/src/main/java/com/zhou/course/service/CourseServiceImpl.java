package com.zhou.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhou.course.mapper.CourseMapper;
import com.zhou.course.thrift.ServiceProvider;
import com.zhou.thrift.user.UserInfo;
import com.zhou.thrift.user.dto.TeacherdDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/04 14:16
 */
@Service(timeout = 5000,version = "1.0.0")
@Slf4j
public class CourseServiceImpl implements ICourseService {


    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        log.info("courseList start");
        List<CourseDTO> courseDTOS = courseMapper.listCourse();

        if (courseDTOS != null) {
            for (CourseDTO courseDTO : courseDTOS) {
                Integer teacherId = courseMapper.getCourseTeacher(courseDTO.getId());
                if (teacherId != null) {
                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
                        log.info("userInfo={}",userInfo.username);
                        courseDTO.setTeacherdDTO(trans2Teacher(userInfo));
                    } catch (TException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return courseDTOS;
    }

    private TeacherdDTO trans2Teacher(UserInfo userInfo) {
        log.info("userInfo={}",userInfo);

        TeacherdDTO teacherdDTO = new TeacherdDTO();
        BeanUtils.copyProperties(userInfo, teacherdDTO);
        return teacherdDTO;
    }
}
