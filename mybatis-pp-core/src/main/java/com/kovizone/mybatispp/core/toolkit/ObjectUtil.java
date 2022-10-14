package com.kovizone.mybatispp.core.toolkit;

import java.util.function.Function;

/**
 * ObjectUtil
 *
 * @author KV
 * @since 2022/09/28
 */
public class ObjectUtil {

    /**
     * 映射
     *
     * @param entity 实体
     * @param mapper 映射逻辑
     * @param <T>    实体类
     * @param <R>    映射结果类型
     * @return 映射结果
     */
    public static <T, R> R map(T entity, Function<T, R> mapper) {
        try {
            return entity == null ? null : mapper.apply(entity);
        } catch (Exception e) {
            return null;
        }
    }

}
