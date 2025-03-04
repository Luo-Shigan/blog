package com.baofeng.blog.mapper.admin;
import com.baofeng.blog.entity.admin.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    Article getArticleById(Long id);
    int insertArticle(Article article);
    int updateArticle(Article article);
    int deleteArticle(Long id);
}
