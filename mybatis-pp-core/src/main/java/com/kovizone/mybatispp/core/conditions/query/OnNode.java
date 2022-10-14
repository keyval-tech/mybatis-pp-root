package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OnNode
 *
 * @author KV
 * @since 2022/10/14
 */
public interface OnNode<LeftTable, RightTable> {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    class StringColumn<LeftTable, RightTable> implements OnNode<LeftTable, RightTable> {

        private final String leftTableColumn;

        private final String rightTableColumn;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    class LambdaColumn<LeftTable, RightTable> implements OnNode<LeftTable, RightTable> {

        private final SFunction<LeftTable, ?> leftTableColumn;

        private final SFunction<RightTable, ?> rightTableColumn;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    class Apply<LeftTable, RightTable> implements OnNode<LeftTable, RightTable> {

        public final String applySql;
    }
}
