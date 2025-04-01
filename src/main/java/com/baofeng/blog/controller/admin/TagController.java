package com.baofeng.blog.controller.admin;

import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.TagPageVO.TagPageRequestVO;
import com.baofeng.blog.vo.admin.TagPageVO.TagPageResponseVO;
import com.baofeng.blog.vo.admin.TagPageVO.CreateTagRequest;
import com.baofeng.blog.vo.admin.TagPageVO.TagDictionaryResponse;
import com.baofeng.blog.service.admin.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/admin/tags")
@RequiredArgsConstructor
@Validated
public class TagController {
    
    private final TagService tagService;

    /**
     * 创建标签
     * @param request 创建标签请求
     * @return 创建结果
     */
    @PostMapping("/create")
    public ApiResponse<String> createTag(@RequestBody CreateTagRequest request) {
        // 参数校验
        if (request == null) {
            return ApiResponse.error(400, "请求参数不能为空");
        }
        if (request.name() == null || request.name().trim().isEmpty()) {
            return ApiResponse.error(400, "标签名称不能为空");
        }
        
        try {
            boolean success = tagService.createTag(request);
            if (success) {
                return ApiResponse.success(null);
            } else {
                return ApiResponse.error(500, "创建标签失败");
            }
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "创建失败：" + e.getMessage());
        }
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTag(@PathVariable Long id) {
        try {
            boolean success = tagService.deleteTag(id);
            if (success) {
                return ApiResponse.success(null);
            } else {
                return ApiResponse.error(500, "删除标签失败");
            }
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "删除失败：" + e.getMessage());
        }
    }

    /**
     * 分页查询标签列表
     * @param request 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/list")
    public ApiResponse<TagPageResponseVO> getTagPage(@RequestBody TagPageRequestVO request) {
        // 参数校验
        if (request == null) {
            return ApiResponse.error(400, "请求参数不能为空");
        }
        if (request.pageNum() != null && request.pageNum() < 1) {
            return ApiResponse.error(400, "页码必须大于0");
        }
        if (request.pageSize() != null && request.pageSize() < 1) {
            return ApiResponse.error(400, "每页显示条数必须大于0");
        }
        
        try {
            return ApiResponse.success(tagService.getTagPage(request));
        } catch (Exception e) {
            return ApiResponse.error(500, "查询失败：" + e.getMessage());
        }
    }
    /**
     * 查询标签列表
     * @return {id,name}
     */
    @GetMapping("/getTagDictionary")
    public ApiResponse<List<TagDictionaryResponse>> getTagDictionary(){
        try {
            List<TagDictionaryResponse> tagDictionaryResponse = tagService.getTagDictionary();
            return ApiResponse.success(tagDictionaryResponse);
        } catch (Exception e) {
            return ApiResponse.error(400,"获取失败"+e.getMessage());
        }
    }
} 