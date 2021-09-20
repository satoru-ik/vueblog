package com.satoru.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satoru.blog.model.User;
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
public interface UserMapper extends BaseMapper<User> {
    /**
     * 验证用户或密码是否正确
     */
    User validLogin(User user);

    /**
     * 检测用户名是否被使用
     */
    Integer checkUsername(User user);

    /**
     * 检测昵称是否被使用
     */
    Integer checkNickname(User user);

    /**
     * 检测邮箱是否被使用
     */
    Integer checkEmail(User user);

    /**
     * 用户验证邮箱成功
     */
    User updateEmailVerify(@Param("uid") Integer uid);

    /**
     * 根据多种条件(用户昵称, 注册时间)查询用户数量
     */
    Integer queryCount(@Param("nickname") String nickname,
                       @Param("startTime") String startTime,
                       @Param("endTime") String endTime);

    /**
     * 后台管理 根据多种条件(用户昵称, 注册时间)和分页、每页的数量获取该分页下的用户
     */
    List<User> selectPage(@Param("nickname") String nickname,
                          @Param("startTime") String startTime,
                          @Param("endTime") String endTime,
                          @Param("index") Integer index,
                          @Param("pageSize") Integer pageSize);

    /**
     * 根据email获取一个用户
     */
    User selectByEmail(@Param("email") String email);

    /**
     * 根据uid获取一个用户
     */
    User selectById(@Param("uid") Integer uid);

    /**
     * 管理员根据id修改用户信息
     */
    Integer AdminUpdateUser(User user);

    /**
     * 根据id删除一个用户
     */
    Integer deleteUser(@Param("uid") Integer uid);

    /**
     * 根据id修改用户信息
     */
    Integer updateUser(User user);

    /**
     * 根据id用户更换头像
     */
    Integer updateAvatar(User user);

    /**
     * 根据id用户修改密码
     */
    Integer updatePassword(@Param("uid") Integer uid,
                           @Param("password") String password);

}
