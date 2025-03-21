package com.baofeng.blog.service.admin;
import com.baofeng.blog.vo.admin.BlogSettingVO.initSettingRequest;

public interface BlogSettingService {

    boolean addViews();
    boolean initSettingById(initSettingRequest request);

} 