package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.annotation.JoinType;
import com.kovizone.mybatispp.annotation.TableJoin;
import com.kovizone.mybatispp.annotation.TableJoins;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * ExtendQuery
 *
 * @author KV
 * @since 2022/09/29
 */
public interface ExtendQuery<T, Children> extends LambdaQuery<T, Children> {

    /**
     * 设置查询字段，加上去重关键字
     * <p>
     * SELECT DISTINCT columns.get(0), columns.get(1), ...
     *
     * @param columns 字段数组
     * @return children
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

    /**
     * 增加查询字段
     *
     * @param columns 查询字段
     * @return children
     */
    Children appendSelect(String... columns);

    /**
     * 增加查询字段
     *
     * @param columns 查询字段
     * @return children
     */
    @SuppressWarnings("unchecked")
    Children appendSelect(SFunction<T, ?>... columns);

    /**
     * 左联查
     * <p>
     * joinEntityType需要标注{@link TableJoin}或{@link TableJoins}
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children leftJoin(boolean condition, Class<Join> joinEntityType) {
        return join(condition, JoinType.LEFT, joinEntityType, (String[]) null);
    }

    /**
     * ignore
     */
    default <Join> Children leftJoin(Class<Join> joinEntityType, On<T, Join> on) {
        return leftJoin(true, joinEntityType, on);
    }

    /**
     * 左联查
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param on             描述联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children leftJoin(boolean condition, Class<Join> joinEntityType, On<T, Join> on) {
        return join(condition, JoinType.LEFT, joinEntityType, on);
    }

    /**
     * ignore
     */
    default <Join> Children leftJoin(Class<Join> joinEntityType, String... onSql) {
        return leftJoin(true, joinEntityType, onSql);
    }

    /**
     * 左联查
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param onSql          联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children leftJoin(boolean condition, Class<Join> joinEntityType, String... onSql) {
        return join(condition, JoinType.LEFT, joinEntityType, onSql);
    }

    /**
     * 右联查
     * <p>
     * joinEntityType需要标注{@link TableJoin}或{@link TableJoins}
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children rightJoin(boolean condition, Class<Join> joinEntityType) {
        return join(condition, JoinType.RIGHT, joinEntityType, (String[]) null);
    }

    /**
     * ignore
     */
    default <Join> Children rightJoin(Class<Join> joinEntityType, On<T, Join> on) {
        return rightJoin(true, joinEntityType, on);
    }

    /**
     * 右联查
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param on             描述联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children rightJoin(boolean condition, Class<Join> joinEntityType, On<T, Join> on) {
        return join(condition, JoinType.RIGHT, joinEntityType, on);
    }

    /**
     * ignore
     */
    default <Join> Children rightJoin(Class<Join> joinEntityType, String... onSql) {
        return rightJoin(true, joinEntityType, onSql);
    }

    /**
     * 右联查
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param onSql          联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children rightJoin(boolean condition, Class<Join> joinEntityType, String... onSql) {
        return join(condition, JoinType.RIGHT, joinEntityType, onSql);
    }

    /**
     * 内联查
     * <p>
     * joinEntityType需要标注{@link TableJoin}或{@link TableJoins}
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children innerJoin(boolean condition, Class<Join> joinEntityType) {
        return join(condition, JoinType.INNER, joinEntityType, (String[]) null);
    }

    /**
     * ignore
     */
    default <Join> Children innerJoin(Class<Join> joinEntityType, On<T, Join> on) {
        return innerJoin(true, joinEntityType, on);
    }

    /**
     * 内联查
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param on             描述联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children innerJoin(boolean condition, Class<Join> joinEntityType, On<T, Join> on) {
        return join(condition, JoinType.INNER, joinEntityType, on);
    }

    /**
     * ignore
     */
    default <Join> Children innerJoin(Class<Join> joinEntityType, String... onSql) {
        return innerJoin(true, joinEntityType, onSql);
    }

    /**
     * 内联查
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param onSql          联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    default <Join> Children innerJoin(boolean condition, Class<Join> joinEntityType, String... onSql) {
        return join(condition, JoinType.INNER, joinEntityType, onSql);
    }

    /**
     * ignore
     */
    default <Join> Children join(Class<Join> joinEntityType, On<T, Join> on) {
        return join(true, null, joinEntityType, on);
    }

    /**
     * ignore
     */
    default <Join> Children join(boolean condition, Class<Join> joinEntityType, On<T, Join> on) {
        return join(condition, null, joinEntityType, on);
    }

    /**
     * ignore
     */
    default <Join> Children join(Class<Join> joinEntityType, String... onSql) {
        return join(true, null, joinEntityType, onSql);
    }

    /**
     * ignore
     */
    default <Join> Children join(boolean condition, Class<Join> joinEntityType, String... onSql) {
        return join(condition, null, joinEntityType, onSql);
    }


    /**
     * ignore
     */
    default <Join> Children join(JoinType joinType, Class<Join> joinEntityType, On<T, Join> on) {
        return join(true, joinType, joinEntityType, on);
    }

    /**
     * 联查
     *
     * @param condition      执行条件
     * @param joinType       联查类型
     * @param joinEntityType 联表的实体
     * @param on             描述联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    <Join> Children join(boolean condition, JoinType joinType, Class<Join> joinEntityType, On<T, Join> on);

    /**
     * ignore
     */
    default <Join> Children join(JoinType joinType, Class<Join> joinEntityType, String... onSql) {
        return join(true, joinType, joinEntityType, onSql);
    }

    /**
     * 联查
     *
     * @param condition      执行条件
     * @param joinType       联查类型
     * @param joinEntityType 联表的实体
     * @param onSql          联查ON条件
     * @param <Join>         联表实体类
     * @return children
     */
    <Join> Children join(boolean condition, JoinType joinType, Class<Join> joinEntityType, String... onSql);

    /**
     * ignore
     */
    default <Join> Children func(Class<Join> joinEntityType, Consumer<QueryWrapper<Join>> joinConsumer) {
        return func(true, joinEntityType, joinConsumer);
    }

    /**
     * 切换选择包装类
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param joinConsumer   描述联查WHERE条件
     * @param <Join>         联表实体类
     * @return children
     */
    <Join> Children func(boolean condition, Class<Join> joinEntityType, Consumer<QueryWrapper<Join>> joinConsumer);
}
