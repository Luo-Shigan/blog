package com.baofeng.blog.service.admin.impl;

import com.baofeng.blog.service.admin.ArticleService;
import com.baofeng.blog.vo.admin.ArticleCRUDVO;
import com.baofeng.blog.entity.admin.Article;
import com.baofeng.blog.mapper.admin.ArticleMapper;
import com.baofeng.blog.entity.admin.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }
    @Override
    public boolean createArticle(ArticleCRUDVO.CreateArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.title());
        article.setContent(articleRequest.content());
        article.setSummary(articleRequest.summary());
        article.setAuthorId(articleRequest.authorId());
        article.setSlug(articleRequest.slug());
        article.setAuthorId(articleRequest.authorId());
        int rowsInserted = articleMapper.insertArticle(article);

        if(rowsInserted > 0){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean deleteArticle(Long id){
        int rowsDeleted = articleMapper.deleteArticle(id);

        if (rowsDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Article getArticleById(Long id){
        Article article = articleMapper.getArticleById(id);
        return article;
    }
    @Override
    public boolean updateArticleSelective(Article article){
        int rowsUpdated = articleMapper.updateArticleSelective(article);
        if ( rowsUpdated > 0 ){
            return true;
        } else {
            return false;
        }
    }
    
}
