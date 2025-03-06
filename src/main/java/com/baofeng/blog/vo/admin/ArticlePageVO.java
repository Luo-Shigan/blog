package com.baofeng.blog.vo.admin;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data

/**
 * 文章分页请求和响应封装类（使用 record）
 */
public class ArticlePageVO {

    /**
     * 文章分页请求参数
     */
    public record ArticlePageRequestVO(
        Integer pageNum,     // 当前页码
        Integer pageSize,    // 每页显示条数
        String keyword,      // 可选，搜索关键词
        Long categoryId,     // 可选，分类ID
        String sortBy,       // 可选，排序字段
        String sortOrder     // 可选，排序方向
    ) {}

    /**
     * 文章分页响应数据
     */
    @Data
    public static class ArticlePageResponseVO {
        private long total;          // 总记录数
        private int pages;           // 总页数
        private List<ArticleVO> list; // 文章列表
    }

    /**
     * 文章信息（改为 class，MyBatis 需要 setter）
     */
    @Data
    public static class ArticleVO {
        private Long id;
        private String title;
        private String summary;
        private String categoryName;
        private Integer viewCount;
        private Integer commentCount;
        private Integer likeCount;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private Integer status;
        private String coverImage;
        private AuthorVO author;
    }

    /**
     * 作者信息（改为 class，MyBatis 需要 setter）
     */
    @Data
    public static class AuthorVO {
        private Long authorId;
        private String authorNickname;
        private String authorAvatar;
    }
}
