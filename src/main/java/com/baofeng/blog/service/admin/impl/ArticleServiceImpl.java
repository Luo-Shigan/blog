package com.baofeng.blog.service.admin.impl;

import com.baofeng.blog.service.admin.ArticleService;
import com.baofeng.blog.vo.admin.ArticleCRUDVO.*;
import com.baofeng.blog.entity.admin.Article;
import com.baofeng.blog.mapper.admin.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;



@Service
// 替代@Autowerid显示注入
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    @Value("${app.upload.dir}")
    private String uploadDir;
    @Override
    public boolean createArticle(CreateArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.title());
        article.setContent(articleRequest.content());
        article.setSummary(articleRequest.summary());
        article.setAuthorId(articleRequest.authorId());
        article.setSlug(articleRequest.slug());
        article.setAuthorId(articleRequest.authorId());
        article.setStatus(Article.ArticleStatus.DRAFT);
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
            article.setStatus(Article.ArticleStatus.PUBLISHED);
            article.setPublishedAt(LocalDateTime.now());
            return updateArticleSelective(article);
        } else {
            return false;
        }
    }
    @Override
    public boolean isTitleExist(String title){
        boolean isDuplicated = articleMapper.isTitleExist(title);
        return isDuplicated;
    }
    /**
     * 存储图片到服务器并返回相对路径
     * @param imageFile 上传的图片文件
     * @return 图片的相对路径
     * @throws IOException 如果存储失败
     */
    @Override
    /**
     * 存储图片到服务器并返回相对路径
     * @param imageFile 上传的图片文件
     * @return 图片的相对路径
     * @throws IOException 如果存储失败
     */
    public String storeImage(MultipartFile imageFile,Long id) throws IOException {
        // 检查文件是否为空
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be null or empty");
        }

        // 获取文件名并检查
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("Original filename cannot be null or blank");
        }

        // 检查文件扩展名
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("File has no extension");
        }

        // 确保上传目录存在
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // 生成唯一文件名
        String fileExtension = originalFilename.substring(lastDotIndex);
        String uniqueFilename = UUID.randomUUID() + fileExtension;
        
        // 存储文件
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(imageFile.getInputStream(), filePath);
        Article article = new Article();
        article.setId(id);
        article.setCoverImage("/uploads/" + uniqueFilename);
        int rowsUpdated = articleMapper.updateArticleSelective(article);
        if (rowsUpdated > 0) {
            return "/uploads/" + uniqueFilename;
        } else {
            throw new IOException("Failed to update article with image path");
        }
    }
}
