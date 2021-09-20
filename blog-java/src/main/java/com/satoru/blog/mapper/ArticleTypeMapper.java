package com.satoru.blog.mapper;

import com.satoru.blog.model.ArticleType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Mapper
public interface ArticleTypeMapper {
    /**
     * 前台 获取除id=0以外的所有文章分类信息
     */
    List<ArticleType> selectArticleTypeList();

    /**
     * 后台管理 根据分页、条件查询获取一个分页内的文章分类信息
     */
    List<ArticleType> selectTypeList(@Param("index") Integer index,
                                     @Param("pageSize") Integer pageSize,
                                     @Param("typeName") String typeName);

    /**
     * 后台管理 根据条件查询获取符合要求的文章分类的数量
     */
    Integer queryCount(@Param("typeName") String typeName);

    /**
     * 后台管理 添加一个文章分类
     */
    Integer insertType(ArticleType articleType);

    /**
     * 后台管理 根据id删除一个文章分类
     */
    Integer deleteType(@Param("id") Integer id);

    /**
     * 后台管理 根据id修改一个文章分类
     */
    Integer updateType(ArticleType articleType);

    /**
     * 后台管理 获取父节点分类列表
     */
    List<ArticleType> selectParentTypeList();
}
