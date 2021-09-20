package com.satoru.blog.controller;

import com.satoru.blog.model.User;
import com.satoru.blog.service.MailService;
import com.satoru.blog.service.RedisService;
import com.satoru.blog.service.UserService;
import com.satoru.blog.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@RestController
@RequestMapping({"/user/"})
public class UserController {
    private UserService userService;
    private RedisService redisService;
    private MailService mailService;

    public UserController(UserService userService, RedisService redisService, MailService mailService) {
        this.userService = userService;
        this.redisService = redisService;
        this.mailService = mailService;
    }

    /**
     * 用户登录
     */
    @PostMapping("login")
    public Map<String, Object> login(@RequestBody User user, 
                                     @RequestParam(name = "time",required = false,defaultValue = "1") Integer time,
                                     HttpServletResponse response) {

        String password = Utility.saltedPassword(user.getPassword());
        user.setPassword(password);
        User currentUser = this.userService.validLogin(user);
        Map<String, Object> map = new HashMap();
        if (currentUser == null) {
            map.put("code", 404);
            map.put("msg", "账号或密码错误!");
        } else if(currentUser.getEmailVerify() == 0) {
            map.put("code", 404);
            map.put("msg", "邮箱还未验证!");
        } else {
            map.put("code", 200);
            map.put("msg", "登录成功!");
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("session_id", token);
            time = 86400 * time;
            cookie.setMaxAge(time);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            this.redisService.sessionSet(0, token, currentUser, time);
            response.addCookie(cookie);
        }

        return map;
    }

    /**
     * 用户退出登录,在redis中删除相关的数据
     */
    @GetMapping("quit")
    public Map<String, Object> quit(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        Boolean result = this.redisService.sessionDelete(request);
        if (result) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }
        return map;
    }

    /**
     * 用户注册
     */
    @PostMapping("register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> map = new HashMap();
        if (user == null) {
            map.put("code", 404);
            map.put("msg", "传入的数据不能为空");
        } else if (user.getUsername() != null && user.getUsername().trim().length() != 0 && user.getPassword() != null && user.getPassword().trim().length() != 0 && user.getNickname() != null && user.getNickname().trim().length() != 0 && user.getEmail() != null && user.getEmail().trim().length() != 0) {
            if (this.userService.checkUsername(user) == 1) {
                map.put("code", 404);
                map.put("msg", "该用户名已存在");
            } else if (this.userService.checkNickname(user) == 1) {
                map.put("code", 404);
                map.put("msg", "该昵称已被使用");
            } else if (this.userService.checkEmail(user) == 1) {
                map.put("code", 404);
                map.put("msg", "该邮箱已被使用");
            } else {
                this.userService.insert(user);
                if (user.getUid() != null) {
                    String token = UUID.randomUUID().toString();
                    this.redisService.stringSet(0, token, user.getUid().toString(), 60*60*24);
                    this.mailService.sendMailActivate(user, "新用户邮箱注册验证", token);
                    map.put("code", 200);
                    map.put("msg", "注册成功,请在一天之内进行邮箱验证");
                } else {
                    map.put("code", 404);
                    map.put("msg", "注册失败");
                }
            }
        } else {
            map.put("code", 404);
            map.put("msg", "传入的数据不能为空");
        }

        return map;
    }

    /**
     * 用户验证邮箱
     */
    @PutMapping("activate")
    public Map<String, Object> activate(@RequestBody Map<String, String> dataMap, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String token = dataMap.get("token");
        String value = this.redisService.stringGet(token);
        if (value == null) {
            map.put("code", 404);
        } else {
            Integer uid = Integer.valueOf(value);
            this.redisService.stringDelete(token);
            User user = this.userService.updateEmailVerify(uid);
            String str = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("session_id", str);
            Integer time = 604800;
            cookie.setMaxAge(time);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            this.redisService.sessionSet(0, str, user, time);
            response.addCookie(cookie);
            map.put("code", 200);
        }

        return map;
    }

    /**
     * 获取当前登录的用户身份
     */
    @GetMapping("currentUser")
    public Map<String, Object> currentUser(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        User user = this.redisService.currentUser(request);
        if(user == null) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
            map.put("data", user);
        }
        return map;
    }

    /**
     * 后台管理 根据多种条件(用户昵称, 注册时间)和分页、每页的数量获取该分页下的用户
     */
    @GetMapping("page")
    public Map<String, Object> selectPage(@RequestParam(name = "nickname", required = false, defaultValue = "") String nickname,
                                          @RequestParam(name = "startTime", required = false, defaultValue = "") String startTime,
                                          @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime,
                                          @RequestParam(name = "pageNum", required = false,defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false,defaultValue = "10") Integer pageSize) {
        Map<String, Object> map = new HashMap();
        List<User> list = this.userService.selectPage(nickname, startTime, endTime, pageNum, pageSize);
        Integer total = this.userService.queryCount(nickname, startTime, endTime);
        map.put("code", 200);
        map.put("data", list);
        map.put("total", total);
        return map;
    }

    /**
     * 用户忘记密码,向该邮箱账号发送重置密码邮件
     */
    @GetMapping("reset")
    public Map<String, Object> reset(@RequestParam String email) {
        Map<String, Object> map = new HashMap<>();
        User user = this.userService.selectByEmail(email);
        if(user == null) {
            map.put("code", 404);
            map.put("msg", "该邮箱并未注册账号!");
        } else {
            map.put("code", 200);
            map.put("msg", "已向该邮箱发送重置邮件!");
            String token = UUID.randomUUID().toString();
            this.redisService.stringSet(0, token, user.getUid().toString(), 60 * 60 * 24);
            this.mailService.sendMailEditPassword(user, "重置密码", token);
        }
        return map;
    }

    /**
     * 用户通过token重置密码
     */
    @PutMapping("reset/password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> dataMap,
                                             HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String token = dataMap.get("token");
        String password = dataMap.get("password");
        String value = this.redisService.stringGet(token);
        if (value == null) {
            map.put("code", 404);
            map.put("msg", "该验证消息已过期!");
        } else {
            Integer uid = Integer.valueOf(value);
            User user = this.userService.selectById(uid);
            if(user == null) {
                map.put("code", 404);
                map.put("msg", "该验证消息已过期!");
            } else {
                this.userService.updatePassword(uid, password);
                this.redisService.stringDelete(token);
                map.put("code", 200);
                map.put("msg", "更改密码成功!");
                token = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("session_id", token);
                Integer time = 86400;
                cookie.setMaxAge(time);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                this.redisService.sessionSet(0, token, user, time);
                response.addCookie(cookie);
            }
        }
        return map;
    }

    /**
     * 根据uid获取一个用户
     */
