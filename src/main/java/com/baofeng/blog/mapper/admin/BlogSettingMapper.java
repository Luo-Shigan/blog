package com.baofeng.blog.mapper.admin;

import com.baofeng.blog.entity.admin.BlogSetting;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BlogSettingMapper {
    /**
     * 获取所有博客设置
     * @return 设置列表
     */
    List<BlogSetting> getAllSettings();

    /**
     * 根据键获取设置
     * @param key 设置键
     * @return 设置信息
     */
    BlogSetting getSettingByKey(String key);

    /**
     * 更新设置
     * @param setting 设置信息
     * @return 影响的行数
     */
    int updateSetting(BlogSetting setting);

    /**
     * 创建设置
     * @param setting 设置信息
     * @return 影响的行数
     */
    int createSetting(BlogSetting setting);
}