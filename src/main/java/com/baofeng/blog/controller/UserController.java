package com.baofeng.blog.controller;

import com.baofeng.blog.dto.ApiResponse;
import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.service.UserService;
import com.baofeng.blog.util.JwtTokenProvider;
import com.baofeng.blog.entity.User;
import com.baofeng.blog.dto.LoginResponse;
import com.baofeng.blog.dto.IdRequest;
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
    public ApiResponse<LoginResponse> login(@RequestBody @Valid UserAuthDTO.LoginRequest loginDTO) {
        User user = userService.loginUser(loginDTO);
        if (user != null) {
            // 生成 token
            String tokenValue = jwtTokenProvider.generateToken(user);
            // 构造 Token 和 User 信息（内部类）
            LoginResponse.User userInfo = new LoginResponse.User(
                    user.getId(),
                    user.getUsername(),
                    user.getRole().name()
            );
            // 封装并返回
            LoginResponse response = new LoginResponse(tokenValue, userInfo);
            return ApiResponse.success(response);
        } else {
            return ApiResponse.error(401, "登录失败");
        }
    }
    @PostMapping("/getUserInfoById")
    public ApiResponse<User> getUserInfoById(@RequestBody IdRequest idRequest){
        int id = idRequest.getId();
        User user = userService.getUserInfoById(id);
        if (user != null){
            return ApiResponse.success(user);
        }else {
            return ApiResponse.error(401, "用户不存在");
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

    @PutMapping("/updateRole/{id}/{role}")
    public ApiResponse<String> updateUserRole(
            @PathVariable int id, 
            @PathVariable String role) {
        User user = userService.getUserInfoById(id);
        if (user.getRole().name() == "ADMIN") {
            boolean success = userService.updateUserRole(id, role);
            return success ? 
                ApiResponse.success("角色更新成功") : 
                ApiResponse.error(400, "角色更新失败");

        }else {
            return ApiResponse.error(403, "用户没有权限");

        }

    }
} 