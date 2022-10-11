package com.kovizone.mybatispp.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * QueryWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
 * @see com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
 * @since 2022/09/28
 */
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
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
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
}