package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * LambdaQuery
 *
 * @author KV
 * @since 2022/09/28
 */
public interface LambdaQuery<T, Children> extends Query<Children, T, String> {

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children select(SFunction<T, ?>... columns);
}