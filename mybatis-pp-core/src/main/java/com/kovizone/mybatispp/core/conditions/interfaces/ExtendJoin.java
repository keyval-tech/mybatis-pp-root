package com.kovizone.mybatispp.core.conditions.interfaces;

import com.baomidou.mybatisplus.core.conditions.interfaces.Join;

/**
 * LambdaJoin
 * <p>
 * 基于{@link Join}扩展
 *
 * @author KV
 * @see Join
 * @since 2022/09/14
 */
public interface ExtendJoin<Children> extends Join<Children> {

    /* 新方法 */

    /**
     * ignore
     */
    default Children limit(Number limit) {
        return limit(true, null, limit);
    }

    /**
     * ignore
     */
    default Children limit(boolean condition, Number limit) {
        return limit(condition, null, limit);
    }

    /**
     * ignore
     */
    default Children limit(Number offset, Number limit) {
        return limit(true, offset, limit);
    }

    /**
     * 限制查询数量
     * <p>
     * LIMIT offset,limit
     * <p>
     * 仅适用于MYSQL
     *
     * @param condition 执行条件
     * @param offset    偏移量
     * @param limit     限制数
     * @return children
     */
    Children limit(boolean condition, Number offset, Number limit);
}
