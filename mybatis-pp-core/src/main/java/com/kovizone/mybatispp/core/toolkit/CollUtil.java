package com.kovizone.mybatispp.core.toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CollUtil
 *
 * @author KV
 * @since 2022/09/28
 */
public class CollUtil {

    /**
     * 是否为空集合
     *
     * @param coll 集合
     * @return 结果
     */
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 是否非空集合
     *
     * @param coll 集合
     * @return 结果
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 映射
     *
     * @param list   实体集
     * @param mapper 映射逻辑
     * @param <T>    实体类
     * @param <R>    变量类
     * @return 变量值集
     */
    public static <T, R> List<R> map(Collection<T> list, Function<T, R> mapper) {
        return map(list, null, mapper);
    }

    /**
     * 映射
     *
     * @param list   实体集
     * @param filter 过滤方法
     * @param mapper 映射逻辑
     * @param <T>    实体类
     * @param <R>    变量类
     * @return 变量值集
     */
    public static <T, R> List<R> map(Collection<T> list, Predicate<? super T> filter, Function<T, R> mapper) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream()
                .filter(filter == null ? (e -> true) : filter)
                .map(mapper)
                .collect(Collectors.toList());
    }
}
