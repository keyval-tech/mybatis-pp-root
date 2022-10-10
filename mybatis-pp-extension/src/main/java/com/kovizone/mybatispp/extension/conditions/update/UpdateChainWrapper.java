package com.kovizone.mybatispp.extension.conditions.update;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.update.ExtendUpdate;
import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import com.kovizone.mybatispp.extension.conditions.AbstractChainExtendWrapper;
import lombok.Getter;

/**
 * UpdateChainWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper
 * @see com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper
 * @since 2022/10/10
 */
public class UpdateChainWrapper<T> extends AbstractChainExtendWrapper<T, UpdateChainWrapper<T>, UpdateWrapper<T>>
        implements ChainUpdate<T, UpdateWrapper<T>, UpdateChainWrapper<T>>, ExtendUpdate<T, UpdateChainWrapper<T>> {

    @Getter
    private final BaseMapper<T> baseMapper;

    @Getter
    private UpdateWrapper<T> wrapper;

    @Override
    public UpdateChainWrapper<T> setWrapper(UpdateWrapper<T> wrapper) {
        this.wrapper = (wrapper == null ? new UpdateWrapper<>() : wrapper);
        return this;
    }

    public UpdateChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        this.wrapper = new UpdateWrapper<>();
    }

    public UpdateChainWrapper(BaseMapper<T> baseMapper, UpdateWrapper<T> wrapper) {
        super();
        this.baseMapper = baseMapper;
        this.wrapper = wrapper;
    }

    @Override
    public UpdateChainWrapper<T> set(boolean condition, String column, Object val, String mapping) {
        getWrapper().set(condition, column, val, mapping);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> setSql(boolean condition, String sql) {
        getWrapper().setSql(condition, sql);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> concat(boolean condition, SFunction<T, ?> column, Object... values) {
        getWrapper().concat(condition, column, values);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> concat(boolean condition, String column, Object... values) {
        getWrapper().concat(condition, column, values);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> incr(boolean condition, SFunction<T, ?> column, Object increment) {
        getWrapper().incr(condition, column, increment);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> incr(boolean condition, String column, Object increment) {
        getWrapper().incr(condition, column, increment);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> cas(boolean condition, SFunction<T, ?> column, Object version) {
        getWrapper().cas(condition, column, version);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> cas(boolean condition, String column, Object version) {
        getWrapper().cas(condition, column, version);
        return this;
    }

    @Override
    public UpdateChainWrapper<T> set(boolean condition, SFunction<T, ?> column, Object val, String mapping) {
        getWrapper().set(condition, column, val, mapping);
        return this;
    }

    @Override
    public String getSqlSet() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }
}
