package com.kovizone.mybatispp.core.conditions.update;

import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * LambdaUpdate
 *
 * @author KV
 * @since 2022/09/28
 */
public interface LambdaUpdate<T, Children> extends Update<Children, String> {

    /**
     * ignore
     */
    default Children set(SFunction<T, ?> column, Object val) {
        return set(true, column, val);
    }

    /**
     * ignore
     */
    default Children set(boolean condition, SFunction<T, ?> column, Object val) {
        return set(condition, column, val, null);
    }

    /**
     * ignore
     */
    Children set(boolean condition, SFunction<T, ?> column, Object val, String mapping);
}
