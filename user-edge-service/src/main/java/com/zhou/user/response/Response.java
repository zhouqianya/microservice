package com.zhou.user.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 13:46
 */

@Data
public class Response implements Serializable {


    public static final Response USERNAME_PASSWORD_INVALID = new Response(1001,"账号或者密码错误");
    public static final Response MOBILE_OR_EMAIL_REQUIRED = new Response(1002," MOBILE_OR_EMAIL_REQUIRED");
    public static final Response SEND_FAILED = new Response(1003," SEND_FAILED");
    public static final Response VERIFY_CODE_INVALID = new Response(1004," VERIFY_CODE_INVAILD");

    public static final Response SUCCESS = new Response();


    private String msg;
    private int code;

    public Response() {
        this.msg = "success";
        this.code = 0;
    }

    public static Response exception(Exception e) {
        return new Response(9999,e.getMessage());
    }

    public Response( int code,String msg) {
        this.msg = msg;
        this.code = code;
    }


}
