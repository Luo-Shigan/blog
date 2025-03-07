package com.baofeng.blog.controller.admin;

import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.BlogSettingVO;
import com.baofeng.blog.service.admin.BlogSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
@Validated
public class BlogSettingController {
    
    private final BlogSettingService blogSettingService;

    /**
     * 获取博客设置
     * @return 博客设置信息
     */
    @GetMapping
    public ApiResponse<BlogSettingVO> getBlogSettings() {
        try {
            return ApiResponse.success(blogSettingService.getBlogSettings());
        } catch (Exception e) {
            return ApiResponse.error(500, "获取设置失败：" + e.getMessage());
        }
    }

    /**
     * 更新博客设置
     * @param settings 设置信息
     * @return 更新结果
     */
    @PutMapping
    public ApiResponse<String> updateBlogSettings(@RequestBody @Validated BlogSettingVO settings) {
        try {
            boolean success = blogSettingService.updateBlogSettings(settings);
            if (success) {
                return ApiResponse.success(null);
            } else {
                return ApiResponse.error(500, "更新设置失败");
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "更新失败：" + e.getMessage());
        }
    }
} 