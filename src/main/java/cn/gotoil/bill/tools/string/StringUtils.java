/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.common.tools.string.StringUtils
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/5/17 3:45 PM
 *
 */

package cn.gotoil.bill.tools.string;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String repeat(String base, int repeat) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            stringBuilder.append(base);
        }
        return stringBuilder.toString();
    }

    /**
     * 把字符串里的占位符替换成对应的值
     *
     * @param str /v1/lottery/issue/{0}
     * @param arr
     * @return 替换后的str
     */
    public static String fillStringByArgs(String str, String[] arr) {
        Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), arr[Integer.parseInt(m.group(1))]);
        }
        return str;
    }

}
