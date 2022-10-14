package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述主表和连表的ON关系
 *
 * @author KV
 * @since 2022/10/11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class On<LeftTable, RightTable> {

    @Getter
    private final List<OnNode<LeftTable, RightTable>> onNodeList = new ArrayList<>();

    /**
     * 字段匹配构建
     *
     * @param column1 左表字段
     * @param column2 右表字段
     */
    public static <LeftTable, RightTable> On<LeftTable, RightTable> eq(String column1, String column2) {
        return new On<LeftTable, RightTable>().andEq(column1, column2);
    }

    /**
     * 字段匹配构建
     *
     * @param column1 左表字段
     * @param column2 右表字段
     */
    public static <LeftTable, RightTable> On<LeftTable, RightTable> eq(SFunction<LeftTable, ?> column1, SFunction<RightTable, ?> column2) {
        return new On<LeftTable, RightTable>().andEq(column1, column2);
    }

    /**
     * 自定义语句构建
     */
    public static <LeftTable, RightTable> On<LeftTable, RightTable> apply(String applySql) {
        return new On<LeftTable, RightTable>().and(applySql);
    }

    /**
     * 字段匹配
     *
     * @param column1 左表字段
     * @param column2 右表字段
     * @return OnHelper
     */
    public On<LeftTable, RightTable> andEq(String column1, String column2) {
        onNodeList.add(new OnNode.StringColumn<>(column1, column2));
        return this;
    }

    /**
     * 字段匹配
     *
     * @param column1 左表字段
     * @param column2 右表字段
     * @return OnHelper
     */
    public On<LeftTable, RightTable> andEq(SFunction<LeftTable, ?> column1, SFunction<RightTable, ?> column2) {
        onNodeList.add(new OnNode.LambdaColumn<>(column1, column2));
        return this;
    }

    /**
     * 自定义语句
     *
     * @param applySql 自定义语句
     * @return OnHelper
     */
    public On<LeftTable, RightTable> and(String applySql) {
        onNodeList.add(new OnNode.Apply<>(applySql));
        return this;
    }
}