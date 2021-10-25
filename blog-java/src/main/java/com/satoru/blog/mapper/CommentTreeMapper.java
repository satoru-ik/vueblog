package com.satoru.blog.mapper;

import com.satoru.blog.model.CommentTree;
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
public interface CommentTreeMapper {
    List<CommentTree> queryDefaultList(@Param("commentId") Integer commentId);

    List<CommentTree> queryCommentTreePage(@Param("commentId") Integer commentId, @Param("pageNum") Integer pageNum);

    Boolean insertByCommentId(CommentTree commentTree);

    Boolean deleteById(@Param("id") Integer id);
}
