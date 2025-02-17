package com.baofeng.blog.service;

import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.entity.User;
import com.baofeng.blog.dto.UserPageDTO;


public interface UserService {
    User registerUser(UserAuthDTO.RegisterRequest registerDTO);
    User loginUser(UserAuthDTO.LoginRequest loginDTO);
    User getUserByUsername(String username);
    User getUserInfoById(int id);
    boolean updateUserRole(int id, String role);
    UserPageDTO.Response getUserList(UserPageDTO.Request param);
} 