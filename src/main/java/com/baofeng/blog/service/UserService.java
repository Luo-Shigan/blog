package com.baofeng.blog.service;

import com.baofeng.blog.dto.UserRegisterDTO;
import com.baofeng.blog.entity.User;

public interface UserService {
    User registerUser(UserRegisterDTO registerDTO);
} 