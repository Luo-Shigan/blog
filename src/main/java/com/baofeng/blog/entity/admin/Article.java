package com.baofeng.blog.entity.admin;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Article {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String summary;
    private String coverImage;
    private Long authorId;
    private Long categoryId;
    private String status;
    private Integer views;
    private Integer likes;
    private Integer commentsCount;
    private Boolean isFeatured;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

