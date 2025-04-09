package com.baofeng.blog.mapper.admin;

import org.apache.ibatis.annotations.Mapper;

import com.baofeng.blog.entity.admin.ArticleImage;
import com.baofeng.blog.entity.admin.Image;

@Mapper
public interface ImageMapper {
    /**
     * 根据ID查询图片
     * @param id 图片ID
     * @return 图片信息
     */
    Image getImageById(Long id);
    /**
     * 插入图片
     * @param image 图片信息
     * @return 影响的行数
     */
    int insertImage(Image image);
    /**
     * 更新图片信息
     * @param image 图片信息
     * @return 影响的行数
     */
    int updateImageSelective(Image image);
    /**
     * 删除图片
     * @param id 图片ID
     * @return 影响的行数
     */
    int deleteImage(Long id);
    /**
     * 根据图片路径（唯一设计）查询图片id
     * @param filePath
     * @return id
     */
    Long getImageIdByfilePath(String filePath);
    /**
     * 创建article_images映射表记录
     */
    int insertArticleImage(ArticleImage articleImage);
}
