package com.baozi.easyps.service.impl;

import com.baozi.easyps.entity.User;
import com.baozi.easyps.mapper.UserMapper;
import com.baozi.easyps.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public User login(String name, String password) {
        User user = userMapper.queryUserByName(name);
        if (user != null)
            if (user.getUserPassword().equals(password))
                return user;
        return null;
    }

}
