package com.satoru.blog.mapper;


import com.satoru.blog.model.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Repository
@Mapper
public interface CommentMapper {
    /**
     * 根据文章id、分页、每页的数量获取该分页下的评论
     */
    List<Comment> queryPageByArticleId(@Param("articleId") Integer articleId,
                                        @Param("index") Integer index,
                                        @Param("pageSize") Integer pageSize);

    /**
     * 根据多种条件(文章id, 评论用户昵称, 评论内容, 评论时间段)获取该文章下的评论数量
     */
    Integer queryCountByArticleId(@Param("articleId") Integer articleId,
                                  @Param("nickname") String nickname,
                                  @Param("content") String content,
                                  @Param("startTime") String startTime,
                                  @Param("endTime") String endTime);

    /**
     *  后台管理 根据多种条件(文章id, 评论用户昵称, 评论内容, 评论时间段)和分页、每页的数量获取该分页下的评论
     */
    List<Comment> queryPage(@Param("articleId") Integer articleId,
                             @Param("nickname") String nickname,
                             @Param("content") String content,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime,
                             @Param("index") Integer index,
                             @Param("pageSize") Integer pageSize);


    /**
     * 添加一条评论
     */
    Integer insert(Comment comment);

    /**
     * 根据id删除一条评论
     */
    Integer deleteById(@Param("id") Integer id);

    /**
     * 根据id修改一条评论
     */
    Integer updateById(Comment comment);
}
