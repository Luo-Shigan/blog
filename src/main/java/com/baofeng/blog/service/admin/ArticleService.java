package com.baofeng.blog.service.admin;

import com.baofeng.blog.vo.admin.ArticleCRUDVO.*;
import com.baofeng.blog.entity.admin.Article;

public interface ArticleService {
    boolean createArticle(CreateArticleRequest article);
    boolean deleteArticle(Long id);
    Article getArticleById(Long id);
    boolean updateArticleSelective(Article article);
    boolean updatePinStaus(Long id,boolean isPinned);
    ArticlePageResponseVO getArticlePage(ArticlePageRequestVO request);
    boolean publishArticle(Long articleId,Long authorId);
    boolean isTitleExist(String title);
}
