package com.satoru.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentTree {
    private Integer commentTreeId;
    private Integer CommentId;
    private String content;
    private String commentTreeTime;
    private Integer deleteTag;
    private User commentUser;
    private User toCommentUser;
}
