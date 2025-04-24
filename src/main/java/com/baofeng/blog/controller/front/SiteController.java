package com.baofeng.blog.controller.front;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baofeng.blog.service.admin.BlogSettingService;
import com.baofeng.blog.vo.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/front/settings")
@RequiredArgsConstructor //自动生成一个包含所有 final 或 @NonNull 字段的构造函数
public class SiteController {
    private final BlogSettingService blogSettingService;
    @PutMapping("/addView")
    public ApiResponse<String> addView(){
        try {
            boolean success = blogSettingService.addViews();
            if ( success ){
                return ApiResponse.success("访问量增加成功");
            } else {
                return ApiResponse.error(400, "访问量增加失败");
            }
        } catch (Exception e){
            return ApiResponse.error(400, "增加失败" + e.getMessage());
        }
    }
}
