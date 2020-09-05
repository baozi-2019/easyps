package com.baozi.easyps.service;

import com.baozi.easyps.entity.User;

public interface IRegisterService {
    public boolean isExist(String userName);

    public boolean addUser(User user);
}
