package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Arrays;

/**
 * ExtendQuery
 *
 * @author KV
 * @since 2022/09/29
 */
public interface ExtendQuery<T, Children> extends LambdaQuery<T, Children> {

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children distinct(SFunction<T, ?>... columns);

    /**
     * 设置查询字段，加上去重关键字
     * <p>
     * SELECT DISTINCT columns.get(0), columns.get(1), ...
     *
     * @param columns 字段数组
     * @return children
     */
    default Children distinct(String... columns) {
        String[] cols = Arrays.copyOf(columns, columns.length);
        cols[0] = "DISTINCT " + cols[0];
        return select(cols);
    }
}
