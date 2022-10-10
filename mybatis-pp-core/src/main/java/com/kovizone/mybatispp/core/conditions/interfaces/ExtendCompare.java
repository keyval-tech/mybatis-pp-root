package com.kovizone.mybatispp.core.conditions.interfaces;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * ExtendCompare
 *
 * @author KV
 * @since 2022/09/29
 */
public interface ExtendCompare<T, Children> extends LambdaCompare<T, Children> {

    /* 新增方法 */

    /**
     * ignore
     */
    default Children binary(SFunction<T, ?> column, Object val) {
        return binary(true, column, val);
    }

    /**
     * ignore
     */
    Children binary(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children binary(String column, Object val) {
        return binary(true, column, val);
    }

    /**
     * 二进制比较
     * <p>
     * BINARY column = value
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children binary(boolean condition, String column, Object val);

    /**
     * ignore
     */
    default Children regexp(SFunction<T, ?> column, Object val) {
        return regexp(true, column, val);
    }

    /**
     * ignore
     */
    Children regexp(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children regexp(String column, Object val) {
        return regexp(true, column, val);
    }

    /**
     * 正则表达式匹配
     * <p>
     * column REGEXP value
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children regexp(boolean condition, String column, Object val);

    /**
     * ignore
     */
    default Children neOrIsNull(SFunction<T, ?> column, Object val) {
        return neOrIsNull(true, column, val);
    }

    /**
     * ignore
     */
    Children neOrIsNull(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children neOrIsNull(String column, Object val) {
        return neOrIsNull(true, column, val);
    }

    /**
     * 不等于或为空
     * <p>
     * (column != val or column IS NULL)
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children neOrIsNull(boolean condition, String column, Object val);

    /**
     * ignore
     */
    default Children likeSql(SFunction<T, ?> column, Object val) {
        return likeSql(true, column, val);
    }

    /**
     * ignore
     */
    Children likeSql(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children likeSql(String column, Object val) {
        return likeSql(true, column, val);
    }

    /**
     * LIKE语句
     * <p>
     * column LIKE val
     * <p>
     * 此方法不会自动拼接%，而like方法会在val的左右拼接%，请区分清楚
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       SQL片段
     * @return children
     */
    Children likeSql(boolean condition, String column, Object val);

    /**
     * ignore
     */
    default Children notLikeOrIsNull(SFunction<T, ?> column, Object val) {
        return notLikeOrIsNull(true, column, val);
    }

    /**
     * ignore
     */
    Children notLikeOrIsNull(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children notLikeOrIsNull(String column, Object val) {
        return notLikeOrIsNull(true, column, val);
    }

    /**
     * 模糊非匹配或为空
     * <p>
     * (column NOT LIKE CONCAT('%',val,'%') or column IS NULL)
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children notLikeOrIsNull(boolean condition, String column, Object val);
}