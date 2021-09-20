package com.satoru.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class User {
    @TableId(
            value = "uid",
            type = IdType.AUTO
    )
    private Integer uid;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String avatar;
    private UserRole role;
    private String registerTime;
    private Integer emailVerify;
    private Integer articleNumber;
    private Integer diaryNumber;
    private Integer commentNumber;
}
