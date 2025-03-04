package com.baofeng.blog.service.admin;

import com.baofeng.blog.entity.admin.User;
import com.baofeng.blog.vo.admin.UserAuthVO;
import com.baofeng.blog.vo.admin.UserPageVO;


public interface UserService {
    User registerUser(UserAuthVO.RegisterRequest registerDTO);
    User loginUser(UserAuthVO.LoginRequest loginDTO);
    User getUserByUsername(String username);
    User getUserInfoById(Long id);
    boolean updateUserRole(Long id, String role);
    UserPageVO.Response getUserList(UserPageVO.Request param);
    boolean updatePassword(String username,String newPassword);
} 