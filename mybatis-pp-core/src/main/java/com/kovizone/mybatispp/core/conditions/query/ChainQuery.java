package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.kovizone.mybatispp.core.conditions.ChainWrapper;

import java.util.List;
import java.util.Optional;

/**
 * ChainQuery
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.query.ChainQuery
 * @since 2022/10/10
 */
public interface ChainQuery<T, Children> extends ChainWrapper<T, Children> {

    /**
     * 获取集合
     *
     * @return 集合
     */
    default List<T> list() {
        return getBaseMapper().selectList(getWrapper());
    }

    /**
     * 获取单个
     *
     * @return 单个
     */
    default T one() {
        return getBaseMapper().selectOne(getWrapper());
    }

    /**
     * 获取单个
     *
     * @return 单个
     * @since 3.3.0
     */
    default Optional<T> oneOpt() {
        return Optional.ofNullable(one());
    }

    /**
     * 获取 count
     *
     * @return count
     */
    default Long count() {
        return SqlHelper.retCount(getBaseMapper().selectCount(getWrapper()));
    }

    /**
     * 判断数据是否存在
     *
     * @return true 存在 false 不存
     */
    default boolean exists() {
        return this.count() > 0;
    }

    /**
     * 获取分页数据
     *
     * @param page 分页条件
     * @return 分页数据
     */
    default <E extends IPage<T>> E page(E page) {
        return getBaseMapper().selectPage(page, getWrapper());
    }
}
