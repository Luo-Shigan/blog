package com.baofeng.blog.entity.admin;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Tag {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 