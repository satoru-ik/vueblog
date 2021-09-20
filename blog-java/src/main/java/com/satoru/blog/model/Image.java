package com.satoru.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Data
@AllArgsConstructor
public class Image{
    private String url;
    private String alt;
    private String href;
}
