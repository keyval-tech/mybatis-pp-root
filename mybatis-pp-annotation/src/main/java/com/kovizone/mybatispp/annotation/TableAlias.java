package com.kovizone.mybatispp.annotation;

import java.lang.annotation.*;

/**
 * 别名
 *
 * @author KV
 * @since 2022/10/13
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableAlias {

    String value();

}
