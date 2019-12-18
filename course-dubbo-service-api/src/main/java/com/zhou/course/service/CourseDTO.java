package com.zhou.course.service;

import com.zhou.thrift.user.dto.TeacherdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/04 14:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO implements Serializable {

    private int id;
    private String title;
    private String description;
    private TeacherdDTO teacherdDTO;

}
