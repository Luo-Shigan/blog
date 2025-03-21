package com.baofeng.blog.vo.admin;
public class BlogSettingVO {
    public record initSettingRequest(
        String siteTitle,
        String siteDescription,
        String siteLogo,
        String theme,
        boolean enableComments
    ) {}
    
}
