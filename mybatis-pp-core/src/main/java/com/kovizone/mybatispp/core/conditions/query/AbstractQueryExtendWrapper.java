package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.annotation.TableJoin;
import com.kovizone.mybatispp.annotation.TableJoins;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;
import com.kovizone.mybatispp.core.conditions.OnSql;
import com.kovizone.mybatispp.core.enums.JoinType;
import com.kovizone.mybatispp.core.toolkit.ArrayUtil;
import com.kovizone.mybatispp.core.toolkit.ReflectUtil;
import com.kovizone.mybatispp.core.toolkit.StrUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * QueryWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
 * @see com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
 * @since 2022/09/28
 */
@SuppressWarnings("unchecked")
public abstract class AbstractQueryExtendWrapper<T, Children extends AbstractQueryExtendWrapper<T, Children>>
        extends AbstractExtendWrapper<T, Children>
        implements ExtendQuery<T, Children> {

    /**
     * 查询字段
     */
    private final SharedString sqlSelect = new SharedString();

    public AbstractQueryExtendWrapper() {
        this((T) null);
    }

    public AbstractQueryExtendWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    public AbstractQueryExtendWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
    }

    public AbstractQueryExtendWrapper(T entity, String... columns) {
        super.setEntity(entity);
        super.initNeed();
        this.select(columns);
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    protected AbstractQueryExtendWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                                         Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                                         SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    @Override
    protected String columnSqlInjectFilter(String column) {
        return StringUtils.sqlInjectionReplaceBlank(column);
    }

    @Override
    public Children select(String... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(StringPool.COMMA, columnsToString(columns)));
        }
        return typedThis;
    }

    @Override
    public Children select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        super.setEntityClass(entityClass);
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(getEntityClass()).chooseSelect(predicate));
        return typedThis;
    }

    @Override
    public void clear() {
        super.clear();
        sqlSelect.toNull();
    }

    @SafeVarargs
    @Override
    public final Children select(SFunction<T, ?>... columns) {
        return select(columnsToString(false, columns));
    }

    @SafeVarargs
    @Override
    public final Children distinct(SFunction<T, ?>... columns) {
        return distinct(columnsToString(false, columns));
    }

    @Override
    public Children appendSelect(String... columns) {
        sqlSelect.setStringValue(getSqlSelect().concat(StringPool.COMMA).concat(columnsToString(columns)));
        return typedThis;
    }

    @SafeVarargs
    @Override
    public final Children appendSelect(SFunction<T, ?>... columns) {
        return appendSelect(columnsToString(columns));
    }

    @Override
    public <T2> Children join(boolean condition, JoinType joinType, Class<T2> model2, Consumer<OnSql<T, T2>> onSqlConsumer) {
        if (condition && model2 != null) {
            if (onSqlConsumer != null) {
                AbstractQueryExtendWrapper<T2, ?> t2QueryChainWrapper = getQueryWrapper(model2);
                OnSql<T, T2> onSql = new OnSql<>();
                onSqlConsumer.accept(onSql);
                List<OnSql.On> onList = onSql.getOnList();
                if (!onList.isEmpty()) {
                    String[] onSqlArr = new String[onList.size()];
                    for (int i = 0; i < onList.size(); i++) {
                        OnSql.On on = onList.get(i);
                        if (on instanceof OnSql.StringColumn) {
                            OnSql.StringColumn onStringColumn = ((OnSql.StringColumn) on);
                            onSqlArr[i] = String.format("%s=%s", columnToString(onStringColumn.getColumn1()), t2QueryChainWrapper.columnToString(onStringColumn.getColumn2()));
                        }
                        if (on instanceof OnSql.LambdaColumn) {
                            OnSql.LambdaColumn<T, T2> onStringColumn = ((OnSql.LambdaColumn<T, T2>) on);
                            onSqlArr[i] = String.format("%s=%s", columnToString(onStringColumn.getColumn1()), t2QueryChainWrapper.columnToString(onStringColumn.getColumn2()));
                        }
                        if (on instanceof OnSql.Apply) {
                            OnSql.Apply onApply = ((OnSql.Apply) on);
                            onSqlArr[i] = onApply.getApplySql();
                        }
                    }
                    return join(joinType, model2, onSqlArr);
                }
            } else {
                return join(joinType, model2, (String[]) null);
            }
        }
        return typedThis;
    }

    protected TableJoin[] getTableJoins() {
        Class<T> entityClass = getEntityClass();
        if (entityClass != null) {
            TableJoin tableJoin = ReflectUtil.getAnnotation(entityClass, TableJoin.class);
            if (tableJoin != null) {
                return new TableJoin[]{tableJoin};
            }
            TableJoins tableJoins = ReflectUtil.getAnnotation(entityClass, TableJoins.class);
            if (tableJoins != null) {
                return tableJoins.value();
            }
        }
        return new TableJoin[]{};
    }

    @Override
    public <T2> Children join(boolean condition, JoinType joinType, Class<T2> model2, String... onSqlArr) {
        if (condition && model2 != null) {
            if (ArrayUtil.isEmpty(onSqlArr)) {
                TableJoin[] tableJoins = getTableJoins();
                for (TableJoin tableJoin : tableJoins) {
                    if (tableJoin.join().equals(model2)) {
                        onSqlArr = tableJoin.on();
                        break;
                    }
                }
            }
            if (ArrayUtil.isNotEmpty(onSqlArr)) {
                if (joinType == null) {
                    joinType = JoinType.LEFT_JOIN;
                }
                AbstractQueryExtendWrapper<T2, ?> t2QueryChainWrapper = getQueryWrapper(model2);
                TableInfo table2Info = t2QueryChainWrapper.getTableInfo();
                if (table2Info != null) {
                    // LEFT JOIN %s ON %s
                    appendSqlFrom(joinType.format(table2Info.getTableName(), onSqlArr));
                }
            }
        }
        return typedThis;
    }

    /**
     * 切换选择包装类
     *
     * @param condition     执行条件
     * @param model2        联表的实体
     * @param whereConsumer 描述联查WHERE条件
     * @param <T2>          联表实体类
     * @return children
     */
    @Override
    public <T2> Children func(boolean condition, Class<T2> model2, Consumer<QueryWrapper<T2>> whereConsumer) {
        if (condition && model2 != null && whereConsumer != null) {
            QueryWrapper<T2> t2QueryWrapper = getQueryWrapper(model2);
            whereConsumer.accept(t2QueryWrapper);
        }
        return typedThis;
    }

    protected final Map<Class<?>, QueryWrapper<?>> joinWrapperCache = new HashMap<>();

    protected final <M> QueryWrapper<M> getQueryWrapper(Class<M> modelClass) {
        QueryWrapper<M> queryWrapper = (QueryWrapper<M>) joinWrapperCache.get(modelClass);
        if (queryWrapper == null) {
            this.fromJoin = true;
            queryWrapper = new QueryWrapper<>(modelClass);
            queryWrapper.fromJoin = true;
            // WHERE片段都接入主表的WHERE片段集
            queryWrapper.expression = this.expression;
            queryWrapper.paramNameSeq = this.paramNameSeq;
            queryWrapper.paramNameValuePairs = this.paramNameValuePairs;

            joinWrapperCache.put(modelClass, queryWrapper);
        }
        return queryWrapper;
    }

    @Override
    public String getSqlSelect() {
        TableInfo tableInfo = getTableInfo();
        String sqlSelect = this.sqlSelect.getStringValue();
        if (tableInfo != null) {
            if (StrUtil.isEmpty(sqlSelect)) {
                sqlSelect = tableInfo.getAllSqlSelect();
            }
        }
        if (fromJoin) {
            return Arrays.stream(sqlSelect.split(StringPool.COMMA))
                    .map(this::columnToString)
                    .collect(Collectors.joining(StringPool.COMMA));
        }
        return sqlSelect;
    }

    @Override
    protected String columnToString(String column) {
        if (fromJoin
                && !column.contains(StringPool.DOT)
                && !column.contains(StringPool.LEFT_BRACKET)
                && !column.contains(StringPool.RIGHT_BRACKET)) {
            TableInfo tableInfo = getTableInfo();
            if (tableInfo != null) {
                return tableInfo.getTableName().concat(StringPool.DOT).concat(super.columnToString(column));
            }
        }
        return super.columnToString(column);
    }

    protected boolean fromJoin = false;

    private final SharedString sqlFrom = new SharedString();

    public String getSqlFrom() {
        if (StrUtil.isEmpty(sqlFrom.getStringValue())) {
            TableInfo tableInfo = getTableInfo();
            if (tableInfo != null) {
                sqlFrom.setStringValue(tableInfo.getTableName());
            }
        }
        return sqlFrom.getStringValue();
    }

    public void appendSqlFrom(String sqlFrom) {
        if (StrUtil.isNotEmpty(sqlFrom)) {
            this.sqlFrom.setStringValue(getSqlFrom().concat(StringPool.SPACE).concat(sqlFrom));
        }
    }
}