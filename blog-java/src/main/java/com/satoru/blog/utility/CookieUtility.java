package com.satoru.blog.utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
public class CookieUtility {
    public static Map<String, Cookie> getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        } else {
            Map<String, Cookie> map = new HashMap();
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                map.put(cookie.getName(), cookie);
            }

            return map;
        }
    }
}
