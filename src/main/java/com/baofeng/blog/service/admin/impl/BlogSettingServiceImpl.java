package com.baofeng.blog.service.admin.impl;

import com.baofeng.blog.service.admin.BlogSettingService;
import com.baofeng.blog.mapper.admin.BlogSettingMapper;
import com.baofeng.blog.vo.admin.BlogSettingVO.initSettingRequest;
import com.baofeng.blog.entity.admin.BlogSetting;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BlogSettingServiceImpl implements BlogSettingService {
    private final BlogSettingMapper blogSettingMapper;
    @Override
    public boolean addViews(){
        //id默认为1,指第一次初始化
        int success = blogSettingMapper.incrementVisitCount(1);
        if ( success > 0 ){
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean initSettingById(initSettingRequest request){
        BlogSetting setting = new BlogSetting();
        setting.setSiteTitle(request.siteTitle());
        setting.setSiteDescription(request.siteDescription());
        setting.setSiteLogo(request.siteLogo());
        setting.setTheme(request.theme());
        setting.setEnableComments(request.enableComments());
        setting.setVisitCount(0);
        setting.setEnableComments(false);
        int success = blogSettingMapper.insertSetting(setting);
        if ( success > 0 ) {
            return true;
        } else {
            return false;
        }

    }
    
} 