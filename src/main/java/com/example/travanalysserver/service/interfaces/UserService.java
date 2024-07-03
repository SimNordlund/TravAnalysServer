package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.entity.User;

public interface UserService {
    User getUserByUsername(String username);
}
