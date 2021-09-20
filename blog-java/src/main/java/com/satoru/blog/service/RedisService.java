package com.satoru.blog.service;

import com.satoru.blog.model.User;
import com.satoru.blog.utility.CookieUtility;
import com.satoru.blog.utility.Utility;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class RedisService {
    private RedisTemplate<String, String> template;
    private RedisTemplate<String, User> userTemplate;

    public RedisService(RedisTemplate redisTemplate, RedisTemplate<String, User> userTemplate) {
        this.template = redisTemplate;
        this.userTemplate = userTemplate;
    }

    /**
     *
     * @param index 设置使用哪个数据库
     * @param key 键
     * @param value 值
     * @param time 设置过期时间, 单位是秒
     */
    public boolean stringSet(Integer index, String key, String value, Integer time) {
        this.template.opsForValue().set(key, value, (long)time, TimeUnit.SECONDS);
        return true;
    }

    public String stringGet(String key) {
        String value = (String)this.template.opsForValue().get(key);
        return value;
    }

    public Boolean stringDelete(String key) {
        Boolean result = this.template.delete(key);
        return result;
    }

    public boolean sessionSet(Integer index, String key, User value, Integer time) {
        this.userTemplate.opsForValue().set(key, value, (long)time, TimeUnit.SECONDS);
        return true;
    }

    public User sessionGet(String key) {
        User value = (User)this.userTemplate.opsForValue().get(key);
        return value;
    }

    public Boolean sessionDelete(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = CookieUtility.getCookies(request);
        if (cookieMap != null && cookieMap.containsKey("session_id")) {
            String key = ((Cookie)cookieMap.get("session_id")).getValue();
            return this.userTemplate.delete(key);
        } else {
            return false;
        }
    }

    public User currentUser(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = CookieUtility.getCookies(request);
        User user = null;
        if (cookieMap != null && cookieMap.containsKey("session_id")) {
            String str = ((Cookie)cookieMap.get("session_id")).getValue();
            user = this.sessionGet(str);
        }

        return user;
    }
}
