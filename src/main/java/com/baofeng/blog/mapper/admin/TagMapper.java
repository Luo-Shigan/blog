package com.baofeng.blog.mapper.admin;

import com.baofeng.blog.vo.admin.TagPageVO.TagVO;
import com.baofeng.blog.vo.admin.TagPageVO.TagDictionaryResponse;
import com.baofeng.blog.vo.admin.TagPageVO.TagPageRequestVO;
import com.baofeng.blog.entity.admin.ArticleTag;
import com.baofeng.blog.entity.admin.Tag;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TagMapper {
    /**
     * 分页查询标签列表
     * @param request 分页查询参数
     * @return 标签列表
     */
    List<TagVO> getTagPage(TagPageRequestVO request);

    /**
     * 根据名称查询标签
     * @param name 标签名称
     * @return 标签信息
     */
    Tag getTagByName(String name);

    /**
     * 根据ID查询标签
     * @param id 标签ID
     * @return 标签信息
     */
    Tag getTagById(Long id);

    /**
     * 创建标签
     * @param tag 标签信息
     * @return 影响的行数
     */
    int createTag(Tag tag);

    /**
     * 删除标签
     * @param id 标签ID
     * @return 影响的行数
     */
    int deleteTag(Long id);

    /**
     * 获取标签下的文章数量
     * @param id 标签ID
     * @return 文章数量
     */
    int getArticleCount(Long id);
    /**
     * 获取所有标签id和name
     * @return 所有标签id和name
     */
    List<TagDictionaryResponse> getAllTags();
    /**
     * 插入 article_tags映射表记录
     * @param ArticleTag
     * @return 影响行数
     */
    int insertArticleTag(ArticleTag articleTag);
    /**
     * 获取所有tag name
     * @return 所有tag name
     */
    List<String> getAllTagName();
    /**
     * 判断name是否在表中存在
     * @param name
     * @return 0不存在/1已存在
     */
    boolean checkExactName(String name);
}