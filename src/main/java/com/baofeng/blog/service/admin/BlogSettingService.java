package com.baofeng.blog.service.admin;

import com.baofeng.blog.vo.admin.BlogSettingVO;

public interface BlogSettingService {
    /**
     * 获取博客设置
     * @return 博客设置信息
     */
    BlogSettingVO getBlogSettings();

    /**
     * 更新博客设置
     * @param settings 设置信息
     * @return 是否更新成功
     */
    boolean updateBlogSettings(BlogSettingVO settings);
} 