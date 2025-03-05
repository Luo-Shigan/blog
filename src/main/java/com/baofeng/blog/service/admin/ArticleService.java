package com.baofeng.blog.service.admin;

import com.baofeng.blog.vo.admin.ArticleCRUDVO;
import com.baofeng.blog.entity.admin.Article;
public interface ArticleService {
    boolean createArticle(ArticleCRUDVO.CreateArticleRequest article);
    boolean deleteArticle(Long id);
    Article getArticleById(Long id);
    boolean updateArticleSelective(Article article);
}
