package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.annotation.TableJoin;
import com.kovizone.mybatispp.annotation.TableJoins;
import com.kovizone.mybatispp.core.conditions.OnSql;
import com.kovizone.mybatispp.core.enums.JoinType;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * ExtendQuery
 *
 * @author KV
 * @since 2022/09/29
 */
public interface ExtendQuery<T1, Children> extends LambdaQuery<T1, Children> {

    /**
     * ignore
     */
    @SuppressWarnings("unchecked")
    Children distinct(SFunction<T1, ?>... columns);

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
    Children appendSelect(SFunction<T1, ?>... columns);

    /**
     * ignore
     */
    default <T2> Children leftJoin(Class<T2> model2) {
        return leftJoin(true, model2);
    }

    /**
     * 左联查
     * <p>
     * model2需要标注{@link TableJoin}或{@link TableJoins}
     *
     * @param condition 执行条件
     * @param model2    联表的实体
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children leftJoin(boolean condition, Class<T2> model2) {
        return join(condition, JoinType.LEFT_JOIN, model2, (String[]) null);
    }

    /**
     * ignore
     */
    default <T2> Children leftJoin(Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return leftJoin(true, model2, onConsumer);
    }

    /**
     * 左联查
     *
     * @param condition  执行条件
     * @param model2     联表的实体
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    default <T2> Children leftJoin(boolean condition, Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.LEFT_JOIN, model2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children leftJoin(Class<T2> model2, String... onSql) {
        return leftJoin(true, model2, onSql);
    }

    /**
     * 左联查
     *
     * @param condition 执行条件
     * @param model2    联表的实体
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children leftJoin(boolean condition, Class<T2> model2, String... onSql) {
        return join(condition, JoinType.LEFT_JOIN, model2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children rightJoin(Class<T2> model2) {
        return rightJoin(true, model2);
    }

    /**
     * 右联查
     * <p>
     * model2需要标注{@link TableJoin}或{@link TableJoins}
     *
     * @param condition 执行条件
     * @param model2    联表的实体
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children rightJoin(boolean condition, Class<T2> model2) {
        return join(condition, JoinType.RIGHT_JOIN, model2, (String[]) null);
    }

    /**
     * ignore
     */
    default <T2> Children rightJoin(Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return rightJoin(true, model2, onConsumer);
    }

    /**
     * 右联查
     *
     * @param condition  执行条件
     * @param model2     联表的实体
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    default <T2> Children rightJoin(boolean condition, Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.RIGHT_JOIN, model2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children rightJoin(Class<T2> model2, String... onSql) {
        return rightJoin(true, model2, onSql);
    }

    /**
     * 右联查
     *
     * @param condition 执行条件
     * @param model2    联表的实体
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children rightJoin(boolean condition, Class<T2> model2, String... onSql) {
        return join(condition, JoinType.RIGHT_JOIN, model2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children innerJoin(Class<T2> model2) {
        return innerJoin(true, model2);
    }

    /**
     * 内联查
     * <p>
     * model2需要标注{@link TableJoin}或{@link TableJoins}
     *
     * @param condition 执行条件
     * @param model2    联表的实体
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children innerJoin(boolean condition, Class<T2> model2) {
        return join(condition, JoinType.INNER_JOIN, model2, (String[]) null);
    }

    /**
     * ignore
     */
    default <T2> Children innerJoin(Class<T2> model2, Consumer<OnSql<T1, T2>> onSqlConsumer) {
        return innerJoin(true, model2, onSqlConsumer);
    }

    /**
     * 内联查
     *
     * @param condition  执行条件
     * @param model2     联表的实体
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    default <T2> Children innerJoin(boolean condition, Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.INNER_JOIN, model2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children innerJoin(Class<T2> model2, String... onSql) {
        return innerJoin(true, model2, onSql);
    }

    /**
     * 内联查
     *
     * @param condition 执行条件
     * @param model2    联表的实体
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children innerJoin(boolean condition, Class<T2> model2, String... onSql) {
        return join(condition, JoinType.INNER_JOIN, model2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children join(Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(true, null, model2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children join(boolean condition, Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.LEFT_JOIN, model2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children join(Class<T2> model2, String... onSql) {
        return join(true, JoinType.LEFT_JOIN, model2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children join(boolean condition, Class<T2> model2, String... onSql) {
        return join(condition, JoinType.LEFT_JOIN, model2, onSql);
    }


    /**
     * ignore
     */
    default <T2> Children join(JoinType joinType, Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(true, joinType, model2, onConsumer);
    }

    /**
     * 联查
     *
     * @param condition  执行条件
     * @param joinType   联查类型
     * @param model2     联表的实体
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    <T2> Children join(boolean condition, JoinType joinType, Class<T2> model2, Consumer<OnSql<T1, T2>> onConsumer);

    /**
     * ignore
     */
    default <T2> Children join(JoinType joinType, Class<T2> model2, String... onSql) {
        return join(true, joinType, model2, onSql);
    }

    /**
     * 联查
     *
     * @param condition 执行条件
     * @param joinType  联查类型
     * @param model2    联表的实体
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    <T2> Children join(boolean condition, JoinType joinType, Class<T2> model2, String... onSql);

    /**
     * ignore
     */
    default <T2> Children func(Class<T2> model2, Consumer<QueryWrapper<T2>> whereConsumer) {
        return func(true, model2, whereConsumer);
    }

    /**
     * 切换选择包装类
     *
     * @param condition     执行条件
     * @param model2        联表的实体
     * @param whereConsumer 描述联查WHERE条件
     * @param <T2>          联表实体类
     * @return children
     */
    <T2> Children func(boolean condition, Class<T2> model2, Consumer<QueryWrapper<T2>> whereConsumer);
}
