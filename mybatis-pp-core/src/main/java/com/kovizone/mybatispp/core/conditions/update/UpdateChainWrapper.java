package com.kovizone.mybatispp.core.conditions.update;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.query.ChainQuery;
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import com.kovizone.mybatispp.core.toolkit.MapperUtil;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UpdateChainWrapper
 *
 * @author KV
 * @since 2022/10/11
 */
public class UpdateChainWrapper<T> extends AbstractUpdateExtendWrapper<T, UpdateChainWrapper<T>>
        implements ChainUpdate<T, UpdateChainWrapper<T>> {

    @Getter
    private final BaseMapper<T> baseMapper;

    private UpdateChainWrapper() {
        throw new IllegalArgumentException();
    }

    private UpdateChainWrapper(T entity) {
        throw new IllegalArgumentException();
    }

    private UpdateChainWrapper(Class<T> entityClass) {
        throw new IllegalArgumentException();
    }

    public UpdateChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        setEntityClass(MapperUtil.extractModelClass(baseMapper));
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private UpdateChainWrapper(T entity, Class<T> entityClass, List<String> sqlSet, AtomicInteger paramNameSeq,
                               Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments,
                               SharedString lastSql, SharedString sqlComment, SharedString sqlFirst,
                               BaseMapper<T> baseMapper) {
        super(entity, entityClass, sqlSet, paramNameSeq, paramNameValuePairs, mergeSegments, lastSql, sqlComment, sqlFirst);
        this.baseMapper = baseMapper;
    }

    @Override
    public Wrapper<T> getWrapper() {
        return this;
    }

    @Override
    public <R> UpdateChainWrapper<T> inWrapper(boolean condition, String column, ChainQuery<R, ?> queryWrapper) {
        if (condition) {
            return in(column, queryWrapper.getBaseMapper().selectObjs(queryWrapper.getWrapper()));
        }
        return this;
    }

    @Override
    public <R> UpdateChainWrapper<T> inWrapper(boolean condition, SFunction<T, ?> column, ChainQuery<R, ?> queryWrapper) {
        return inWrapper(condition, columnToString(column), queryWrapper);
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected UpdateChainWrapper<T> instance() {
        return new UpdateChainWrapper<T>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs,
                new MergeSegments(), SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString(),
                baseMapper);
    }
}