//    @GetMapping("{uid}")
//    public Map<String, Object> selectById(@PathVariable Integer uid) {
//        Map<String, Object> map = new HashMap<>();
//        User user = this.userService.selectById(uid);
//        if(user == null) {
//            map.put("code", 404);
//            map.put("msg", "该用户不存在!");
//        } else {
//            map.put("code", 200);
//            map.put("data", user);
//        }
//        return map;
//    }

    /**
     * 添加用户
     */
    @PostMapping("insert")
    public Map<String, Object> insertUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap();
        if (user == null) {
            map.put("code", 404);
            map.put("msg", "传入的数据不能为空");
        } else if (user.getUsername() != null && user.getUsername().trim().length() != 0 && user.getPassword() != null && user.getPassword().trim().length() != 0 && user.getNickname() != null && user.getNickname().trim().length() != 0 && user.getEmail() != null && user.getEmail().trim().length() != 0) {
            if (this.userService.checkUsername(user) == 1) {
                map.put("code", 404);
                map.put("msg", "该用户名已存在");
            } else if (this.userService.checkNickname(user) == 1) {
                map.put("code", 404);
                map.put("msg", "该昵称已被使用");
            } else if (this.userService.checkEmail(user) == 1) {
                map.put("code", 404);
                map.put("msg", "该邮箱已被使用");
            } else {
                this.userService.insert(user);
                if (user.getUid() != null) {
                    map.put("code", 200);
                    map.put("msg", "添加成功");
                } else {
                    map.put("code", 404);
                    map.put("msg", "添加失败");
                }
            }
        } else {
            map.put("code", 404);
            map.put("msg", "传入的数据不能为空");
        }

        return map;
    }

    /**
     * 管理员根据id修改用户信息
     */
    @PutMapping("adminUpdate")
    public Map<String, Object> adminUpdateUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        Integer status = this.userService.AdminUpdateUser(user);
        if (status == 0) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
        }
        return map;
    }

    /**
     * 根据id删除一个用户
     */
    @DeleteMapping("{uid}")
    public Map<String, Object> deleteById(@PathVariable Integer uid) {
        Map<String, Object> map = new HashMap<>();
        Integer status = this.userService.deleteUser(uid);
        if (status == 0) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
        }
        return map;
    }

    /**
     * 根据id修改用户信息
     */
    @PutMapping("update/user")
    public Map<String, Object> updateUser(@RequestBody User user,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        User currentUser = this.redisService.currentUser(request);
        Integer status = this.userService.updateUser(user);
        if (status == 0) {
            map.put("code", 404);
        } else {
            this.redisService.sessionDelete(request);
            currentUser.setEmail(user.getEmail());
            currentUser.setNickname(user.getNickname());
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("session_id", token);
            Integer time = 86400 * 7;
            cookie.setMaxAge(time);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            this.redisService.sessionSet(0, token, currentUser, time);
            response.addCookie(cookie);
            map.put("code", 200);
        }
        return map;
    }

    /**
     * 根据id更换用户头像
     */
    @PutMapping("update/avatar")
    public Map<String, Object> updateAvatar(@RequestBody Map<String, String> dataMap,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String avatar = dataMap.get("avatar");
        User user = this.redisService.currentUser(request);
        user.setAvatar(avatar);
        Integer status = this.userService.updateAvatar(user);
        if (status == 0) {
            map.put("code", 404);
        } else {
            this.redisService.sessionDelete(request);
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("session_id", token);
            Integer time = 86400 * 7;
            cookie.setMaxAge(time);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            this.redisService.sessionSet(0, token, user, time);
            response.addCookie(cookie);
            map.put("code", 200);
        }
        return map;
    }

    /**
     * 根据id修改用户密码
     * 此处有问题，@RequestParam不能获取put请求中的参数
     * @RequestParam只能接收x-www-form-urlencoded编码格式的数据
     */
    @PutMapping("update/password")
    public Map<String, Object> updatePassword(@RequestBody User user) {
        Integer uid = user.getUid();
        String password = user.getPassword();
        Map<String, Object> map = new HashMap<>();
        password = Utility.saltedPassword(password);
        Integer status = this.userService.updatePassword(uid, password);
        if (status == 0) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
        }
        return map;
    }
}
