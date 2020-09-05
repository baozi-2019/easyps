package com.baozi.easyps.service.impl;

import com.baozi.easyps.entity.User;
import com.baozi.easyps.mapper.UserMapper;
import com.baozi.easyps.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements IRegisterService {

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public boolean isExist(String userName) {
        User user = userMapper.queryUserByName(userName);
        if (user == null)
            return false;
        return true;
    }

    @Override
    public boolean addUser(User user) {
        if (!isExist(user.getUserName()))
            if (userMapper.insertUser(user) >= 1)
                return true;
        return false;
    }
}
