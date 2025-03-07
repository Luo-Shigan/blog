package com.baofeng.blog.service.admin.impl;

import com.baofeng.blog.service.admin.BlogSettingService;
import com.baofeng.blog.vo.admin.BlogSettingVO;
import com.baofeng.blog.entity.admin.BlogSetting;
import com.baofeng.blog.mapper.admin.BlogSettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlogSettingServiceImpl implements BlogSettingService {
    private final BlogSettingMapper blogSettingMapper;

    @Override
    public BlogSettingVO getBlogSettings() {
        // 获取所有设置
        List<BlogSetting> settings = blogSettingMapper.getAllSettings();
        
        // 转换为Map
        Map<String, String> settingMap = new HashMap<>();
        for (BlogSetting setting : settings) {
            settingMap.put(setting.getSettingKey(), setting.getSettingValue());
        }
        
        // 转换为VO对象
        return BlogSettingVO.fromSettingMap(settingMap);
    }

    @Override
    @Transactional
    public boolean updateBlogSettings(BlogSettingVO settings) {
        // 将VO转换为Map
        Map<String, String> settingMap = settings.toSettingMap();
        
        // 更新每个设置项
        boolean success = true;
        for (Map.Entry<String, String> entry : settingMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            // 检查设置是否存在
            BlogSetting existingSetting = blogSettingMapper.getSettingByKey(key);
            
            if (existingSetting != null) {
                // 更新已存在的设置
                BlogSetting setting = new BlogSetting();
                setting.setSettingKey(key);
                setting.setSettingValue(value);
                success &= blogSettingMapper.updateSetting(setting) > 0;
            } else {
                // 创建新的设置
                BlogSetting setting = new BlogSetting();
                setting.setSettingKey(key);
                setting.setSettingValue(value);
                success &= blogSettingMapper.createSetting(setting) > 0;
            }
        }
        
        return success;
    }
} 