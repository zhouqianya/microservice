package com.zhou.thrift.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 15:59
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public int id;
    public String username;
    public String password;
    public String realName;
    public String email;
    public String mobile;
}
