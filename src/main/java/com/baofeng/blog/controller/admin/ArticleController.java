package com.baofeng.blog.controller.admin;
import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.ArticleCRUDVO;

import lombok.experimental.PackagePrivate;

import com.baofeng.blog.entity.admin.Article;
import com.baofeng.blog.service.admin.ArticleService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

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
            // error 定义 result 为null,这是符合ApiResponse<Article>的
            return ApiResponse.error(404, "文章不存在");
        }
    }
    @PostMapping("/update")
    public ApiResponse<String> updateArticleSelective(@RequestBody Article article){
        if ( article.getId() == null){
            return ApiResponse.error(400,"文章id不能未空");
        }else {
            boolean success =  articleService.updateArticleSelective(article);
            if (success) {
                return ApiResponse.success(null);
            } else {
                return ApiResponse.error(400, "更新失败");
            }
        }
    }


}


    
    

