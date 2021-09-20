package com.satoru.blog.mapper;

import com.satoru.blog.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Repository
@Mapper
public interface ArticleMapper {
    // 根据文章分类、文章标题、分页起始的索引和每页多少条数据来查询一个分页的文章列表
    List<Article> selectArticlePage(@Param("index") Integer index,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("title") String title,
                                    @Param("articleTypeId") Integer articleTypeId);

    // 根据id查询一篇文章
    Article selectOneById(@Param("id") Integer id);

    // 添加一篇文章
    Integer insert(Article article);

    // 根据id更新一篇文章的内容
    Integer updateById(Article article);

    // 通过id增加文章的访问数
    Integer addViewsById(@Param("id") Integer id);

    // 根据id删除一篇文章
    Integer deleteById(@Param("id") Integer id);

    // 根据文章分类、文章标题来查询符号该条件的文章总数
    Integer queryCount(@Param("title") String title, @Param("articleTypeId") Integer articleTypeId);
}
