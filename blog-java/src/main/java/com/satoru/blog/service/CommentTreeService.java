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

    public List<CommentTree> defaultList(Integer commentId) {
        return this.commentTreeMapper.defaultList(commentId);
    }

    public List<CommentTree> selectCommentTreePage(Integer commentId, Integer pageNum) {
        return this.commentTreeMapper.selectCommentTreePage(commentId, pageNum);
    }

    public Boolean insertByCommentId(CommentTree commentTree) {
        return this.commentTreeMapper.insertByCommentId(commentTree);
    }

    public Boolean deleteById(Integer id) {
        return this.commentTreeMapper.deleteById(id);
    }
}
