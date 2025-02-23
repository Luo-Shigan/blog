package com.baofeng.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
/**
 * LoginResponse 直接将 token 定义为 String
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    @JsonUnwrapped
    private User result;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private int id;
        private String username;
        private String role;
    }
} 