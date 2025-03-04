package com.baofeng.blog.controller.admin;
import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.ArticleCRUDVO;
import com.baofeng.blog.entity.admin.Article;
import com.baofeng.blog.service.admin.ArticleService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public ApiResponse<Article> createArticle(@RequestBody ArticleCRUDVO.CreateArticleRequest articleRequest) {

        boolean flag = articleService.createArticle(articleRequest);
        if(flag){
            return ApiResponse.success(null);
        }else{
            return ApiResponse.error(400, "创建失败");
        }
    }
    
    
}
