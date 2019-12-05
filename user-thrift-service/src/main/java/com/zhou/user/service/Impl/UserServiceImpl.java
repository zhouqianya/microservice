package com.zhou.user.service.Impl;

import com.zhou.thrift.user.UserInfo;
import com.zhou.thrift.user.UserService;
import com.zhou.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 09:45
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getuserById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        log.info("getTeacherById  start");
        return userMapper.getTeacherById(id);
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}
