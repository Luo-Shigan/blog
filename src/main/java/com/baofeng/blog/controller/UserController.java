package com.baofeng.blog.controller;

import com.baofeng.blog.dto.ApiResponse;
import com.baofeng.blog.dto.UserRegisterDTO;
import com.baofeng.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.baofeng.blog.entity.User;
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<User> registerUser(@RequestBody @Valid UserRegisterDTO registerDTO) {
        return ApiResponse.success(userService.registerUser(registerDTO));
    }
} 