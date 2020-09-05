package com.baozi.easyps.service;

import com.baozi.easyps.entity.User;

public interface ILoginService {

    public User login(String name, String password);

}
