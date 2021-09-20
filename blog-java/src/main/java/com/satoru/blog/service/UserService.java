package com.satoru.blog.service;

import com.satoru.blog.mapper.UserMapper;
import com.satoru.blog.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Integer insert(User user) {
        return this.userMapper.insert(user);
    }

    /**
     * 验证用户或密码是否正确
     */
    public User validLogin(User user) {
        return this.userMapper.validLogin(user);
    }

    /**
     * 检测用户名是否被使用
     */
    public Integer checkUsername(User user) {
        return this.userMapper.checkUsername(user);
    }

    /**
     * 检测昵称是否被使用
     */
    public Integer checkNickname(User user) {
        return this.userMapper.checkNickname(user);
    }

    /**
     * 检测邮箱是否被使用
     */
    public Integer checkEmail(User user) {
        return this.userMapper.checkEmail(user);
    }

    /**
     * 用户验证邮箱成功
     */
    public User updateEmailVerify(Integer uid) {
        return this.userMapper.updateEmailVerify(uid);
    }

    /**
     * 根据多种条件(用户昵称, 注册时间)查询用户数量
     */
    public Integer queryCount(String nickname, String startTime, String endTime) {
        return userMapper.queryCount(nickname, startTime, endTime);
    }

    /**
     * 后台管理 根据多种条件(用户昵称, 注册时间)和分页、每页的数量获取该分页下的用户
     */
    public List<User> selectPage(String nickname, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        Integer index = (pageNum - 1) * pageSize;
        return userMapper.selectPage(nickname, startTime, endTime, index, pageSize);
    }

    /**
     * 根据email获取一个用户
     */
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    /**
     * 根据uid获取一个用户
     */
    public User selectById(Integer uid) {
        return userMapper.selectById(uid);
    }

    /**
     * 管理员根据id修改用户信息
     */
    public Integer AdminUpdateUser(User user) {
        return userMapper.AdminUpdateUser(user);
    }

    /**
     * 根据id删除一个用户
     */
    public Integer deleteUser(Integer uid) {
        return userMapper.deleteUser(uid);
    }

    /**
     * 根据id修改用户信息
     */
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 根据id更换用户头像
     */
    public Integer updateAvatar(User user) {
        return userMapper.updateAvatar(user);
    }

    /**
     * 根据id用户修改密码
     */
    public Integer updatePassword(Integer uid, String password) {
        return userMapper.updatePassword(uid, password);
    }
}
