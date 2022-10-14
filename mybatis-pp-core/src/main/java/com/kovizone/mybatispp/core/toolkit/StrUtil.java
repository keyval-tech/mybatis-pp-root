package com.kovizone.mybatispp.core.toolkit;

import java.util.Locale;

/**
 * StrUtil
 *
 * @author KV
 * @since 2022/09/30
 */
public class StrUtil {

    /**
     * 移除prefixes中首个匹配倒的前缀
     *
     * @param str      字符串
     * @param prefixes 前缀数组
     * @return 字符串
     */
    public static String removeAnyPrefix(String str, String... prefixes) {
        if (str != null && prefixes != null) {
            for (String prefix : prefixes) {
                if (str.startsWith(prefix)) {
                    str = str.substring(prefix.length());
                    break;
                }
            }
        }
        return str;
    }

    public static String defaultIfEmpty(String str, String defaultValue) {
        return isNotEmpty(str) ? str : defaultValue;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String upperFirstCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase(Locale.ROOT);
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT).concat(str.substring(1));
    }

    public static String lowerFirstCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase(Locale.ROOT);
        }
        return str.substring(0, 1).toLowerCase(Locale.ROOT).concat(str.substring(1));
    }

    public static boolean endWithIgnoreCase(String arg, String suffix) {
        if (arg == null || suffix == null || arg.length() < suffix.length()) {
            return false;
        }
        return arg.substring(arg.length() - suffix.length()).equalsIgnoreCase(suffix);
    }

    /**
     * 是否包含任一个
     *
     * @param arg 原文
     * @param s   检查
     * @return 结果
     */
    public static boolean containsAny(String arg, CharSequence... s) {
        if (arg != null) {
            for (CharSequence c : s) {
                if (arg.contains(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}
