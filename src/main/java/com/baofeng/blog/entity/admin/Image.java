package com.baofeng.blog.entity.admin;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class Image {
    private Long id;
    private Long articleId;
    private String filePath;
    private String fileName;
    //kb
    private Long fileSize;
    private String mimeType;
    private boolean isCover;
    private long sortOrder;
    //上传人用户名
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
}
