package com.baofeng.blog.dto;

import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

public class UserPageDTO {
    
    @Data
    public static class Request {
        private Integer current = 1;    // 当前页码
        private Integer size = 10;      // 每页大小
    }
    
    @Data
    public static class Response {
        private List<UserVO> list;      // 数据列表
        private int total;             // 总记录数
    }
    
    @Data
    public static class UserVO {
        private String username;
        private String nickName;
        private String avatarUrl;
        private Role role;
        public enum Role {
            USER, ADMIN
        }
            // 数据库字段 created_at
        private LocalDateTime createdAt;
        
        // 数据库字段 updated_at
        private LocalDateTime updatedAt;
        // ... 其他需要返回的用户字段
    }
}
