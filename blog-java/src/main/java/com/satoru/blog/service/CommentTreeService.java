package com.satoru.blog.service;

import com.satoru.blog.mapper.CommentTreeMapper;
import com.satoru.blog.model.CommentTree;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class CommentTreeService {
    private CommentTreeMapper commentTreeMapper;

    public CommentTreeService(CommentTreeMapper commentTreeMapper) {
        this.commentTreeMapper = commentTreeMapper;
    }

    public List<CommentTree> queryDefaultList(Integer commentId) {
        return this.commentTreeMapper.queryDefaultList(commentId);
    }

    public List<CommentTree> queryCommentTreePage(Integer commentId, Integer pageNum) {
        return this.commentTreeMapper.queryCommentTreePage(commentId, pageNum);
    }

    public Boolean insertByCommentId(CommentTree commentTree) {
        return this.commentTreeMapper.insertByCommentId(commentTree);
    }

    public Boolean deleteById(Integer id) {
        return this.commentTreeMapper.deleteById(id);
    }
}
