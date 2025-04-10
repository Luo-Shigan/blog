package com.baofeng.blog.mapper.admin;

import com.baofeng.blog.vo.admin.CategoryPageVO.CategoryVO;
import com.baofeng.blog.vo.admin.CategoryPageVO.CategoryDictionaryResponse;
import com.baofeng.blog.vo.admin.CategoryPageVO.CategoryPageRequestVO;
import com.baofeng.blog.entity.admin.ArticleCategory;
import com.baofeng.blog.entity.admin.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 分页查询分类列表
     * @param request 分页查询参数
     * @return 分类列表
     */
    List<CategoryVO> getCategoryPage(CategoryPageRequestVO request);

    /**
     * 创建分类
     * @param category 分类信息
     * @return 影响的行数
     */
    int createCategory(Category category);

    /**
     * 根据名称查询分类
     * @param name 分类名称
     * @return 分类信息
     */
    Category getCategoryByName(String name);

    /**
     * 删除分类
     * @param id 分类ID
     * @return 影响的行数
     */
    int deleteCategory(Long id);

    /**
     * 获取分类下的文章数量
     * @param id 分类ID
     * @return 文章数量
     */
    int getArticleCount(Long id);

    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类信息
     */
    Category getCategoryById(Long id);
    /**
     * 获取目录字典
     */
    List<CategoryDictionaryResponse> getAllCategories();
    /**
     * 插入article_categories映射表记录
     * @param ArticleCategory
     * @return 影响行数量
     */
    int insertCategoryReflect(ArticleCategory articleCategory);
} 