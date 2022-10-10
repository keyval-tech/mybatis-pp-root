package com.kovizone.mybatispp.core.conditions.update;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Collection;

/**
 * ExtendUpdate
 *
 * @author KV
 * @since 2022/09/29
 */
public interface ExtendUpdate<T, Children> extends LambdaUpdate<T, Children> {

    /* 新增方法 */

    /**
     * ignore
     */
    default Children concat(SFunction<T, ?> column, Collection<?> coll) {
        return concat(true, column, coll);
    }

    /**
     * ignore
     */
    default Children concat(String column, Collection<?> coll) {
        return concat(true, column, coll);
    }

    /**
     * ignore
     */
    default Children concat(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        return concat(condition, column, coll.toArray());
    }

    /**
     * ignore
     */
    default Children concat(boolean condition, String column, Collection<?> coll) {
        return concat(condition, column, coll.toArray());
    }

    /**
     * ignore
     */
    default Children concat(SFunction<T, ?> column, Object... values) {
        return concat(true, column, values);
    }

    /**
     * ignore
     */
    default Children concat(String column, Object... values) {
        return concat(true, column, values);
    }

    /**
     * ignore
     */
    Children concat(boolean condition, SFunction<T, ?> column, Object... values);

    /**
     * 串联
     * <p>
     * column = CONCAT_WS('', column, values.get(0), values.get(1), ...)
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    值
     * @return children
     */
    Children concat(boolean condition, String column, Object... values);

    /**
     * ignore
     */
    default Children incr(SFunction<T, ?> column, Object increment) {
        return incr(true, column, increment);
    }

    /**
     * ignore
     */
    default Children incr(String column, Object increment) {
        return incr(true, column, increment);
    }

    /**
     * ignore
     */
    Children incr(boolean condition, SFunction<T, ?> column, Object increment);

    /**
     * 数值增加
     * <p>
     * column = column + increment
     *
     * @param condition 执行条件
     * @param column    字段
     * @param increment 增加值（支持负数）
     * @return children
     */
    Children incr(boolean condition, String column, Object increment);

    /**
     * ignore
     */
    default Children cas(Object version) {
        return cas(true, (String) null, version);
    }

    /**
     * 乐观锁逻辑
     * <p>
     * 识别实体中标记{@link Version}注解的字段
     * <p>
     * SET column = column + 1 WHERE column = version
     *
     * @param condition 执行条件
     * @param version   检查值
     * @return children
     */
    default Children cas(boolean condition, Object version) {
        return cas(condition, (String) null, version);
    }

    /**
     * ignore
     */
    default Children cas(SFunction<T, ?> column, Object version) {
        return cas(true, column, version);
    }

    /**
     * ignore
     */
    default Children cas(String column, Object version) {
        return cas(true, column, version);
    }

    /**
     * ignore
     */
    Children cas(boolean condition, SFunction<T, ?> column, Object version);

    /**
     * 乐观锁逻辑
     * <p>
     * SET column = column + 1 WHERE column = version
     *
     * @param condition 执行条件
     * @param column    字段
     * @param version   检查值
     * @return children
     */
    Children cas(boolean condition, String column, Object version);
}
