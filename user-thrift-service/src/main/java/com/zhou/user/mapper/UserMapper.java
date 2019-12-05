package com.zhou.user.mapper;

import com.zhou.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 10:02
 */
@Mapper
public interface UserMapper {
    @Select("select id,username,password,real_name,mobile,email from pe_user where id =#{id}")
    UserInfo getuserById(@Param("id") int id);

    @Select("select id,username,password,real_name as realName,email,mobile " +
            "from pe_user where username =#{username}")
    UserInfo getUserByName(@Param("username") String username);

    @Insert("insert into pe_user(username,password,real_name,mobile,email) " +
            "value (#{user.username},#{user.password},#{user.realName},#{user.mobile},#{user.email})")
    void registerUser(@Param("user") UserInfo userInfo);


    @Select("select u.id, u.username, u.password,u.real_name as realName, u.mobile,u.email,t.intro,t.stars " +
            "from pe_user as u, pe_teacher as t  where u.id =#{id} and u.id=t.user_id")
    UserInfo getTeacherById(@Param("id") int userId);

}
