package com.kovizone.mybatispp.core.converter;

import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;
import com.kovizone.mybatispp.core.toolkit.WrapperUtil;

/**
 * 能更新
 *
 * @author KV
 * @since 2022/09/29
 */
public interface Updatable<T> {

    /**
     * 转为更新条件包装类
     *
     * @return 更新条件包装类
     */
    default UpdateWrapper<T> toUpdate() {
        return WrapperUtil.update(this);
    }

}
