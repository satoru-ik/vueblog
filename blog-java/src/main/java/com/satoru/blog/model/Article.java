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
public class Article {
    private Integer articleId;
    private String title;
    private String content;
    private String startTime;
    private String updateTime;
    private Integer viewedNumber;
    private Integer praiseNumber;
    private Integer commentNumber;
    private Integer deleteTag;
    private Integer topTag;
    private User user;
    private ArticleType articleType;
}
