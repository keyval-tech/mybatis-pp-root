package com.kovizone.mybatispp.core.conditions.update;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UpdateWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper
 * @since 2022/09/28
 */
public class UpdateWrapper<T> extends AbstractUpdateExtendWrapper<T, UpdateWrapper<T>> {

    public UpdateWrapper() {
        super();
    }

    public UpdateWrapper(T entity) {
        super(entity);
    }

    public UpdateWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private UpdateWrapper(T entity, Class<T> entityClass, List<String> sqlSet, AtomicInteger paramNameSeq,
                          Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments,
                          SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super(entity, entityClass, sqlSet, paramNameSeq, paramNameValuePairs, mergeSegments, lastSql, sqlComment, sqlFirst);
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected UpdateWrapper<T> instance() {
        return new UpdateWrapper<>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs,
                new MergeSegments(), SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }
}
