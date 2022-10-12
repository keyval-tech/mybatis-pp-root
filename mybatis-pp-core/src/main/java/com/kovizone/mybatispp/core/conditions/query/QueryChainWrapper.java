package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.OnSql;
import com.kovizone.mybatispp.core.enums.JoinType;
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import com.kovizone.mybatispp.core.toolkit.ArrayUtil;
import com.kovizone.mybatispp.core.toolkit.MapperUtil;
import com.kovizone.mybatispp.core.toolkit.StrUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * QueryChainWrapper2
 *
 * @author KV
 * @see QueryWrapper
 * @since 2022/10/11
 */
@SuppressWarnings("unchecked")
public class QueryChainWrapper<T> extends AbstractQueryExtendWrapper<T, QueryChainWrapper<T>>
        implements ChainQuery<T, QueryChainWrapper<T>>, ChainJoin<T, QueryChainWrapper<T>> {

    @Getter
    private final BaseMapper<T> baseMapper;

    private QueryChainWrapper() {
        throw new IllegalArgumentException();
    }

    private QueryChainWrapper(T entity) {
        throw new IllegalArgumentException();
    }

    private QueryChainWrapper(Class<T> entityClass) {
        throw new IllegalArgumentException();
    }

    private QueryChainWrapper(T entity, String... columns) {
        throw new IllegalArgumentException();
    }

    public QueryChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        joinWrapperCache.put(baseMapper, this);
        setEntityClass(MapperUtil.extractModelClass(baseMapper));
    }

    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private QueryChainWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                              Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                              SharedString lastSql, SharedString sqlComment, SharedString sqlFirst,
                              BaseMapper<T> baseMapper) {
        super(entity, entityClass, paramNameSeq, paramNameValuePairs, mergeSegments, paramAlias, lastSql, sqlComment, sqlFirst);
        this.baseMapper = baseMapper;
    }

    @Override
    public Wrapper<T> getWrapper() {
        return this;
    }

    @Override
    public <R> QueryChainWrapper<T> inWrapper(boolean condition, String column, ChainQuery<R, ?> queryWrapper) {
        if (condition) {
            return in(column, queryWrapper.getBaseMapper().selectObjs(queryWrapper.getWrapper()));
        }
        return this;
    }

    @Override
    public <R> QueryChainWrapper<T> inWrapper(boolean condition, SFunction<T, ?> column, ChainQuery<R, ?> queryWrapper) {
        return inWrapper(condition, columnToString(column), queryWrapper);
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected QueryChainWrapper<T> instance() {
        return new QueryChainWrapper<T>(getEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs, new MergeSegments(),
                paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString(), baseMapper);
    }

    @Override
    public <T2> QueryChainWrapper<T> join(boolean condition, JoinType joinType, BaseMapper<T2> mapper2, Consumer<OnSql<T, T2>> onSqlConsumer) {
        if (condition && mapper2 != null && onSqlConsumer != null) {
            QueryChainWrapper<T2> t2QueryChainWrapper = getWrapper(mapper2);
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
                return join(joinType, mapper2, onSqlArr);
            }
        }
        return this;
    }

    @Override
    public <T2> QueryChainWrapper<T> join(boolean condition, JoinType joinType, BaseMapper<T2> mapper2, String... onSqlArr) {
        if (condition && mapper2 != null && ArrayUtil.isNotEmpty(onSqlArr)) {
            if (joinType == null) {
                joinType = JoinType.LEFT_JOIN;
            }
            QueryChainWrapper<T2> t2QueryChainWrapper = getWrapper(mapper2);
            TableInfo table2Info = t2QueryChainWrapper.getTableInfo();
            if (table2Info != null) {
                // LEFT JOIN %s ON %s
                appendSqlFrom(joinType.format(table2Info.getTableName(), onSqlArr));
            }
        }
        return this;
    }

    @Override
    public <T2> QueryChainWrapper<T> func(boolean condition, BaseMapper<T2> mapper2, Consumer<QueryChainWrapper<T2>> whereConsumer) {
        if (condition && mapper2 != null && whereConsumer != null) {
            QueryChainWrapper<T2> t2QueryChainWrapper = getWrapper(mapper2);
            whereConsumer.accept(t2QueryChainWrapper);
        }
        return this;
    }

    private final Map<BaseMapper<?>, QueryChainWrapper<?>> joinWrapperCache = new HashMap<>();

    protected final <W> QueryChainWrapper<W> getWrapper(BaseMapper<W> mapper) {
        QueryChainWrapper<W> queryChainWrapper = (QueryChainWrapper<W>) joinWrapperCache.get(mapper);
        if (queryChainWrapper == null) {
            this.fromJoin = true;
            queryChainWrapper = mapper.queryWrapper();
            queryChainWrapper.fromJoin = true;
            // WHERE片段都接入主表的WHERE片段集
            queryChainWrapper.expression = this.expression;
            queryChainWrapper.paramNameSeq = this.paramNameSeq;
            queryChainWrapper.paramNameValuePairs = this.paramNameValuePairs;

            joinWrapperCache.put(mapper, queryChainWrapper);
        }
        return queryChainWrapper;
    }

    @Override
    public String getSqlSelect() {
        TableInfo tableInfo = getTableInfo();
        String sqlSelect = super.getSqlSelect();
        if (StrUtil.isEmpty(sqlSelect) && tableInfo != null) {
            return Arrays.stream(tableInfo.getAllSqlSelect().split(StringPool.COMMA))
                    .map(e -> (fromJoin ? (tableInfo.getTableName() + StringPool.DOT) : StringPool.EMPTY) + e)
                    .collect(Collectors.joining(StringPool.COMMA));
        }
        return sqlSelect;
    }

    @Override
    protected String columnToString(String column) {
        if (fromJoin && !column.contains(StringPool.DOT)) {
            TableInfo tableInfo = getTableInfo();
            if (tableInfo != null) {
                return tableInfo.getTableName() + StringPool.DOT + super.columnToString(column);
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
