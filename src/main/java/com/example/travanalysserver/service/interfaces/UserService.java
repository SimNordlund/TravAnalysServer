package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.entity.login.User;

public interface UserService {
    User getUserByUsername(String username);
}
