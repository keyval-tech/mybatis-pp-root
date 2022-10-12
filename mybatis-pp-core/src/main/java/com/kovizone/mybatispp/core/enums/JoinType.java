package com.kovizone.mybatispp.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JoinType
 *
 * @author KV
 * @since 2022/10/12
 */
@Getter
@AllArgsConstructor
public enum JoinType {

    /**
     * JOIN类型
     */
    LEFT_JOIN("LEFT JOIN %s ON %s"),
    RIGHT_JOIN("RIGHT JOIN %s ON %s"),
    INNER_JOIN("INNER JOIN %s ON %s"),
    ;

    private String sqlTemplate;

    public String format(String joinTableName, String[] joinTableOn) {
        return String.format(sqlTemplate, joinTableName, String.join(" AND ", joinTableOn));
    }

}
