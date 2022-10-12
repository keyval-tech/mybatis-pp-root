package com.kovizone.mybatispp.core.conditions.query;

import com.kovizone.mybatispp.core.conditions.OnSql;
import com.kovizone.mybatispp.core.enums.JoinType;
import com.kovizone.mybatispp.core.mapper.BaseMapper;

import java.util.function.Consumer;

/**
 * ChainJoinQuery
 *
 * @author KV
 * @since 2022/10/12
 */
public interface ChainJoin<T1, Children> {

    /**
     * ignore
     */
    default <T2> Children leftJoin(BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return leftJoin(true, mapper2, onConsumer);
    }

    /**
     * 左联查
     *
     * @param condition  执行条件
     * @param mapper2    联表的Mapper
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    default <T2> Children leftJoin(boolean condition, BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.LEFT_JOIN, mapper2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children leftJoin(BaseMapper<T2> mapper2, String... onSql) {
        return leftJoin(true, mapper2, onSql);
    }

    /**
     * 左联查
     *
     * @param condition 执行条件
     * @param mapper2   联表的Mapper
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children leftJoin(boolean condition, BaseMapper<T2> mapper2, String... onSql) {
        return join(condition, JoinType.LEFT_JOIN, mapper2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children rightJoin(BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return rightJoin(true, mapper2, onConsumer);
    }

    /**
     * 右联查
     *
     * @param condition  执行条件
     * @param mapper2    联表的Mapper
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    default <T2> Children rightJoin(boolean condition, BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.RIGHT_JOIN, mapper2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children rightJoin(BaseMapper<T2> mapper2, String... onSql) {
        return rightJoin(true, mapper2, onSql);
    }

    /**
     * 右联查
     *
     * @param condition 执行条件
     * @param mapper2   联表的Mapper
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children rightJoin(boolean condition, BaseMapper<T2> mapper2, String... onSql) {
        return join(condition, JoinType.RIGHT_JOIN, mapper2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children innerJoin(BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onSqlConsumer) {
        return innerJoin(true, mapper2, onSqlConsumer);
    }

    /**
     * 内联查
     *
     * @param condition  执行条件
     * @param mapper2    联表的Mapper
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    default <T2> Children innerJoin(boolean condition, BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.INNER_JOIN, mapper2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children innerJoin(BaseMapper<T2> mapper2, String... onSql) {
        return innerJoin(true, mapper2, onSql);
    }

    /**
     * 内联查
     *
     * @param condition 执行条件
     * @param mapper2   联表的Mapper
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    default <T2> Children innerJoin(boolean condition, BaseMapper<T2> mapper2, String... onSql) {
        return join(condition, JoinType.INNER_JOIN, mapper2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children join(BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(true, null, mapper2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children join(boolean condition, BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(condition, JoinType.LEFT_JOIN, mapper2, onConsumer);
    }

    /**
     * ignore
     */
    default <T2> Children join(BaseMapper<T2> mapper2, String... onSql) {
        return join(true, JoinType.LEFT_JOIN, mapper2, onSql);
    }

    /**
     * ignore
     */
    default <T2> Children join(boolean condition, BaseMapper<T2> mapper2, String... onSql) {
        return join(condition, JoinType.LEFT_JOIN, mapper2, onSql);
    }


    /**
     * ignore
     */
    default <T2> Children join(JoinType joinType, BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer) {
        return join(true, joinType, mapper2, onConsumer);
    }

    /**
     * 联查
     *
     * @param condition  执行条件
     * @param joinType   联查类型
     * @param mapper2    联表的Mapper
     * @param onConsumer 描述联查ON条件
     * @param <T2>       联表实体类
     * @return children
     */
    <T2> Children join(boolean condition, JoinType joinType, BaseMapper<T2> mapper2, Consumer<OnSql<T1, T2>> onConsumer);

    /**
     * ignore
     */
    default <T2> Children join(JoinType joinType, BaseMapper<T2> mapper2, String... onSql) {
        return join(true, joinType, mapper2, onSql);
    }

    /**
     * 联查
     *
     * @param condition 执行条件
     * @param joinType  联查类型
     * @param mapper2   联表的Mapper
     * @param onSql     联查ON条件
     * @param <T2>      联表实体类
     * @return children
     */
    <T2> Children join(boolean condition, JoinType joinType, BaseMapper<T2> mapper2, String... onSql);


    /**
     * ignore
     */
    default <T2> Children func(BaseMapper<T2> mapper2, Consumer<QueryChainWrapper<T2>> whereConsumer) {
        return func(true, mapper2, whereConsumer);
    }

    /**
     * 切换选择包装类
     *
     * @param condition     执行条件
     * @param mapper2       联表的Mapper
     * @param whereConsumer 描述联查WHERE条件
     * @param <T2>          联表实体类
     * @return children
     */
    <T2> Children func(boolean condition, BaseMapper<T2> mapper2, Consumer<QueryChainWrapper<T2>> whereConsumer);
}
