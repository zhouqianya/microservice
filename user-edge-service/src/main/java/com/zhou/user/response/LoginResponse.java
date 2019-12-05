package com.zhou.user.response;

import lombok.Data;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 16:02
 */
@Data
public class LoginResponse extends Response {
    private  String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
