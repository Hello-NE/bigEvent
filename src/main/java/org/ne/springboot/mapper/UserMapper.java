package org.ne.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.ne.springboot.pojo.User;

@Mapper
public interface UserMapper {


    @Select("select * from user where username=#{username}")
    User findByUserName(String username);


    @Insert("insert into user(username,password,create_time,update_time)" +
    "values (#{username}, #{password}, now(), now())")
    void add(@Param("username") String username,@Param("password")  String password);


    @Update("update user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id}")
    void update(User user);


    @Update("update user set user_pic=#{userPic}, update_time=now() where id=#{id}")
    void updateAvatar(@Param("userPic") String userPic, @Param("id") Integer id);


    @Update("update user set password=#{password}, update_time=now() where id=#{id}")
    void updatePwd(@Param("password") String password, @Param("id") Integer id);
}
