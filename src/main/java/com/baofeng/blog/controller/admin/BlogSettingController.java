package com.baofeng.blog.controller.admin;

import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.service.admin.BlogSettingService;
import com.baofeng.blog.vo.admin.BlogSettingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;


@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
@Validated
public class BlogSettingController {
    
    private final BlogSettingService blogSettingService;
    @PostMapping("/initSetting")
    public ApiResponse<String> initSetting(@RequestBody BlogSettingVO.initSettingRequest request){
        try {
            boolean success = blogSettingService.initSettingById(request);
            if ( success ){
                return ApiResponse.success("网站初始化成功");
            } else {
                return ApiResponse.error(400, "网站初始化失败");
            }
        } catch(Exception e){
            return ApiResponse.error(400, "网站初始化失败" + e.getMessage());
        }

    }
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