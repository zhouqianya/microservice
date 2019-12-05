package com.zhou.thrift.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/04 14:07
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherdDTO extends UserDto implements Serializable {
    private String intro;
    private int stars;
}
