package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.entity.login.User;
import com.example.travanalysserver.repository.UserRepo;
import com.example.travanalysserver.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Override
    public User getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }
}
