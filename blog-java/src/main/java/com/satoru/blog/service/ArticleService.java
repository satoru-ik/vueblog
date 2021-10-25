package com.satoru.blog.service;

import com.satoru.blog.mapper.ArticleMapper;
import com.satoru.blog.model.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class ArticleService {
    private ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    // 根据文章分类、文章标题、分页起始的索引和每页多少条数据来查询一个分页的文章列表
    public List<Article> queryArticlePage(Integer pageNum, Integer pageSize, String title, Integer articleTypeId) {
        Integer index = (pageNum - 1) * pageSize;
        return this.articleMapper.queryArticlePage(index, pageSize, title, articleTypeId);
    }

    // 根据id查询一篇文章
    public Article queryOneById(Integer id) {
        return this.articleMapper.queryOneById(id);
    }

    // 添加一篇文章
    public Integer insert(Article article) {
        return this.articleMapper.insert(article);
    }

    // 根据id更新一篇文章的内容
    public Integer updateById(Article article) {
        return this.articleMapper.updateById(article);
    }

    // 根据id增加文章的访问数量
    public Integer addViewsById(Integer articleId) {
        return this.articleMapper.addViewsById(articleId);
    }

    // 根据id删除一篇文章
    public Integer deleteById(Integer id) {
        return this.articleMapper.deleteById(id);
    }

    // 根据文章分类、文章标题来查询符号该条件的文章总数
    public Integer queryCount(String title, Integer articleTypeId) {
        return this.articleMapper.queryCount(title, articleTypeId);
    }
}
