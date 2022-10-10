package com.kovizone.mybatispp.core.conditions.update;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * UpdateWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper
 * @since 2022/09/28
 */
public class UpdateWrapper<T> extends AbstractExtendWrapper<T, UpdateWrapper<T>> implements ExtendUpdate<T, UpdateWrapper<T>> {

    /**
     * SQL 更新字段内容，例如：name='1', age=2
     */
    private final List<String> sqlSet;

    /**
     * 不建议直接 new 该实例，使用 MybatisUtil.update()
     */
    public UpdateWrapper() {
        // 如果无参构造函数，请注意实体 NULL 情况 SET 必须有否则 SQL 异常
        this((T) null);
    }

    /**
     * 不建议直接 new 该实例，使用 MybatisUtil.update(entity)
     */
    public UpdateWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList<>();
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.update(entity)
     */
    public UpdateWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
        this.sqlSet = new ArrayList<>();
    }

    UpdateWrapper(T entity, Class<T> entityClass, List<String> sqlSet, AtomicInteger paramNameSeq,
                  Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments,
                  SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.sqlSet = sqlSet;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    @Override
    public UpdateWrapper<T> set(boolean condition, String column, Object val, String mapping) {
        return maybeDo(condition, () -> {
            String sql = formatParam(mapping, val);
            sqlSet.add(columnToString(column) + Constants.EQUALS + sql);
        });
    }

    @Override
    public UpdateWrapper<T> setSql(boolean condition, String sql) {
        if (condition && StringUtils.isNotBlank(sql)) {
            sqlSet.add(sql);
        }
        return typedThis;
    }

    @Override
    public String getSqlSet() {
        if (CollectionUtils.isEmpty(sqlSet)) {
            return null;
        }
        return String.join(StringPool.COMMA, sqlSet);
    }

    @Override
    protected UpdateWrapper<T> instance() {
        return new UpdateWrapper<>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs,
                new MergeSegments(), SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    @Override
    public void clear() {
        super.clear();
        sqlSet.clear();
    }

    /* 新增方法 */

    @Override
    public UpdateWrapper<T> concat(boolean condition, SFunction<T, ?> column, Object... values) {
        if (condition) {
            return concat(columnToString(column), values);
        }
        return typedThis;
    }

    @Override
    public UpdateWrapper<T> concat(boolean condition, String column, Object... values) {
        if (condition) {
            sqlSet.add(String.format("%s=CONCAT_WS('',%s,%s)", column, column,
                    Arrays.stream(values).filter(Objects::nonNull)
                            .map(e -> formatParam(null, e)).collect(Collectors.joining(StringPool.COMMA))));
        }
        return typedThis;
    }

    @Override
    public UpdateWrapper<T> incr(boolean condition, SFunction<T, ?> column, Object increment) {
        if (condition) {
            return incr(columnToString(column), increment);
        }
        return typedThis;
    }

    @Override
    public UpdateWrapper<T> incr(boolean condition, String column, Object increment) {
        if (condition) {
            sqlSet.add(String.format("%s=(%s+%s)", column, column, formatParam(null, increment)));
        }
        return typedThis;
    }

    @Override
    public UpdateWrapper<T> cas(boolean condition, SFunction<T, ?> column, Object version) {
        if (condition) {
            return cas(columnToString(column), version);
        }
        return typedThis;
    }

    @Override
    public UpdateWrapper<T> cas(boolean condition, String column, Object version) {
        if (condition) {
            if (column == null) {
                // 读取标记了@Version的字段
                Class<T> entityClass = getEntityClass();
                if (entityClass != null) {
                    TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
                    if (tableInfo != null) {
                        TableFieldInfo versionFieldInfo = tableInfo.getVersionFieldInfo();
                        if (versionFieldInfo != null) {
                            column = versionFieldInfo.getColumn();
                        }
                    }
                }
            }
            return eq(column, version).incr(column, 1);
        }
        return typedThis;
    }

    @Override
    public UpdateWrapper<T> set(boolean condition, SFunction<T, ?> column, Object val, String mapping) {
        if (condition) {
            return set(columnToString(column), val, mapping);
        }
        return typedThis;
    }
}
