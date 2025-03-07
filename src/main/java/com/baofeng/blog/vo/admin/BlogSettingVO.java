package com.baofeng.blog.vo.admin;

import lombok.Data;
import java.util.Map;
import java.util.HashMap;

@Data
public class BlogSettingVO {
    private String blogName;        // 博客名称
    private String blogDescription; // 博客描述
    private String blogLogo;       // 博客Logo
    private String blogFavicon;    // 博客图标
    private String footerInfo;     // 页脚信息
    private Boolean enableComments; // 是否启用评论
    private String seoKeywords;    // SEO关键词
    private String seoDescription; // SEO描述

    /**
     * 从设置Map转换为VO对象
     */
    public static BlogSettingVO fromSettingMap(Map<String, String> settings) {
        BlogSettingVO vo = new BlogSettingVO();
        vo.setBlogName(settings.getOrDefault("blog_name", ""));
        vo.setBlogDescription(settings.getOrDefault("blog_description", ""));
        vo.setBlogLogo(settings.getOrDefault("blog_logo", ""));
        vo.setBlogFavicon(settings.getOrDefault("blog_favicon", ""));
        vo.setFooterInfo(settings.getOrDefault("footer_info", ""));
        vo.setEnableComments(Boolean.parseBoolean(settings.getOrDefault("enable_comments", "true")));
        vo.setSeoKeywords(settings.getOrDefault("seo_keywords", ""));
        vo.setSeoDescription(settings.getOrDefault("seo_description", ""));
        return vo;
    }

    /**
     * 将VO对象转换为设置Map
     */
    public Map<String, String> toSettingMap() {
        Map<String, String> settings = new HashMap<>();
        settings.put("blog_name", blogName);
        settings.put("blog_description", blogDescription);
        settings.put("blog_logo", blogLogo);
        settings.put("blog_favicon", blogFavicon);
        settings.put("footer_info", footerInfo);
        settings.put("enable_comments", String.valueOf(enableComments));
        settings.put("seo_keywords", seoKeywords);
        settings.put("seo_description", seoDescription);
        return settings;
    }
} 