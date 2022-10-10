package com.kovizone.mybatispp.core.conditions.interfaces;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Collection;

/**
 * ExtendFunc
 *
 * @author KV
 * @since 2022/09/29
 */
public interface ExtendFunc<T, Children> extends LambdaFunc<T, Children> {

    /* 新增方法 */

    /**
     * ignore
     */
    default Children orderBySql(String val) {
        return orderBySql(true, val);
    }

    Children orderBySql(boolean condition, String val);

    /**
     * ignore
     */
    default Children orderByField(String column, Object... values) {
        return orderByField(true, column, values);
    }

    /**
     * ignore
     */
    default Children orderByField(SFunction<T, ?> column, Object... values) {
        return orderByField(true, column, values);
    }

    /**
     * ignore
     */
    default Children orderByField(String column, Collection<?> values) {
        return orderByField(true, column, values);
    }

    /**
     * ignore
     */
    default <V> Children orderByField(SFunction<T, V> column, Collection<V> values) {
        return orderByField(true, column, values);
    }

    /**
     * ignore
     */
    default Children orderByField(boolean condition, String column, Collection<?> coll) {
        return orderByField(condition, column, coll.toArray());
    }

    /**
     * ignore
     */
    default Children orderByField(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        return orderByField(condition, column, coll.toArray());
    }

    /**
     * ignore
     */
    Children orderByField(boolean condition, SFunction<T, ?> column, Object... values);

    /**
     * 使用FIELD函数排序
     * <p>
     * ORDER BY FIELD(column, value.get(0), value.get(1), ...)
     * <p>
     * 仅适用于MYSQL
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    值集
     * @return children
     */
    Children orderByField(boolean condition, String column, Object... values);

    /**
     * ignore
     */
    default Children notInOrIsNull(String column, Collection<?> coll) {
        return notInOrIsNull(true, column, coll);
    }

    /**
     * ignore
     */
    default Children notInOrIsNull(SFunction<T, ?> column, Collection<?> coll) {
        return notInOrIsNull(true, column, coll);
    }

    /**
     * ignore
     */
    default Children notInOrIsNull(String column, Object... values) {
        return notInOrIsNull(true, column, values);
    }

    /**
     * ignore
     */
    default Children notInOrIsNull(SFunction<T, ?> column, Object... values) {
        return notInOrIsNull(true, column, values);
    }

    /**
     * ignore
     */
    default Children notInOrIsNull(boolean condition, String column, Collection<?> coll) {
        return notInOrIsNull(condition, column, coll.toArray());
    }

    /**
     * ignore
     */
    default Children notInOrIsNull(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        return notInOrIsNull(condition, column, coll.toArray());
    }

    /**
     * ignore
     */
    Children notInOrIsNull(boolean condition, SFunction<T, ?> column, Object... values);

    /**
     * 不包含或为空
     * <p>
     * (column NOT IN (value.get(0), value.get(1), ...) OR column IS NULL)
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    数据集合
     * @return children
     */
    Children notInOrIsNull(boolean condition, String column, Object... values);

    /**
     * ignore
     */
    default Children isEmpty(String column) {
        return isEmpty(true, column);
    }

    /**
     * ignore
     */
    default Children isEmpty(SFunction<T, ?> column) {
        return isEmpty(true, column);
    }

    /**
     * ignore
     */
    Children isEmpty(boolean condition, SFunction<T, ?> column);

    /**
     * 为NULL或空字符串
     * <p>
     * (column IS NULL OR column = '')
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    Children isEmpty(boolean condition, String column);
}
