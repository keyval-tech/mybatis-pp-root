package com.kovizone.mybatispp.core.conditions;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendCompare;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendFunc;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendJoin;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendNested;
import com.kovizone.mybatispp.core.toolkit.ArrayUtil;
import lombok.Getter;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.enums.SqlKeyword.ORDER_BY;
import static java.util.stream.Collectors.joining;

/**
 * AbstractWrapperExt
 *
 * @author KV
 * @see com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper
 * @since 2022/09/28
 */
public abstract class AbstractExtendWrapper<T, Children extends AbstractExtendWrapper<T, Children>>
        extends AbstractWrapper<T, String, Children>
        implements ExtendCompare<T, Children>, ExtendNested<Children, Children>, ExtendJoin<Children>, ExtendFunc<T, Children> {

    private Map<String, ColumnCache> columnMap = null;

    private boolean initColumnMap = false;

    @Getter
    private TableInfo tableInfo;

    /**
     * 获取字段名
     *
     * @param column 属性GETTER
     * @return 字段名
     */
    protected String columnToString(SFunction<T, ?> column) {
        return columnToString(true, column);
    }

    /**
     * 获取字段名
     *
     * @param columns 属性GETTER集
     * @return 字段名
     */
    protected String columnsToString(SFunction<T, ?>[] columns) {
        return Arrays.stream(columns).map(this::columnToString).collect(joining(StringPool.COMMA));
    }

    /**
     * 获取字段名
     *
     * @param onlyColumn 是否仅字段
     * @param column     属性GETTER
     * @return 字段名
     */
    protected String columnToString(boolean onlyColumn, SFunction<T, ?> column) {
        ColumnCache columnCache = getColumnCache(column);
        return columnToString(onlyColumn ? columnCache.getColumn() : columnCache.getColumnSelect());
    }

    /**
     * 获取字段名
     *
     * @param onlyColumn 是否仅字段
     * @param columns    属性GETTER集
     * @return 字段名
     */
    protected String columnsToString(boolean onlyColumn, SFunction<T, ?>[] columns) {
        return Arrays.stream(columns).map(e -> columnToString(onlyColumn, e)).collect(joining(StringPool.COMMA));
    }

    /**
     * 复制AbstractLambdaWrapper#getColumnCache(SFunction)代码
     *
     * @see com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper
     */
    protected ColumnCache getColumnCache(SFunction<T, ?> column) {
        LambdaMeta meta = LambdaUtils.extract(column);
        String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
        Class<?> instantiatedClass = meta.getInstantiatedClass();
        tryInitCache(instantiatedClass);
        return getColumnCache(fieldName, instantiatedClass);
    }

    private void tryInitCache(Class<?> lambdaClass) {
        if (!initColumnMap) {
            final Class<T> entityClass = getEntityClass();
            if (entityClass != null) {
                lambdaClass = entityClass;
            }
            columnMap = LambdaUtils.getColumnMap(lambdaClass);
            Assert.notNull(columnMap, "can not find lambda cache for this entity [%s]", lambdaClass.getName());
            initColumnMap = true;
        }
    }

    private ColumnCache getColumnCache(String fieldName, Class<?> lambdaClass) {
        ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
        Assert.notNull(columnCache, "can not find lambda cache for this property [%s] of entity [%s]", fieldName, lambdaClass.getName());
        return columnCache;
    }

    @Override
    public Children eq(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return eq(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children ne(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return ne(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children gt(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return gt(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children ge(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return ge(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children lt(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return lt(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children le(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return le(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children between(boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        if (condition) {
            return between(columnToString(column), val1, val2);
        }
        return typedThis;
    }

    @Override
    public Children notBetween(boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        if (condition) {
            return notBetween(columnToString(column), val1, val2);
        }
        return typedThis;
    }

    @Override
    public Children like(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return like(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children notLike(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return notLike(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children likeLeft(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return likeLeft(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children likeRight(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return likeRight(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children isNull(boolean condition, SFunction<T, ?> column) {
        if (condition) {
            return isNull(columnToString(column));
        }
        return typedThis;
    }

    @Override
    public Children isNotNull(boolean condition, SFunction<T, ?> column) {
        if (condition) {
            return isNotNull(columnToString(column));
        }
        return typedThis;
    }

    @Override
    public Children in(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        if (condition) {
            return in(columnToString(column));
        }
        return typedThis;
    }

    @Override
    public Children in(boolean condition, SFunction<T, ?> column, Object... values) {
        if (condition) {
            return in(columnToString(column), values);
        }
        return typedThis;
    }

    @Override
    public Children notIn(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        if (condition) {
            return notIn(columnToString(column), coll);
        }
        return typedThis;
    }

    @Override
    public Children notIn(boolean condition, SFunction<T, ?> column, Object... values) {
        if (condition) {
            return notIn(columnToString(column), values);
        }
        return typedThis;
    }

    @Override
    public Children inSql(boolean condition, SFunction<T, ?> column, String inValue) {
        if (condition) {
            return inSql(columnToString(column), inValue);
        }
        return typedThis;
    }

    @Override
    public Children notInSql(boolean condition, SFunction<T, ?> column, String inValue) {
        if (condition) {
            return notInSql(columnToString(column), inValue);
        }
        return typedThis;
    }

    @Override
    public Children gtSql(boolean condition, SFunction<T, ?> column, String inValue) {
        if (condition) {
            return gtSql(columnToString(column), inValue);
        }
        return typedThis;
    }

    @Override
    public Children geSql(boolean condition, SFunction<T, ?> column, String inValue) {
        if (condition) {
            return geSql(columnToString(column), inValue);
        }
        return typedThis;
    }

    @Override
    public Children ltSql(boolean condition, SFunction<T, ?> column, String inValue) {
        if (condition) {
            return ltSql(columnToString(column), inValue);
        }
        return typedThis;
    }

    @Override
    public Children leSql(boolean condition, SFunction<T, ?> column, String inValue) {
        if (condition) {
            return leSql(columnToString(column), inValue);
        }
        return typedThis;
    }

    @SafeVarargs
    @Override
    public final Children groupBy(SFunction<T, ?> column, SFunction<T, ?>... columns) {
        return groupBy(true, column, columns);
    }

    @SafeVarargs
    @Override
    public final Children groupBy(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        if (condition) {
            if (ArrayUtil.isEmpty(columns)) {
                return groupBy(true, columnToString(column));
            }
            return groupBy(true, columnToString(column), columnsToString(columns));
        }
        return typedThis;
    }

    @SafeVarargs
    @Override
    public final Children orderByAsc(SFunction<T, ?> column, SFunction<T, ?>... columns) {
        return orderByAsc(true, column, columns);
    }

    @SafeVarargs
    @Override
    public final Children orderByAsc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        return orderBy(condition, true, column, columns);
    }

    @SafeVarargs
    @Override
    public final Children orderByDesc(SFunction<T, ?> column, SFunction<T, ?>... columns) {
        return orderByDesc(true, column, columns);
    }

    @SafeVarargs
    @Override
    public final Children orderByDesc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        return orderBy(condition, false, column, columns);
    }

    @SafeVarargs
    @Override
    public final Children orderBy(boolean condition, boolean isAsc, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        if (condition) {
            if (ArrayUtil.isEmpty(columns)) {
                return orderBy(true, isAsc, columnToString(column));
            }
            return orderBy(true, isAsc, columnToString(column), columnsToString(columns));
        }
        return typedThis;
    }

    /* 新增方法 */

    @Override
    public Children isEmpty(boolean condition, SFunction<T, ?> column) {
        if (condition) {
            return isEmpty(columnToString(column));
        }
        return typedThis;
    }

    @Override
    public Children isEmpty(boolean condition, String column) {
        if (condition) {
            return nested(w -> w.isNull(column).or().eq(column, StringPool.EMPTY));
        }
        return typedThis;
    }

    @Override
    public Children notInOrIsNull(boolean condition, SFunction<T, ?> column, Object... values) {
        if (condition) {
            return notInOrIsNull(columnToString(column), values);
        }
        return typedThis;
    }

    @Override
    public Children notInOrIsNull(boolean condition, String column, Object... values) {
        if (condition) {
            return nested(w -> w.notIn(column, values).or().isNull(column));
        }
        return typedThis;
    }

    @Override
    public Children neOrIsNull(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return neOrIsNull(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children neOrIsNull(boolean condition, String column, Object val) {
        if (condition) {
            return nested(w -> w.ne(column, val).or().isNull(column));
        }
        return typedThis;
    }

    @Override
    public Children binary(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return binary(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children binary(boolean condition, String column, Object val) {
        return maybeDo(condition, () -> appendSqlSegments(() -> "BINARY", columnToSqlSegment(column), SqlKeyword.EQ, () -> formatParam(null, val)));
    }

    @Override
    public Children regexp(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return regexp(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children regexp(boolean condition, String column, Object val) {
        return maybeDo(condition, () -> appendSqlSegments(columnToSqlSegment(column), () -> "REGEXP", () -> formatParam(null, val)));
    }

    @Override
    public Children likeSql(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return likeSql(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children likeSql(boolean condition, String column, Object val) {
        return maybeDo(condition, () -> appendSqlSegments(columnToSqlSegment(column), () -> "LIKE", () -> formatParam(null, val)));
    }

    @Override
    public Children notLikeOrIsNull(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            return notLikeOrIsNull(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children notLikeOrIsNull(boolean condition, String column, Object val) {
        if (condition) {
            return nested(w -> w.notLike(column, val).or().isNull(column));
        }
        return typedThis;
    }

    @Override
    public Children orderBySql(boolean condition, String val) {
        if (condition) {
            return maybeDo(true, () -> appendSqlSegments(ORDER_BY, val::trim));
        }
        return typedThis;
    }

    @Override
    public Children orderByField(boolean condition, SFunction<T, ?> column, Object... values) {
        if (condition) {
            return orderByField(columnToString(column), values);
        }
        return typedThis;
    }

    @Override
    public Children orderByField(boolean condition, String column, Object... values) {
        if (condition) {
            return orderByAsc(String.format("FIELD(%s,%s)", column, Arrays.stream(values).map(e -> formatParam(null, e)).collect(Collectors.joining(StringPool.COMMA))));
        }
        return typedThis;
    }

    @Override
    public Children limit(boolean condition, Number offset, Number limit) {
        if (condition) {
            return last(String.format("LIMIT %s%s", (offset == null ? StringPool.EMPTY : (formatParam(null, offset) + StringPool.COMMA)), formatParam(null, limit)));
        }
        return typedThis;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Children setEntity(T entity) {
        if (entity != null && !entity.equals(super.getEntity())) {
            setEntityClass((Class<T>) entity.getClass());
        }
        super.setEntity(entity);
        return typedThis;
    }

    @Override
    public Children setEntityClass(Class<T> entityClass) {
        if (entityClass != null) {
            this.tableInfo = TableInfoHelper.getTableInfo(entityClass);
        }
        super.setEntityClass(entityClass);
        return typedThis;
    }
}
