package com.kovizone.mybatispp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WrapperModelProperty
 * <p>
 * 例如：“id = 1”中，“id”是column，“=”是operation，“1”是arg
 *
 * @author KV
 * @since 2022/09/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface WrapperModelProperty {

    /**
     * 关联字段
     * <p>
     * 若此处设置了大于一个字段，则生成OR关系条件，并嵌套
     * <p>
     * 例如： @WrapperModelProperty(column={"col_1", "col_2"})
     * <p>
     * 转换为 "xxx AND (col_1 = value OR col_2 = value) AND ..."
     *
     * @return 字段
     */
    String[] column() default {};

    /**
     * 执行条件表达式
     * <p>
     * {0}为其标注的属性值的占位符
     * <p>
     * 基于SPEL实现
     *
     * @return 条件表达式
     */
    String condition() default "{0} != null";

    /**
     * 操作
     *
     * @return 操作
     */
    Operation operation() default Operation.EQ;

    /**
     * 操作参数
     * <p>
     * 为空时，标注的属性值作为操作参数
     * <p>
     * 非空时，作为操作参数
     *
     * @return 操作结果
     */
    String arg() default "";

}