package com.kovizone.mybatispp.annotation;

import java.lang.annotation.*;

/**
 * TableJoins
 *
 * @author KV
 * @since 2022/10/13
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableJoins {

    TableJoin[] value();

}
