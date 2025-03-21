package com.baofeng.blog.controller.admin;

import com.baofeng.blog.entity.admin.User;
import com.baofeng.blog.service.admin.UserService;
import com.baofeng.blog.util.JwtTokenProvider;
import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.LoginResponseVO;
import com.baofeng.blog.vo.admin.UserAuthVO;
import com.baofeng.blog.vo.admin.UserPageVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/admin/users")
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
    public ApiResponse<User> registerUser(@RequestBody @Valid UserAuthVO.RegisterRequest registerDTO) {
        //在Serivce写清楚了用户名重复如何处理的逻辑
        return ApiResponse.success(userService.registerUser(registerDTO));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseVO> login(@RequestBody @Valid UserAuthVO.LoginRequest loginDTO) {
        System.out.println("---------1-----------");
        User user = userService.loginUser(loginDTO);
        if (user != null) {
            // 生成 token
            String tokenValue = jwtTokenProvider.generateToken(user);
            // 构造 Token 和 User 信息（内部类）
            LoginResponseVO.User userInfo = new LoginResponseVO.User(
                    user.getId(),
                    user.getUsername(),
                    user.getRole().name()
            );
            // 封装并返回
            LoginResponseVO response = new LoginResponseVO(tokenValue, userInfo);
            return ApiResponse.success(response);
        } else {
            return ApiResponse.error(401, "登录失败");
        }
    }
    @PostMapping("/getUserInfoById")
    public ApiResponse<User> getUserInfoById(@RequestBody JsonNode idRequest){
        Long id = idRequest.get("id").asLong(); 
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
            @PathVariable Long id, 
            @PathVariable String role) {
            boolean success = userService.updateUserRole(id, role);
            return success ? 
                ApiResponse.success("角色更新成功") : 
                ApiResponse.error(400, "角色更新失败");
    }
    @PostMapping("/getUsersList")
    public ApiResponse<UserPageVO.Response> getUserList(@RequestBody UserPageVO.Request request) {
        System.out.println("你好");
        return ApiResponse.success(userService.getUserList(request));
    }
    @GetMapping("/verifyToken")
    public ApiResponse<User> getUserInfoByToken(@RequestHeader("Authorization") String BearerToken) {
        String token = BearerToken.substring(7); // 去除 "Bearer " 前缀获取真正的 token
        // 验证 token 并获取用户名
        String username = jwtTokenProvider.getUserNameFromToken(token);

        if (username == null) {
            return ApiResponse.error(401, "无效的 token");
        }
        // 根据用户名查询数据库返回用户信息
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ApiResponse.error(404, "未找到用户信息");
        }
        return ApiResponse.success(user);
    }
    @PostMapping("/passwordUpdate")
    public ApiResponse<String> updatePassword(@RequestBody JsonNode requestBody){
        String username = requestBody.get("username").asText(); // 从请求体中提取用户名
        String newPassword = requestBody.get("newPassword").asText(); // 提取新密码
    
        // 这里可以添加更新密码的逻辑
        boolean success = userService.updatePassword(username, newPassword);
        return success ? 
            ApiResponse.success("密码更新成功") : 
            ApiResponse.error(401, "密码更新失败");

    }

} 