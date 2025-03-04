package com.baofeng.blog.vo.admin;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticlePageVO {
    @Data
    public static class ListVO {
        private Integer total;      // 总记录数
        private Integer pages;      // 总页数
        private List<ArticlePageVO> list;  // 文章列表
    }

    @Data
    public static class AuthorVO {
        private Long id;
        private String nickname;
        private String avatar;
    }

    private Long id;
    private String title;
    private String summary;
    private String categoryName;
    private List<String> tags;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;  // 文章状态：0草稿，1已发布
    private String coverImage;
    private AuthorVO author;
}