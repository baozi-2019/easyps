package com.baozi.easyps.mapper;

import com.baozi.easyps.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where user_name = #{num}")
    public User queryUserByName(String userName);

    @Insert("insert into user (user_name, user_password, user_email, user_age) values (#{userName}, #{userPassword}, #{userEmail}, #{userAge})")
    public int insertUser(User user);
}
