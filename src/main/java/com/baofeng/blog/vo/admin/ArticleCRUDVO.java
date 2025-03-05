package com.baofeng.blog.vo.admin;

public class ArticleCRUDVO {
    public record CreateArticleRequest(
        String title,
        String content,
        String slug,
        String summary,
        Long authorId
    ) {}
    
    
}
