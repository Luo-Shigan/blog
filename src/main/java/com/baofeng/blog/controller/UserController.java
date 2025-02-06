package com.baofeng.blog.controller;

import com.baofeng.blog.dto.ApiResponse;
import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.service.UserService;
import com.baofeng.blog.util.JwtTokenProvider;
import com.baofeng.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<User> registerUser(@RequestBody @Valid UserAuthDTO.RegisterRequest registerDTO) {
        //在Serivce写清楚了用户名重复如何处理的逻辑
        return ApiResponse.success(userService.registerUser(registerDTO));
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid UserAuthDTO.LoginRequest loginDTO) {
        User user = userService.loginUser(loginDTO);
        if (user != null){
            String token = jwtTokenProvider.generateToken(user);
            return ApiResponse.success(token);
        }else{
            return ApiResponse.error(401, "登录失败");
        }
    }

    /**
     * 此接口要求请求携带有效的 JWT Token，JwtAuthenticationFilter 已经在请求处理前解析并设置好了认证信息
     * 当请求成功认证后，就可以通过 SecurityContextHolder 获取当前用户的用户名，再通过业务层查询数据库返回数据。
     */
    @GetMapping("/me")
    public ApiResponse<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ApiResponse.error(401, "未认证");
        }
        // 获取认证后的用户名
        String username = auth.getName();
        // 根据用户名查询数据库返回用户信息
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ApiResponse.error(404, "未找到用户信息");
        }
        return ApiResponse.success(user);
    }
} 