package com.baofeng.blog.exception;
public class AuthException extends RuntimeException {  // 继承RuntimeException
    public AuthException(String message) {
        super(message);
    }
} 