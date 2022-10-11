package com.kovizone.mybatispp.core.conditions;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.query.ChainQuery;
import com.kovizone.mybatispp.core.mapper.BaseMapper;

/**
 * ChainWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.ChainWrapper
 * @since 2022/10/10
 */
public interface ChainWrapper<T, Children> {

    /**
     * 获取 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 获取最终拿去执行的 Wrapper
     *
     * @return Wrapper
     */
    Wrapper<T> getWrapper();

    /**
     * ignore
     */
    default <R> Children inWrapper(String column, ChainQuery<R, ?> queryWrapper) {
        return inWrapper(true, column, queryWrapper);
    }

    /**
     * in 另一个ChainQuery的结果
     *
     * @param condition    执行条件
     * @param column       字段
     * @param queryWrapper 另一个ChainQuery
     * @param <R>          另一个ChainQuery的实体
     * @return children
     */
    <R> Children inWrapper(boolean condition, String column, ChainQuery<R, ?> queryWrapper);

    /**
     * ignore
     */
    default <R> Children inWrapper(SFunction<T, ?> column, ChainQuery<R, ?> queryWrapper) {
        return inWrapper(true, column, queryWrapper);
    }

    /**
     * in 另一个ChainQuery的结果
     *
     * @param condition    执行条件
     * @param column       字段
     * @param queryWrapper 另一个ChainQuery
     * @param <R>          另一个ChainQuery的实体
     * @return children
     */
    <R> Children inWrapper(boolean condition, SFunction<T, ?> column, ChainQuery<R, ?> queryWrapper);
}