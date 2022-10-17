package com.kovizone.mybatispp.annotation;

/**
 * 操作枚举（非标准SQL操作符）
 *
 * @author KV
 * @since 2021/08/30 11:30
 */
public enum Operation {

    /**
     * 等于
     */
    EQ,
    /**
     * 不等于
     */
    NE,
    /**
     * 不等于或为空
     */
    NE_OR_IS_NULL,
    /**
     * 大于或等于
     */
    GE,
    /**
     * 大于
     */
    GT,
    /**
     * 小于或等于
     */
    LE,
    /**
     * 小于
     */
    LT,
    /**
     * 模糊查询（匹配左右）
     */
    LIKE,
    /**
     * 模糊查询（不会自动加上%）
     */
    LIKE_SQL,
    /**
     * 模糊查询（匹配左）
     */
    LIKE_LEFT,
    /**
     * 模糊查询（匹配右）
     */
    LIKE_RIGHT,
    /**
     * 模糊查询的非集
     */
    NOT_LIKE,
    /**
     * 模糊查询的非集或为空
     */
    NOT_LIKE_OR_IS_NULL,
    /**
     * 包含，支持Collection类型属性或数组
     */
    IN,
    /**
     * 不包含，支持Collection类型属性或数组
     */
    NOT_IN,
    /**
     * 不包含或为空，支持Collection类型属性或数组
     */
    NOT_IN_OR_IS_NULL,
    /**
     * 为空
     */
    IS_NULL,
    /**
     * 不为空
     */
    IS_NOT_NULL,
    /**
     * 二进制匹配
     */
    BINARY,
    /**
     * 正则表达式匹配
     */
    REGEXP,
    /**
     * 排序
     */
    ORDER_BY(false),
    /**
     * 排序（正序）
     */
    ORDER_BY_ASC(false),
    /**
     * 排序（倒序）
     */
    ORDER_BY_DESC(false),
    /**
     * 赋值
     */
    SET(false),
    /**
     * 拼接
     */
    CONCAT(false),
    /**
     * 递增
     */
    INCR(false),
    /**
     * 乐观锁
     */
    CAS(false),
    /**
     * 选择
     */
    SELECT(false),
    /**
     * 去重选择
     */
    DISTINCT(false),
    ;

    private final boolean supportOr;

    Operation(boolean supportOr) {
        this.supportOr = supportOr;
    }

    Operation() {
        this.supportOr = true;
    }

    public boolean isSupportOr() {
        return supportOr;
    }
}
