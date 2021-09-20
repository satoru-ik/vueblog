package com.satoru.blog.service;

import com.satoru.blog.mapper.CommentMapper;
import com.satoru.blog.mapper.CommentTreeMapper;
import com.satoru.blog.model.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class CommentService {
    private CommentMapper commentMapper;
    private CommentTreeMapper commentTreeMapper;

    public CommentService(CommentMapper commentMapper, CommentTreeMapper commentTreeMapper) {
        this.commentMapper = commentMapper;
        this.commentTreeMapper = commentTreeMapper;
    }

    /**
     * 根据文章id、分页、每页的数量获取该分页下的评论
     */
    public List<Comment> selectPageByArticleId(Integer articleId, Integer pageNum, Integer pageSize) {
        Integer index = pageNum * pageSize;
        List<Comment> list = this.commentMapper.selectPageByArticleId(articleId, index, pageSize);

        for (Comment comment : list) {
            comment.setCommentTreeList(this.commentTreeMapper.selectCommentTreePage(comment.getCommentId(), 0));
        }
        return list;
    }

    /**
     * 根据多种条件(文章id, 评论用户昵称, 评论内容, 评论时间段)获取该文章下的评论数量
     */
    public Integer queryCountByArticleId(Integer articleId, String nickname, String content, String startTime, String endTime) {
        return this.commentMapper.queryCountByArticleId(articleId, nickname, content, startTime, endTime);
    }

    /**
     *  后台管理 根据多种条件(文章id, 评论用户昵称, 评论内容, 评论时间段)和分页、每页的数量获取该分页下的评论
     */
    public List<Comment> selectPage(Integer articleId, String nickname, String content, String startTime, String endTime,
                                    Integer pageNum, Integer pageSize) {
        Integer index = (pageNum - 1) * pageSize;
        return this.commentMapper.selectPage(articleId, nickname, content, startTime, endTime, index, pageSize);
    }

    /**
     * 添加一条评论
     */
    public Integer insert(Comment comment) {
        return this.commentMapper.insert(comment);
    }

    /**
     * 根据id删除一条评论
     */
    public Integer deleteById(Integer id) {
        return this.commentMapper.deleteById(id);
    }

    /**
     * 根据id修改一条评论
     */
    public Integer updateById(Comment comment) {
        return this.commentMapper.updateById(comment);
    }

}
