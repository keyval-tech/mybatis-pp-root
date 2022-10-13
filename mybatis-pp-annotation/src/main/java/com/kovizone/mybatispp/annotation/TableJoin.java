package com.kovizone.mybatispp.annotation;

import java.lang.annotation.*;

/**
 * 联表配置
 *
 * @author KV
 * @since 2022/10/13
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableJoin {

    /**
     * 联查表实体类
     *
     * @return 联查表实体类
     */
    Class<?> join();

    /**
     * 联查条件
     *
     * @return 联查条件
     */
    String[] on();

}
