package com.kovizone.mybatispp.annotation;

import java.lang.annotation.*;

/**
 * WrapperModel
 *
 * @author KV
 * @since 2022/09/30
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WrapperModel {

    /**
     * 设置实体类型，用于自动匹配字段名
     *
     * @return 实体类型
     */
    Class<?> model() default Void.class;

    /**
     * 自定义SQL
     *
     * @return 自定义SQL
     */
    String[] apply() default {};

    /**
     * 自定义排序
     *
     * @return 排序描述
     */
    String[] orderBy() default {};

    /**
     * 排序描述
     *
     * @return 排序描述（滞后的）
     */
    String[] lastOrderBy() default {};

}
