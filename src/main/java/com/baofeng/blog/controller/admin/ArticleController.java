package com.baofeng.blog.controller.admin;
import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.ArticleCRUDVO;
import com.baofeng.blog.vo.admin.ArticlePageVO.ArticlePageRequestVO;
import com.baofeng.blog.vo.admin.ArticlePageVO.ArticlePageResponseVO;
import com.baofeng.blog.entity.admin.Article;
import com.baofeng.blog.service.admin.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/admin/articles")
@RequiredArgsConstructor
@Validated
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/publish")
    public ApiResponse<String> createArticle(@RequestBody ArticleCRUDVO.CreateArticleRequest articleRequest) {
        boolean flag = articleService.createArticle(articleRequest);
        if (flag) {
            return ApiResponse.success(null);
        }else{
            return ApiResponse.error(400, "创建失败");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteArticle(@PathVariable Long id){
        boolean success = articleService.deleteArticle(id);
        if (success) {
            return ApiResponse.success(null);
        } else {
            return ApiResponse.error(404, "文章未找到");
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<Article> getArticleById(@PathVariable Long id){
        Article article = articleService.getArticleById(id);
        if (article !=null ) {
            return ApiResponse.success(article);
        } else {
            return ApiResponse.error(404, "文章不存在");
        }
    }

    @PostMapping("/update")
    public ApiResponse<String> updateArticleSelective(@RequestBody Article article){
        if ( article.getId() == null){
            return ApiResponse.error(400,"文章id不能为空");
        }else {
            boolean success =  articleService.updateArticleSelective(article);
            if (success) {
                return ApiResponse.success(null);
            } else {
                return ApiResponse.error(400, "更新失败");
            }
        }
    }

    @PutMapping("/{id}/pin")
    public ApiResponse<String> updataPinStaus(@PathVariable Long id,@RequestParam boolean isPinned){
        boolean success = articleService.updatePinStaus(id,isPinned);
        if ( success ) {
            return ApiResponse.success(null);
        } else {
            return ApiResponse.error(400, "文章状态更新失败");
        }
    }

    /**
     * 分页查询文章列表
     * @param request 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/list")
    public ApiResponse<ArticlePageResponseVO> getArticlePage(@RequestBody ArticlePageRequestVO request) {
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
        if (request.sortBy() != null && !isValidSortField(request.sortBy())) {
            return ApiResponse.error(400, "无效的排序字段");
        }
        if (request.sortOrder() != null && !isValidSortOrder(request.sortOrder())) {
            return ApiResponse.error(400, "无效的排序方向");
        }
        
        try {
            return ApiResponse.success(articleService.getArticlePage(request));
        } catch (Exception e) {
            return ApiResponse.error(500, "查询失败：" + e.getMessage());
        }
    }

    /**
     * 验证排序字段是否有效
     */
    private boolean isValidSortField(String sortBy) {
        return sortBy == null || sortBy.matches("^(createTime|updateTime|viewCount|likeCount|commentCount)$");
    }

    /**
     * 验证排序方向是否有效
     */
    private boolean isValidSortOrder(String sortOrder) {
        return sortOrder == null || sortOrder.equalsIgnoreCase("asc") || sortOrder.equalsIgnoreCase("desc");
    }
}


    
    

