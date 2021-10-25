package com.satoru.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Mapper
public interface MailMapper {
    String queryMailById(@Param("id") Integer id);
}
