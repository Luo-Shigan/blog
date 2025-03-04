package com.baofeng.blog.service.admin;

import com.baofeng.blog.vo.admin.ArticleCRUDVO;

public interface ArticleService {
    boolean createArticle(ArticleCRUDVO.CreateArticleRequest article);
}
