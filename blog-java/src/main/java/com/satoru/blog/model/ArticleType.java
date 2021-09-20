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
public class ArticleType {
    private Integer articleTypeId;
    private String typeName;
    private Integer parentNode;
    private Integer articleNumber;
}
