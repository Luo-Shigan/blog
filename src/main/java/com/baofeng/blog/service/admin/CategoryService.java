package com.baofeng.blog.service.admin;

import com.baofeng.blog.vo.admin.CategoryPageVO.CategoryDictionaryResponse;
import com.baofeng.blog.vo.admin.CategoryPageVO.CategoryPageRequestVO;
import com.baofeng.blog.vo.admin.CategoryPageVO.CategoryPageResponseVO;
import com.baofeng.blog.vo.admin.CategoryPageVO.CreateCategoryRequest;

import java.util.List;
public interface CategoryService {
    /**
     * 分页查询分类列表
     * @param request 分页查询参数
     * @return 分页结果
     */
    CategoryPageResponseVO getCategoryPage(CategoryPageRequestVO request);

    /**
     * 创建分类
     * @param request 创建分类请求
     * @return 是否创建成功
     */
    boolean createCategory(CreateCategoryRequest request);

    /**
     * 删除分类
     * @param id 分类ID
     * @return 是否删除成功
     * @throws RuntimeException 当分类不存在或分类下有文章时抛出
     */
    boolean deleteCategory(Long id);
    /**
     * 获取目录字典列表
     * @return
     */
    List<CategoryDictionaryResponse> getCategoryDictionary();
}
