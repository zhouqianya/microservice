package com.zhou.course.service;

import com.zhou.course.service.CourseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseServiceImplTest {

    @Autowired
    CourseServiceImpl courseService;

    @Test
    public void courseList() {

        courseService.courseList();
    }
}
