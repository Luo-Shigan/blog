package com.baofeng.blog.service.impl;

import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.entity.User;
import com.baofeng.blog.exception.DuplicateUserException;
import com.baofeng.blog.exception.AuthException;
import com.baofeng.blog.mapper.UserMapper;
import com.baofeng.blog.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(UserAuthDTO.RegisterRequest registerDTO) {
        // 检查用户名和邮箱唯一性
        checkUserUniqueness(registerDTO.username(), registerDTO.email());
        
        User newUser = new User();
        newUser.setUsername(registerDTO.username());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
        newUser.setRole(User.Role.USER);
        newUser.setStatus(User.Status.ACTIVE);
        
        userMapper.insertUser(newUser);
        return newUser;
    }

    private void checkUserUniqueness(String username, String email) {
        if (userMapper.selectByUsernameOrEmail(username) != null) {
            throw new DuplicateUserException("用户名已存在");
        }
        if (userMapper.selectByUsernameOrEmail(email) != null) {
            throw new DuplicateUserException("邮箱已被注册");
        }
    }
    @Override
    public User loginUser(UserAuthDTO.LoginRequest loginDTO) {
        User user = userMapper.selectByUsernameOrEmail(loginDTO.username());
        if (user == null) {
            throw new AuthException("用户不存在");
        }
        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            userMapper.incrementLoginAttempts(user.getId());
            throw new AuthException("密码错误");
        }

        if (user.getStatus() == User.Status.BANNED) {
            throw new AuthException("账户已被锁定");
        }

        userMapper.updateLoginInfo(user.getId());
        return user;
    }
    
} 