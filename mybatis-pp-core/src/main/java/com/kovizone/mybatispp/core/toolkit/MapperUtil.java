package com.kovizone.mybatispp.core.toolkit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * MapperUtil
 *
 * @author KV
 * @since 2022/09/28
 */
public class MapperUtil {

    /**
     * 根据{@link BaseMapper}实例返回对应实体类
     *
     * @param baseMapper {@link BaseMapper}实例
     * @param <T>        实体类型
     * @param <M>        {@link BaseMapper}
     * @return 实体类
     */
    @SuppressWarnings("unchecked")
    public static <T, M extends BaseMapper<T>> Class<T> extractModelClass(M baseMapper) {
        try {
            Type[] types = ((Class<?>) baseMapper.getClass().getGenericInterfaces()[0]).getGenericInterfaces();
            if (types.length > 0) {
                if (types[0] instanceof ParameterizedType) {
                    Type[] generics = ((ParameterizedType) types[0]).getActualTypeArguments();
                    for (Type generic : generics) {
                        if (generic != null
                                && !(generic instanceof TypeVariable)
                                && !(generic instanceof WildcardType)) {
                            return (Class<T>) generic;
                        }
                    }
                }
            }
        } catch (Exception ignore) {
        }
        return null;
    }
}
