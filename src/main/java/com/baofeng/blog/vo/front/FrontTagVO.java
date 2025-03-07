package com.baofeng.blog.vo.front;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FrontTagVO {
    private Long id;
    private String name;
    private Integer articleCount;  // 标签下的文章数量

    /**
     * 标签详细信息
     */
    @Data
    public static class TagDetailVO {
        private Long id;
        private String name;
        private Integer articleCount;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }
} 