package com.kovizone.mybatispp.extension.conditions.query;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.query.ExtendQuery;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import com.kovizone.mybatispp.extension.conditions.AbstractChainExtendWrapper;
import lombok.Getter;

import java.util.function.Predicate;

/**
 * QueryChainWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper
 * @see com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper
 * @since 2022/10/10
 */
public class QueryChainWrapper<T> extends AbstractChainExtendWrapper<T, QueryChainWrapper<T>, QueryWrapper<T>>
        implements ChainQuery<T, QueryWrapper<T>, QueryChainWrapper<T>>, ExtendQuery<T, QueryChainWrapper<T>> {

    @Getter
    private final BaseMapper<T> baseMapper;

    @Getter
    private QueryWrapper<T> wrapper;

    @Override
    public QueryChainWrapper<T> setWrapper(QueryWrapper<T> wrapper) {
        this.wrapper = (wrapper == null ? new QueryWrapper<>() : wrapper);
        return this;
    }

    public QueryChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        this.wrapper = new QueryWrapper<>();
    }

    public QueryChainWrapper(BaseMapper<T> baseMapper, QueryWrapper<T> wrapper) {
        super();
        this.baseMapper = baseMapper;
        this.wrapper = wrapper;
    }

    @Override
    public QueryChainWrapper<T> select(String... columns) {
        getWrapper().select(columns);
        return this;
    }

    @Override
    public QueryChainWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        getWrapper().select(entityClass, predicate);
        return this;
    }

    @SafeVarargs
    @Override
    public final QueryChainWrapper<T> distinct(SFunction<T, ?>... columns) {
        getWrapper().distinct(columns);
        return this;
    }

    @SafeVarargs
    @Override
    public final QueryChainWrapper<T> select(SFunction<T, ?>... columns) {
        getWrapper().select(columns);
        return this;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }
}
