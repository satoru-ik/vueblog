package com.satoru.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer floor;
    private String commentContent;
    private String commentTime;
    private Integer praiseNumber;
    private Integer deleteTag;
    private Integer topTag;
    private User user;
    private Article article;
    private List<CommentTree> commentTreeList;
}
