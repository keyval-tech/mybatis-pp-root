package com.kovizone.mybatispp.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JOIN类型
 *
 * @author KV
 * @since 2022/10/12
 */
@Getter
public enum JoinType {

    /**
     * 左连接
     */
    LEFT,
    /**
     * 右连接
     */
    RIGHT,
    /**
     * 内连接
     */
    INNER,
    /**
     * 全连接
     * <p>
     * 不支持mysql
     */
    FULL,
    ;
}