package com.hongfei.springbootshiro.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author hongfei.shen
 * @date 2019/06/03
 */
@Slf4j
public class MD5Util {
    /**
     * 生成密码
     *
     * @param password 密码
     * @param salt     密码盐
     * @return
     */
    public static String createPassword(String password, String salt, int hashIterations) {
        Md5Hash md5Hash = new Md5Hash(password.trim(), salt, hashIterations);
        return md5Hash.toString();
    }
}
