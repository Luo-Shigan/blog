package com.baofeng.blog.vo.admin;
import jakarta.validation.constraints.*;
public class UserAuthVO {

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
        @Size(min = 5, max = 16, message = "用户名长度4-20个字符")
        String username,

        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        String email,

        @NotBlank(message = "密码不能为空")
        @Pattern(regexp = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){6,18}$",
                 message = "密码格式应为6-18位数字、字母、符号的任意两种组合")
        String password
    ) {}
} 