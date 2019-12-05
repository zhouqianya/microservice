package com.zhou.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhou.thrift.user.UserInfo;
import com.zhou.thrift.user.dto.UserDto;
import com.zhou.user.redis.RedisClient;
import com.zhou.user.response.LoginResponse;
import com.zhou.user.response.Response;
import com.zhou.user.thrift.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 13:36
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    ServiceProvider serviceProvider;

    @Autowired
    RedisClient redisClient;

    //    @GetMapping("/login")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        System.out.println("login");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

        UserInfo userInfo = null;
        //1.
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }

        if (userInfo == null) {
            return Response.USERNAME_PASSWORD_INVALID;
        }

        if (!userInfo.getPassword().equalsIgnoreCase(md5(password))) {
            return Response.USERNAME_PASSWORD_INVALID;
        }
        //2.生成token
        String token = genToken();

        redisClient.set(token, String.valueOf(userInfo.getId()), 3600);


        return new LoginResponse(token);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam(value = "realName", required = false) String realName,
                             @RequestParam(value = "mobile", required = false) String mobile,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "verifyCode", required = false) String verifyCode) {


        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            return Response.MOBILE_OR_EMAIL_REQUIRED;
        }

        //校验 验证码
        if (!StringUtils.isBlank(mobile)) {
            String redisCode = redisClient.get(mobile);
            if (!verifyCode.equals(redisCode)) {
                return Response.VERIFY_CODE_INVALID;
            }
        }
        if (!StringUtils.isBlank(email)) {
            String redisCode = redisClient.get(email);
            if (!verifyCode.equals(redisCode)) {
                return Response.VERIFY_CODE_INVALID;
            }
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setUsername(username);
        userInfo.setPassword(md5(password));
        userInfo.setMobile(mobile);
        userInfo.setRealName(realName);
        try {
            serviceProvider.getUserService().regiserUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return Response.SUCCESS;
    }


    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    @ResponseBody
    public UserDto authentication(@RequestParam("token") String token) {

        UserDto userDto = new UserDto();
        try {
            String userId = redisClient.get(token);
            log.info("userId = {}", userId);

            if (userId != null) {
                UserInfo userInfo = serviceProvider.getUserService().getUserById(Integer.parseInt(userId));
                BeanUtils.copyProperties(userInfo, userDto);
            }
//            return  new ObjectMapper().readValue(redisClient.get(token), UserDto.class);
            return userDto;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "sendVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public Response sendVerifyCode(@RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email) {

        String message = "Verify code is ：";
        String code = randomCode("0123456789", 6);
        try {

            boolean result = false;
            if (!StringUtils.isBlank(mobile)) {
                result = serviceProvider.getMessageService().sendMobileMessage(mobile, message + code);
                redisClient.set(mobile, code);
            } else if (!StringUtils.isBlank(email)) {
                result = serviceProvider.getMessageService().sendEmailMessage(email, message + code);
                redisClient.set(email, code);
            } else {
                return Response.MOBILE_OR_EMAIL_REQUIRED;
            }

            if (!result) {
                return Response.SEND_FAILED;
            }
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }

        return Response.SUCCESS;
    }

    private UserDto toDto(UserInfo userInfo) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userInfo, userDto);
        return userDto;
    }

    private String genToken() {

        return randomCode("0123456789abcdefghigklmnopqrstuvwsyz", 32);
    }

    private String randomCode(String s, int size) {

        StringBuilder result = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return String.valueOf(result);
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(new UserController().md5("111"));
    }
}
