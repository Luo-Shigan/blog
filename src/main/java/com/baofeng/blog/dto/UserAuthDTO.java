package com.baofeng.blog.dto;
import jakarta.validation.constraints.*;
public class UserAuthDTO {

    // 登录请求
    public record LoginRequest(
        @NotBlank(message = "账号不能为空")
        String username,
        
        @NotBlank(message = "密码不能为空")
        String password
    ) {}

    // 注册请求
    public record RegisterRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 4, max = 20, message = "用户名长度4-20个字符")
        String username,

        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        String email,

        @NotBlank(message = "密码不能为空")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                 message = "密码必须至少8位，包含字母和数字")
        String password
    ) {}
} 