package com.satoru.blog.service;

import com.satoru.blog.mapper.ArticleTypeMapper;
import com.satoru.blog.model.ArticleType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class ArticleTypeService {
    private ArticleTypeMapper articleTypeMapper;

    public ArticleTypeService(ArticleTypeMapper articleTypeMapper) {
        this.articleTypeMapper = articleTypeMapper;
    }

    /**
     * 前台 获取除id=0以外的所有文章分类信息
     */
    public List<ArticleType> selectArticleTypeList() {
        return this.articleTypeMapper.selectArticleTypeList();
    }

    /**
     * 后台管理 根据分页、条件查询获取一个分页内的文章分类信息
     */
    public List<ArticleType> selectTypeList(Integer pageNum, Integer pageSize, String typeName) {
        Integer index = (pageNum - 1) * pageSize;
        return this.articleTypeMapper.selectTypeList(index, pageSize, typeName);
    }

    /**
     * 后台管理 根据条件查询获取符合要求的文章分类的数量
     */
    public Integer queryCount(String typeName) {
        return  this.articleTypeMapper.queryCount(typeName);
    }

    /**
     * 后台管理 添加一个文章分类
     */
    public Integer insertType(ArticleType articleType) {
        return this.articleTypeMapper.insertType(articleType);
    }

    /**
     * 后台管理 根据id删除一个文章分类
     */
    public Integer deleteType(Integer articleTypeId) {
        return this.articleTypeMapper.deleteType(articleTypeId);
    }

    /**
     * 后台管理 根据id修改一个文章分类
     */
    public Integer updateType(ArticleType articleType) {
        return this.articleTypeMapper.updateType(articleType);
    }

    /**
     * 后台管理 获取父节点分类列表
     */
    public List<ArticleType> selectParentTypeList() {
        return this.articleTypeMapper.selectParentTypeList();
    }
}
