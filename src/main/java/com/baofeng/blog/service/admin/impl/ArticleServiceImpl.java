package com.baofeng.blog.service.admin.impl;

import com.baofeng.blog.service.admin.ArticleService;
import com.baofeng.blog.vo.admin.ArticleCRUDVO;
import com.baofeng.blog.vo.admin.ArticlePageVO.ArticlePageRequestVO;
import com.baofeng.blog.vo.admin.ArticlePageVO.ArticlePageResponseVO;
import com.baofeng.blog.vo.admin.ArticlePageVO.ArticleVO;
import com.baofeng.blog.entity.admin.Article;
import com.baofeng.blog.mapper.admin.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
// 替代@Autowerid显示注入
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;

    @Override
    public boolean createArticle(ArticleCRUDVO.CreateArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.title());
        article.setContent(articleRequest.content());
        article.setSummary(articleRequest.summary());
        article.setAuthorId(articleRequest.authorId());
        article.setSlug(articleRequest.slug());
        article.setAuthorId(articleRequest.authorId());
        article.setStatus("DRAFT");
        LocalDateTime now = LocalDateTime.now();
        article.setCreatedAt(now);
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

    @Override
    public boolean updatePinStaus(Long id,boolean isPinned){
        Article article = new Article();
        article.setId(id);
        article.setIsFeatured(isPinned);
        int rowsUpdated = articleMapper.updateArticleSelective(article);
        if ( rowsUpdated > 0 ){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArticlePageResponseVO getArticlePage(ArticlePageRequestVO request) {
        // 参数校验
        int pageNum = request.pageNum() != null ? request.pageNum() : 1;
        int pageSize = request.pageSize() != null ? request.pageSize() : 10;
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<ArticleVO> list = articleMapper.getArticlePage(request);
        // 获取分页信息
        PageInfo<ArticleVO> pageInfo = new PageInfo<>(list);
        
        // 封装返回结果
        ArticlePageResponseVO response = new ArticlePageResponseVO();
        response.setTotal(pageInfo.getTotal());    // 总记录数
        response.setPages(pageInfo.getPages());    // 总页数
        response.setList(pageInfo.getList());      // 当前页数据
        return response;
    }
    @Override
    public boolean publishArticle(Long articleId,Long authorId) {
        Long articleAuthorId = articleMapper.getAuthorIdById(articleId);
        if ( articleAuthorId == authorId ) {
            Article article = new Article();
            article.setId(articleId);
            article.setStatus("PUBLISHED");
            article.setPublishedAt(LocalDateTime.now());
            return updateArticleSelective(article);
        } else {
            return false;
        }
    }
}
