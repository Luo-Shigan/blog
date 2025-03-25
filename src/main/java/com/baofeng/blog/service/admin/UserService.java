package com.baofeng.blog.service.admin;

import com.baofeng.blog.entity.admin.User;
import com.baofeng.blog.vo.admin.UserAuthVO.*;



public interface UserService {
    User registerUser(RegisterRequest registerDTO);
    User loginUser(LoginRequest loginDTO);
    User getUserByUsername(String username);
    User getUserInfoById(Long id);
    boolean updateUserRole(Long id, String role);
    userPageResponse getUserList(userPageRequest param);
    boolean updatePassword(String username,String newPassword);
} 