package com.satoru.blog.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
public class Utility {
    private static Logger logger = LoggerFactory.getLogger(Utility.class);
    private static String salt = "satoru666";

    /**
     * 打印输出
     */
    public static void log(String format, Object... args) {
        logger.info(String.format(format, args));
    }

    /**
     * 对密码进行加盐,并返回md5计算后的摘要
     */
    public static String saltedPassword(String password) {
        String str = salt + password;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (Exception e) {
            log("密码加盐算摘要出错了:%s", e);
        }

        md.update(str.getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();

        String hex = new BigInteger(1, result).toString(16);
        int zeroLength = result.length * 2 - hex.length();
        for (int i = 0; i < zeroLength; i++) {
            hex = "0" + hex;
        }
        return hex;
    }


}
