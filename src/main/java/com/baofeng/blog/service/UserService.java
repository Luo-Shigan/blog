package com.baofeng.blog.service;

import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.entity.User;


public interface UserService {
    User registerUser(UserAuthDTO.RegisterRequest registerDTO);
    User loginUser(UserAuthDTO.LoginRequest loginDTO);
    User getUserByUsername(String username);
} 