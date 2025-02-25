package com.baofeng.blog.service.impl;

import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.dto.UserPageDTO;
import com.baofeng.blog.entity.User;
import com.baofeng.blog.exception.DuplicateUserException;
import com.baofeng.blog.exception.AuthException;
import com.baofeng.blog.mapper.UserMapper;
import com.baofeng.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder) {
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
            throw new AuthException(400,"用户不存在");
        }
        else if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            userMapper.incrementLoginAttempts(user.getId());
            throw new AuthException(400,"密码错误");
        }
        else if (user.getStatus() == User.Status.BANNED) {
            throw new AuthException(403,"账户已被锁定");
        }
        else {
            //什么都不做
        }

        userMapper.updateLoginInfo(user.getId());
        return user;
    }
    
    @Override
    public User getUserByUsername(String username) {
        // 此处直接复用 UserMapper 中 selectByUsernameOrEmail 方法
        return userMapper.selectByUsernameOrEmail(username);
    }
    @Override
    public User getUserInfoById(int id){
        return userMapper.selectById(id);
    }

    @Override
    public boolean updateUserRole(int id, String role) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setRole(User.Role.valueOf(role.toUpperCase()));
            userMapper.updateUserSelective(user);
            return true;
        }
        return false;
    }

    @Override
    public UserPageDTO.Response getUserList(UserPageDTO.Request param) {
        int offset = (param.getCurrent() - 1) * param.getSize();
        int pageSize = param.getSize();
        // 先查询 User 列表
        List<User> userslist = userMapper.selectByPage(offset, pageSize);
        int total = userslist.size();
        // 手动转换为 UserVO
        List<UserPageDTO.UserVO> voList = userslist.stream()
            .map(user -> {
                UserPageDTO.UserVO vo = new UserPageDTO.UserVO();
                vo.setUsername(user.getUsername());
                vo.setNickName(user.getNickName());
                vo.setAvatarUrl(user.getAvatarUrl());
                vo.setCreatedAt(user.getCreatedAt());
                vo.setUpdatedAt(user.getUpdatedAt());
                return vo;
            })
            .collect(Collectors.toList());
        
        UserPageDTO.Response result = new UserPageDTO.Response();
        result.setList(voList);
        result.setTotal(total);
        return result;
    }
    @Override
    public boolean updatePassword(String username,String newPassword){

        int result = userMapper.updatePassword(username, passwordEncoder.encode(newPassword));
        return result > 0;
    }
} 