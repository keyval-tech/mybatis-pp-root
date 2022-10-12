package com.kovizone.mybatispp.core.conditions;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * OnNode
 *
 * @author KV
 * @since 2022/10/11
 */
public class OnSql<T1, T2> {

    @Getter
    private final List<On> onList = new ArrayList<>();

    public OnSql<T1, T2> eqColumn(String column1, String column2) {
        onList.add(new StringColumn(column1, column2));
        return this;
    }

    public OnSql<T1, T2> eqColumn(SFunction<T1, ?> column1, SFunction<T2, ?> column2) {
        onList.add(new LambdaColumn<T1, T2>(column1, column2));
        return this;
    }

    public OnSql<T1, T2> apply(String applySql) {
        onList.add(new Apply(applySql));
        return this;
    }

    public interface On {
    }

    @Getter
    public static class StringColumn implements On {

        private final String column1;

        private final String column2;

        private StringColumn(String column1, String column2) {
            super();
            this.column1 = column1;
            this.column2 = column2;
        }
    }

    @Getter
    public static class LambdaColumn<T1, T2> implements On {

        private final SFunction<T1, ?> column1;

        private final SFunction<T2, ?> column2;

        private LambdaColumn(SFunction<T1, ?> column1, SFunction<T2, ?> column2) {
            super();
            this.column1 = column1;
            this.column2 = column2;
        }
    }

    @Getter
    public static class Apply implements On {

        private final String applySql;

        private Apply(String applySql) {
            super();
            this.applySql = applySql;
        }

    }

}
