package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * QueryChainWrapper2
 *
 * @author KV
 * @see QueryWrapper
 * @since 2022/10/11
 */
public class QueryChainWrapper<T> extends AbstractQueryExtendWrapper<T, QueryChainWrapper<T>>
        implements ChainQuery<T, QueryChainWrapper<T>> {

    @Getter
    private final BaseMapper<T> baseMapper;

    private QueryChainWrapper() {
        throw new IllegalArgumentException();
    }

    private QueryChainWrapper(T entity) {
        throw new IllegalArgumentException();
    }

    private QueryChainWrapper(Class<T> entityClass) {
        throw new IllegalArgumentException();
    }

    private QueryChainWrapper(T entity, String... columns) {
        throw new IllegalArgumentException();
    }

    public QueryChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private QueryChainWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                              Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                              SharedString lastSql, SharedString sqlComment, SharedString sqlFirst,
                              BaseMapper<T> baseMapper) {
        super(entity, entityClass, paramNameSeq, paramNameValuePairs, mergeSegments, paramAlias, lastSql, sqlComment, sqlFirst);
        this.baseMapper = baseMapper;
    }

    @Override
    public Wrapper<T> getWrapper() {
        return this;
    }

    @Override
    public <R> QueryChainWrapper<T> inWrapper(boolean condition, String column, ChainQuery<R, ?> queryWrapper) {
        if (condition) {
            return in(column, queryWrapper.getBaseMapper().selectObjs(queryWrapper.getWrapper()));
        }
        return this;
    }

    @Override
    public <R> QueryChainWrapper<T> inWrapper(boolean condition, SFunction<T, ?> column, ChainQuery<R, ?> queryWrapper) {
        return inWrapper(condition, columnToString(column), queryWrapper);
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected QueryChainWrapper<T> instance() {
        return new QueryChainWrapper<T>(getEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs, new MergeSegments(),
                paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString(), baseMapper);
    }
}
