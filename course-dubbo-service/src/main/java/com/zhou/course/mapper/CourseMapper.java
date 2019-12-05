package com.zhou.course.mapper;

import com.zhou.course.service.CourseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/04 14:19
 */
@Mapper
public interface CourseMapper {

    @Select("select * from pe_course")
    List<CourseDTO> listCourse();

    @Select("select user_id from pr_user_course where course_id = #{courseId}")
    Integer getCourseTeacher(@Param("courseId")int courseId);

}
