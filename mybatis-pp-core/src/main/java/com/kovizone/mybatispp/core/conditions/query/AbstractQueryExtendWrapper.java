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
import com.kovizone.mybatispp.annotation.JoinType;
import com.kovizone.mybatispp.annotation.TableAlias;
import com.kovizone.mybatispp.annotation.TableJoin;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;
import com.kovizone.mybatispp.core.toolkit.*;

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

    /**
     * FROM片段
     */
    private final SharedString sqlFrom = new SharedString();

    /**
     * 是否需要别名引用（联表时）
     */
    protected boolean needAlias = false;

    /**
     * 连接查询包装缓存
     */
    protected final Map<Class<?>, QueryWrapper<?>> joinWrapperCache = new HashMap<>();

    public AbstractQueryExtendWrapper() {
        this((T) null);
    }

    public AbstractQueryExtendWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    public AbstractQueryExtendWrapper(Class<T> entityClass) {
        this.setEntityClass(entityClass);
        super.initNeed();
    }

    public AbstractQueryExtendWrapper(T entity, String... columns) {
        super.setEntity(entity);
        super.initNeed();
        this.select(columns);
    }

    @Override
    public Children setEntityClass(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        return typedThis;
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

    /**
     * 获取连接实体的查询包装
     *
     * @param joinEntityType 连接实体类
     * @param <Join>         连接实体
     * @return 查询包装类
     */
    public <Join> QueryWrapper<Join> getJoinWrapper(Class<Join> joinEntityType) {
        this.needAlias = true;
        return (QueryWrapper<Join>) joinWrapperCache.computeIfAbsent(joinEntityType, k -> {
            QueryWrapper<Join> queryWrapper = new QueryWrapper<>((Class<Join>) k);
            queryWrapper.needAlias = true;
            // WHERE片段都接入主表的WHERE片段集
            queryWrapper.paramNameSeq = this.paramNameSeq;
            queryWrapper.paramNameValuePairs = this.paramNameValuePairs;
            queryWrapper.expression = this.expression;
            return queryWrapper;
        });
    }

    @Override
    public <Join> Children join(boolean condition, JoinType joinType, Class<Join> joinEntityType, On<T, Join> onHelper) {
        if (condition && joinEntityType != null) {
            String[] onSqlArr = null;
            if (onHelper != null) {
                List<OnNode<T, Join>> onNodeList = onHelper.getOnNodeList();
                if (!onNodeList.isEmpty()) {
                    QueryWrapper<Join> joinWrapper = getJoinWrapper(joinEntityType);

                    onSqlArr = new String[onNodeList.size()];
                    for (int i = 0; i < onNodeList.size(); i++) {
                        OnNode<T, Join> onNode = onNodeList.get(i);
                        if (onNode instanceof OnNode.StringColumn) {
                            OnNode.StringColumn<T, Join> onStringColumn = ((OnNode.StringColumn<T, Join>) onNode);
                            onSqlArr[i] = String.format("%s=%s", columnToString(onStringColumn.getLeftTableColumn()), joinWrapper.columnToString(onStringColumn.getRightTableColumn()));
                        }
                        if (onNode instanceof OnNode.LambdaColumn) {
                            OnNode.LambdaColumn<T, Join> onStringColumn = ((OnNode.LambdaColumn<T, Join>) onNode);
                            onSqlArr[i] = String.format("%s=%s", columnToString(onStringColumn.getLeftTableColumn()), joinWrapper.columnToString(onStringColumn.getRightTableColumn()));
                        }
                        if (onNode instanceof OnNode.Apply) {
                            OnNode.Apply<T, Join> onApply = ((OnNode.Apply<T, Join>) onNode);
                            onSqlArr[i] = onApply.getApplySql();
                        }
                    }
                }
            }
            return join(joinType, joinEntityType, onSqlArr);
        }
        return typedThis;
    }

    @Override
    public <Join> Children join(boolean condition, JoinType joinType, Class<Join> joinEntityType, String... onSqlArr) {
        if (condition && joinEntityType != null) {
            TableJoin tableJoin = WrapperUtil.getTableJoin(getEntityClass(), joinEntityType);
            if (ArrayUtil.isEmpty(onSqlArr)) {
                // ON条件是相通的
                onSqlArr = (tableJoin != null && ArrayUtil.isNotEmpty(tableJoin.on())) ?
                        tableJoin.on() :
                        Mapper.of(WrapperUtil.getTableJoin(joinEntityType, getEntityClass())).map(TableJoin::on).get();
            }
            if (joinType == null) {
                // 使用默认连接类型
                joinType = tableJoin != null ? tableJoin.defaultType() : JoinType.LEFT;
            }
            TableInfo joinTableInfo = getJoinWrapper(joinEntityType).getTableInfo();
            if (joinTableInfo != null) {
                String alias = ObjectUtil.map(ReflectUtil.getAnnotation(joinEntityType, TableAlias.class), TableAlias::value);
                // %s JOIN %s AS %s ON %s
                appendSqlFrom(joinType, joinTableInfo.getTableName(), alias, onSqlArr);
            }
        }
        return typedThis;
    }

    /**
     * 切换选择包装类
     *
     * @param condition      执行条件
     * @param joinEntityType 联表的实体
     * @param joinConsumer   描述联查WHERE条件
     * @param <Join>         联表实体类
     * @return children
     */
    @Override
    public <Join> Children func(boolean condition, Class<Join> joinEntityType, Consumer<QueryWrapper<Join>> joinConsumer) {
        if (condition && joinEntityType != null && joinConsumer != null) {
            if (!joinWrapperCache.containsKey(joinEntityType) && !joinEntityType.equals(getEntityClass())) {
                join(joinEntityType);
            }
            joinConsumer.accept(getJoinWrapper(joinEntityType));
        }
        return typedThis;
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
        if (needAlias) {
            return Arrays.stream(sqlSelect.split(StringPool.COMMA))
                    .map(this::columnToString)
                    .collect(Collectors.joining(StringPool.COMMA));
        }
        return sqlSelect;
    }

    @Override
    protected String columnToString(String column) {
        if (needAlias && !StrUtil.containsAny(column, StringPool.DOT, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET)) {
            String tableAlias = WrapperUtil.getTableAlias(getEntityClass());
            if (tableAlias != null) {
                return tableAlias.concat(StringPool.DOT).concat(super.columnToString(column));
            }
        }
        return super.columnToString(column);
    }

    /**
     * 获取FROM片段
     *
     * @return 获取FROM片段
     */
    public String getSqlFrom() {
        if (StrUtil.isEmpty(sqlFrom.getStringValue())) {
            TableInfo tableInfo = getTableInfo();
            if (tableInfo != null) {
                String tableName = tableInfo.getTableName();
                String tableAlias = WrapperUtil.getTableAlias(tableInfo.getEntityType());
                if (!tableName.equals(tableAlias)) {
                    tableName = tableName.concat(StringPool.SPACE).concat(tableAlias);
                }
                sqlFrom.setStringValue(tableName);
            }
        }
        return sqlFrom.getStringValue();
    }

    /**
     * 拼接FROM片段
     *
     * @param joinType       连接类型
     * @param joinTableName  连接表
     * @param joinTableAlias 连接表别名
     * @param joinTableOn    连接ON条件
     */
    protected void appendSqlFrom(JoinType joinType, String joinTableName, String joinTableAlias, String[] joinTableOn) {

        if (StrUtil.isEmpty(joinTableName)) {
            return;
        }
        if (joinType == null) {
            joinType = JoinType.LEFT;
        }
        if (StrUtil.isNotEmpty(joinTableAlias) && !joinTableName.equals(joinTableAlias)) {
            joinTableName = joinTableName.concat(StringPool.SPACE).concat(joinTableAlias);
        }
        String sqlFrom = getSqlFrom().concat(StringPool.SPACE).concat(joinType.toString()).concat(" JOIN ").concat(joinTableName);
        if (ArrayUtil.isNotEmpty(joinTableOn)) {
            sqlFrom = sqlFrom.concat(String.format(" ON (%s)", String.join(StringPool.COMMA, joinTableOn)));
        }
        this.sqlFrom.setStringValue(sqlFrom);
    }
}