package com.baofeng.blog.entity.admin;

import lombok.Data;

@Data
public class BlogSetting {
    private Long id;
    private String settingKey;
    private String settingValue;
} 