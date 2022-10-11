package com.kovizone.mybatispp.core.conditions.update;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;
import com.kovizone.mybatispp.core.conditions.ChainWrapper;

/**
 * ChainUpdate
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.update.ChainUpdate
 * @since 2022/10/10
 */
public interface ChainUpdate<T, Children extends AbstractExtendWrapper<T, Children>> extends ChainWrapper<T, Children> {

    /**
     * 更新数据
     *
     * @return 是否成功
     */
    default boolean update() {
        return update(null);
    }

    /**
     * 更新数据
     *
     * @param entity 实体
     * @return 是否成功
     */
    default boolean update(T entity) {
        return SqlHelper.retBool(getBaseMapper().update(entity, (Wrapper<T>) this));
    }

    /**
     * 删除数据
     *
     * @return 是否成功
     */
    default boolean remove() {
        return SqlHelper.retBool(getBaseMapper().delete((Wrapper<T>) this));
    }
}
