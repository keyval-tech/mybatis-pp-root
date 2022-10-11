package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * QueryWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
 * @see com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
 * @since 2022/09/28
 */
public class QueryWrapper<T> extends AbstractQueryExtendWrapper<T, QueryWrapper<T>> {

    public QueryWrapper() {
        super();
    }

    public QueryWrapper(T entity) {
        super(entity);
    }

    public QueryWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    public QueryWrapper(T entity, String... columns) {
        super(entity, columns);
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private QueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                         Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                         SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super(entity, entityClass, paramNameSeq, paramNameValuePairs, mergeSegments, paramAlias, lastSql, sqlComment, sqlFirst);
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected QueryWrapper<T> instance() {
        return new QueryWrapper<T>(getEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs, new MergeSegments(),
                paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }
}