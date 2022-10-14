package com.kovizone.mybatispp.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.query.QueryChainWrapper;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.conditions.update.UpdateChainWrapper;
import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;
import com.kovizone.mybatispp.core.toolkit.CollUtil;
import com.kovizone.mybatispp.core.toolkit.MapperUtil;
import com.kovizone.mybatispp.core.toolkit.ObjectUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * BaseMapper
 *
 * @author KV
 * @since 2022/09/29
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 生成{@link QueryWrapper}
     *
     * @return QueryWrapper:
     */
    default QueryWrapper<T> query() {
        return new QueryWrapper<>(MapperUtil.extractModelClass(this));
    }

    /**
     * 生成{@link UpdateWrapper}
     *
     * @return UpdateWrapper
     */
    default UpdateWrapper<T> update() {
        return new UpdateWrapper<>(MapperUtil.extractModelClass(this));
    }

    /**
     * 生成{@link QueryChainWrapper}
     *
     * @return QueryChainWrapper
     */
    default QueryChainWrapper<T> queryChain() {
        return new QueryChainWrapper<>(this);
    }

    /**
     * 生成{@link UpdateChainWrapper}
     *
     * @return UpdateChainWrapper
     */
    default UpdateChainWrapper<T> updateChain() {
        return new UpdateChainWrapper<>(this);
    }

    /**
     * 联查
     * <p>
     * 不支持@TableLogic
     *
     * @param wrapper 查询条件
     * @return 结果对象集
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.sqlFrom} ${ew.customSqlSegment}")
    List<T> selectJoinList(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

    /**
     * 联查
     * <p>
     * 不支持@TableLogic
     *
     * @param wrapper 查询条件
     * @return 结果Map集
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.sqlFrom} ${ew.customSqlSegment}")
    List<Map<String, Object>> selectJoinMaps(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

    // 重载selectById

    /**
     * 重载selectById
     *
     * @param id     ID
     * @param column 属性
     * @param <M>    映射结果类型
     * @return 映射结果
     */
    default <M> M selectById(Serializable id, SFunction<T, M> column) {
        return ObjectUtil.map(selectById(id), column);
    }

    // 重载selectBatchIds

    /**
     * 重载selectById
     *
     * @param ids    ID集
     * @param column 属性
     * @param <M>    映射结果类型
     * @return 映射结果集
     */
    default <M> List<M> selectBatchIds(Collection<? extends Serializable> ids, SFunction<T, M> column) {
        return CollUtil.map(selectBatchIds(ids), column);
    }

    // 重载selectOne

    /**
     * 重载selectOne
     *
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @return 映射结果
     */
    default T selectOne(Consumer<QueryWrapper<T>> queryConsumer) {
        return selectOne(new QueryWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer));
    }

    /**
     * 重载selectOne
     *
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @param column        属性
     * @param <M>           映射结果类型
     * @return 映射结果
     */
    default <M> M selectOne(Consumer<QueryWrapper<T>> queryConsumer, SFunction<T, M> column) {
        return selectOne(new QueryWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer), column);
    }

    /**
     * 重载selectOne
     *
     * @param queryWrapper 查询条件
     * @param column       属性
     * @param <M>          映射结果类型
     * @return 映射结果
     */
    default <M> M selectOne(Wrapper<T> queryWrapper, SFunction<T, M> column) {
        if (queryWrapper instanceof QueryWrapper) {
            ((QueryWrapper<T>) queryWrapper).select(column);
        }
        return ObjectUtil.map(selectOne(queryWrapper), column);
    }

    // 重载selectCount

    // 重载selectList

    /**
     * 重载selectList
     *
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @return 映射结果
     */
    default List<T> selectList(Consumer<QueryWrapper<T>> queryConsumer) {
        return selectList(query().func(queryConsumer));
    }

    /**
     * 重载selectList
     *
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @param column        属性
     * @param <M>           映射结果类型
     * @return 映射结果集
     */
    default <M> List<M> selectList(Consumer<QueryWrapper<T>> queryConsumer, SFunction<T, M> column) {
        return selectList(new QueryWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer), column);
    }

    /**
     * 重载selectList
     *
     * @param queryWrapper 查询条件
     * @param column       属性
     * @param <M>          映射结果类型
     * @return 映射结果集
     */
    default <M> List<M> selectList(Wrapper<T> queryWrapper, SFunction<T, M> column) {
        if (queryWrapper instanceof QueryWrapper) {
            ((QueryWrapper<T>) queryWrapper).select(column);
        }
        return CollUtil.map(selectList(queryWrapper), column);
    }

    // 重载selectPage

    /**
     * 重载selectPage
     *
     * @param page          分页对象
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @return 分页结果集
     */
    default <E extends IPage<T>> E selectPage(E page, Consumer<QueryWrapper<T>> queryConsumer) {
        return selectPage(page, new QueryWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer));
    }

    // 重载selectCount

    /**
     * 统计
     *
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @return 统计
     */
    default Long selectCount(Consumer<QueryWrapper<T>> queryConsumer) {
        return selectCount(new QueryWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer));
    }

    // 重载update

    /**
     * 重载update
     *
     * @param entity        更新目标
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @return 更新数量
     */
    default int update(T entity, Consumer<UpdateWrapper<T>> queryConsumer) {
        return update(entity, new UpdateWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer));
    }

    /**
     * 重载update
     *
     * @param updateConsumer {@link UpdateWrapper}更新条件
     * @return 更新数量
     */
    default int update(Consumer<UpdateWrapper<T>> updateConsumer) {
        return update(new UpdateWrapper<>(MapperUtil.extractModelClass(this)).func(updateConsumer));
    }

    /**
     * 重载update
     *
     * @param updateWrapper 更新包装类
     * @return 更新数量
     */
    default int update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    // 重载delete

    default int delete(Consumer<QueryWrapper<T>> queryConsumer) {
        return delete(new QueryWrapper<>(MapperUtil.extractModelClass(this)).func(queryConsumer));
    }

    // 新增exist

    /**
     * 查询结果是否存在
     *
     * @param queryConsumer {@link QueryWrapper}查询条件
     * @return 查询结果
     */
    default boolean exist(Consumer<QueryWrapper<T>> queryConsumer) {
        return CollUtil.isNotEmpty(selectList(queryConsumer));
    }

    /**
     * 查询结果是否存在
     *
     * @param queryWrapper 查询条件
     * @return 查询结果
     */
    default boolean exist(Wrapper<T> queryWrapper) {
        return CollUtil.isNotEmpty(selectList(queryWrapper));
    }

    // 新增selectAll

    /**
     * 查询全部
     * <p>
     * 数据量过大时慎用
     *
     * @return 结果集
     */
    default List<T> selectAll() {
        return selectList((Wrapper<T>) null);
    }
}
