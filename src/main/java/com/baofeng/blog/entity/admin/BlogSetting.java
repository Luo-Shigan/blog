package com.baofeng.blog.entity.admin;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class BlogSetting {
    private Long id;
    private String siteTitle;
    private String siteDescription;
    private String siteLogo;
    private String theme;
    private boolean enableComments;
    private long visitCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

} 