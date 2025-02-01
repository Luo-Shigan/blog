package com.baofeng.blog.controller;

import com.baofeng.blog.dto.ApiResponse;
import com.baofeng.blog.dto.UserAuthDTO;
import com.baofeng.blog.service.UserService;
import com.baofeng.blog.util.JwtTokenProvider;
import com.baofeng.blog.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public UserController(UserService userService,JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<User> registerUser(@RequestBody @Valid UserAuthDTO.RegisterRequest registerDTO) {
        return ApiResponse.success(userService.registerUser(registerDTO));
    }


    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid UserAuthDTO.LoginRequest loginDTO) {
        User user = userService.loginUser(loginDTO);
        String token = jwtTokenProvider.generateToken(user);
        return ApiResponse.success(token);
    }
} 