package com.kovizone.mybatispp.core.conditions.interfaces;

import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Collection;

/**
 * LambdaFunc
 * <p>
 * 基于{@link Func}扩展
 *
 * @author KV
 * @see Func
 * @since 2022/09/14
 */
public interface LambdaFunc<T, Children> extends Func<Children, String> {

    /**
     * ignore
     */
    default Children isNull(SFunction<T, ?> column) {
        return isNull(true, column);
    }

    /**
     * ignore
     */
    Children isNull(boolean condition, SFunction<T, ?> column);

    /**
     * ignore
     */
    default Children isNotNull(SFunction<T, ?> column) {
        return isNotNull(true, column);
    }

    /**
     * ignore
     */
    Children isNotNull(boolean condition, SFunction<T, ?> column);

    /**
     * ignore
     */
    default Children in(SFunction<T, ?> column, Collection<?> coll) {
        return in(true, column, coll);
    }

    /**
     * ignore
     */
    Children in(boolean condition, SFunction<T, ?> column, Collection<?> coll);

    /**
     * ignore
     */
    default Children in(SFunction<T, ?> column, Object... values) {
        return in(true, column, values);
    }

    /**
     * ignore
     */
    Children in(boolean condition, SFunction<T, ?> column, Object... values);

    /**
     * ignore
     */
    default Children notIn(SFunction<T, ?> column, Collection<?> coll) {
        return notIn(true, column, coll);
    }

    /**
     * ignore
     */
    Children notIn(boolean condition, SFunction<T, ?> column, Collection<?> coll);

    /**
     * ignore
     */
    default Children notIn(SFunction<T, ?> column, Object... values) {
        return notIn(true, column, values);
    }

    /**
     * ignore
     */
    Children notIn(boolean condition, SFunction<T, ?> column, Object... values);

    /**
     * ignore
     */
    default Children inSql(SFunction<T, ?> column, String inValue) {
        return inSql(true, column, inValue);
    }

    /**
     * ignore
     */
    Children inSql(boolean condition, SFunction<T, ?> column, String inValue);

    /**
     * ignore
     */
    default Children gtSql(SFunction<T, ?> column, String inValue) {
        return gtSql(true, column, inValue);
    }

    /**
     * ignore
     */
    Children gtSql(boolean condition, SFunction<T, ?> column, String inValue);

    /**
     * ignore
     */
    default Children geSql(SFunction<T, ?> column, String inValue) {
        return geSql(true, column, inValue);
    }

    /**
     * ignore
     */
    Children geSql(boolean condition, SFunction<T, ?> column, String inValue);

    /**
     * ignore
     */
    default Children ltSql(SFunction<T, ?> column, String inValue) {
        return ltSql(true, column, inValue);
    }

    /**
     * ignore
     */
    Children ltSql(boolean condition, SFunction<T, ?> column, String inValue);

    /**
     * ignore
     */
    default Children leSql(SFunction<T, ?> column, String inValue) {
        return leSql(true, column, inValue);
    }

    /**
     * ignore
     */
    Children leSql(boolean condition, SFunction<T, ?> column, String inValue);

    /**
     * ignore
     */
    default Children notInSql(SFunction<T, ?> column, String inValue) {
        return notInSql(true, column, inValue);
    }

    /**
     * ignore
     */
    Children notInSql(boolean condition, SFunction<T, ?> column, String inValue);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children groupBy(SFunction<T, ?> column, SFunction<T, ?>... columns);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children groupBy(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children orderByAsc(SFunction<T, ?> column, SFunction<T, ?>... columns);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children orderByAsc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children orderByDesc(SFunction<T, ?> column, SFunction<T, ?>... columns);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children orderByDesc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns);

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children orderBy(boolean condition, boolean isAsc, SFunction<T, ?> column, SFunction<T, ?>... columns);
}