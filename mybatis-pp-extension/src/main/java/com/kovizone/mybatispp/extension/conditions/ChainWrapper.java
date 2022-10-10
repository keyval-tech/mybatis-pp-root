package com.kovizone.mybatispp.extension.conditions;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.kovizone.mybatispp.core.mapper.BaseMapper;

/**
 * ChainWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.ChainWrapper
 * @since 2022/10/10
 */
public interface ChainWrapper<T, W extends Wrapper<T>, Children> {

    /**
     * 获取 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 获取最终拿去执行的 Wrapper
     *
     * @return Wrapper
     */
    W getWrapper();

    /**
     * 设置最终拿去执行的 Wrapper
     *
     * @return children
     */
    Children setWrapper(W wrapper);
}