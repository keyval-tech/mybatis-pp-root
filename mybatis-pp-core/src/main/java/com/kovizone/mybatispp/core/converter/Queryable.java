package com.kovizone.mybatispp.core.converter;

import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.toolkit.WrapperUtil;

/**
 * 能查询
 *
 * @author KV
 * @since 2022/09/28
 */
public interface Queryable<T> {

    /**
     * 转为查询条件包装类
     *
     * @return 查询条件包装类
     */
    default QueryWrapper<T> toQuery() {
        return WrapperUtil.query(this);
    }

}
