package com.kovizone.mybatispp.core.conditions.interfaces;

import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * LambdaCompare
 * <p>
 * 基于{@link Compare}扩展
 *
 * @author KV
 * @see Compare
 * @since 2022/09/14
 */
public interface LambdaCompare<T, Children> extends Compare<Children, String> {

    /**
     * ignore
     */
    default Children eq(SFunction<T, ?> column, Object val) {
        return eq(true, column, val);
    }

    /**
     * ignore
     */
    Children eq(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children ne(SFunction<T, ?> column, Object val) {
        return ne(true, column, val);
    }

    /**
     * ignore
     */
    Children ne(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children gt(SFunction<T, ?> column, Object val) {
        return gt(true, column, val);
    }

    /**
     * ignore
     */
    Children gt(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children ge(SFunction<T, ?> column, Object val) {
        return ge(true, column, val);
    }

    /**
     * ignore
     */
    Children ge(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children lt(SFunction<T, ?> column, Object val) {
        return lt(true, column, val);
    }

    /**
     * ignore
     */
    Children lt(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children le(SFunction<T, ?> column, Object val) {
        return le(true, column, val);
    }

    /**
     * ignore
     */
    Children le(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children between(SFunction<T, ?> column, Object val1, Object val2) {
        return between(true, column, val1, val2);
    }

    /**
     * ignore
     */
    Children between(boolean condition, SFunction<T, ?> column, Object val1, Object val2);

    /**
     * ignore
     */
    default Children notBetween(SFunction<T, ?> column, Object val1, Object val2) {
        return notBetween(true, column, val1, val2);
    }

    /**
     * ignore
     */
    Children notBetween(boolean condition, SFunction<T, ?> column, Object val1, Object val2);

    /**
     * ignore
     */
    default Children like(SFunction<T, ?> column, Object val) {
        return like(true, column, val);
    }

    /**
     * ignore
     */
    Children like(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children notLike(SFunction<T, ?> column, Object val) {
        return notLike(true, column, val);
    }

    /**
     * ignore
     */
    Children notLike(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children likeLeft(SFunction<T, ?> column, Object val) {
        return likeLeft(true, column, val);
    }

    /**
     * ignore
     */
    Children likeLeft(boolean condition, SFunction<T, ?> column, Object val);

    /**
     * ignore
     */
    default Children likeRight(SFunction<T, ?> column, Object val) {
        return likeRight(true, column, val);
    }

    /**
     * ignore
     */
    Children likeRight(boolean condition, SFunction<T, ?> column, Object val);
}
