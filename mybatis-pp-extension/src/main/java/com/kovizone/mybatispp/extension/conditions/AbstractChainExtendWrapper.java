package com.kovizone.mybatispp.extension.conditions;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendCompare;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendFunc;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendJoin;
import com.kovizone.mybatispp.core.conditions.interfaces.ExtendNested;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * AbstractChainWrapper
 *
 * @author KV
 * @see com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper
 * @see com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper
 * @see com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper
 * @see com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper
 * @see com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper
 * @since 2022/10/10
 */
@SuppressWarnings("unchecked")
public abstract class AbstractChainExtendWrapper<T, Children extends AbstractChainExtendWrapper<T, Children, Param>, Param extends AbstractExtendWrapper<T, Param>>
        extends Wrapper<T>
        implements ChainWrapper<T, Param, Children>, ExtendCompare<T, Children>, ExtendFunc<T, Children>, ExtendJoin<Children>, ExtendNested<Param, Children> {

    private final Children typeThis = (Children) this;

    @Override
    public String getSqlSegment() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSegment");
    }

    @Override
    public String getSqlFirst() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlFirst");
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    @Override
    public String getSqlSet() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSet");
    }

    @Override
    public String getSqlComment() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlComment");
    }

    @Override
    public String getTargetSql() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getTargetSql");
    }

    @Override
    public T getEntity() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getEntity");
    }

    @Override
    public MergeSegments getExpression() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getExpression");
    }

    @Override
    public String getCustomSqlSegment() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getCustomSqlSegment");
    }

    @Override
    public void clear() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "clear");
    }

    @Override
    public Children clone() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "clone");
    }

    @Override
    public Children isNull(boolean condition, String column) {
        getWrapper().isNull(condition, column);
        return typeThis;
    }

    @Override
    public Children isNotNull(boolean condition, String column) {
        getWrapper().isNotNull(condition, column);
        return typeThis;
    }

    @Override
    public Children in(boolean condition, String column, Collection<?> coll) {
        getWrapper().in(condition, column, coll);
        return typeThis;
    }

    @Override
    public Children in(boolean condition, String column, Object... values) {
        getWrapper().in(condition, column, values);
        return typeThis;
    }

    @Override
    public Children notIn(boolean condition, String column, Collection<?> coll) {
        getWrapper().notIn(condition, column, coll);
        return typeThis;
    }

    @Override
    public Children notIn(boolean condition, String column, Object... values) {
        getWrapper().notIn(condition, column, values);
        return typeThis;
    }

    @Override
    public Children inSql(boolean condition, String column, String inValue) {
        getWrapper().inSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children gtSql(boolean condition, String column, String inValue) {
        getWrapper().gtSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children geSql(boolean condition, String column, String inValue) {
        getWrapper().geSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children ltSql(boolean condition, String column, String inValue) {
        getWrapper().ltSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children leSql(boolean condition, String column, String inValue) {
        getWrapper().leSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children notInSql(boolean condition, String column, String inValue) {
        getWrapper().notInSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children groupBy(boolean condition, String column) {
        getWrapper().groupBy(condition, column);
        return typeThis;
    }

    @Override
    public Children groupBy(boolean condition, List<String> columns) {
        getWrapper().groupBy(condition, columns);
        return typeThis;
    }

    @Override
    public Children groupBy(boolean condition, String column, String... columns) {
        getWrapper().groupBy(condition, column, columns);
        return typeThis;
    }

    @Override
    public Children orderBy(boolean condition, boolean isAsc, String column) {
        getWrapper().orderBy(condition, isAsc, column);
        return typeThis;
    }

    @Override
    public Children orderBy(boolean condition, boolean isAsc, List<String> columns) {
        getWrapper().orderBy(condition, isAsc, columns);
        return typeThis;
    }

    @Override
    public Children orderBy(boolean condition, boolean isAsc, String column, String... columns) {
        getWrapper().orderBy(condition, isAsc, column, columns);
        return typeThis;
    }

    @Override
    public Children having(boolean condition, String sqlHaving, Object... params) {
        getWrapper().having(condition, sqlHaving, params);
        return typeThis;
    }

    @Override
    public Children func(boolean condition, Consumer<Children> consumer) {
        if (condition) {
            consumer.accept(typeThis);
        }
        return typeThis;
    }

    @Override
    public Children or(boolean condition) {
        getWrapper().or(condition);
        return typeThis;
    }

    @Override
    public Children apply(boolean condition, String applySql, Object... values) {
        getWrapper().apply(condition, applySql, values);
        return typeThis;
    }

    @Override
    public Children last(boolean condition, String lastSql) {
        getWrapper().last(condition, lastSql);
        return typeThis;
    }

    @Override
    public Children comment(boolean condition, String comment) {
        getWrapper().comment(condition, comment);
        return typeThis;
    }

    @Override
    public Children first(boolean condition, String firstSql) {
        getWrapper().first(condition, firstSql);
        return typeThis;
    }

    @Override
    public Children exists(boolean condition, String existsSql, Object... values) {
        getWrapper().exists(condition, existsSql, values);
        return typeThis;
    }

    @Override
    public Children notExists(boolean condition, String existsSql, Object... values) {
        getWrapper().notExists(condition, existsSql, values);
        return typeThis;
    }

    @Override
    public Children and(boolean condition, Consumer<Param> consumer) {
        getWrapper().and(condition, consumer);
        return typeThis;
    }

    @Override
    public Children or(boolean condition, Consumer<Param> consumer) {
        getWrapper().or(condition, consumer);
        return typeThis;
    }

    @Override
    public Children nested(boolean condition, Consumer<Param> consumer) {
        getWrapper().nested(condition, consumer);
        return typeThis;
    }

    @Override
    public Children not(boolean condition, Consumer<Param> consumer) {
        getWrapper().not(condition, consumer);
        return typeThis;
    }

    @Override
    public Children binary(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().binary(condition, column, val);
        return typeThis;
    }

    @Override
    public Children binary(boolean condition, String column, Object val) {
        getWrapper().binary(condition, column, val);
        return typeThis;
    }

    @Override
    public Children regexp(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().regexp(condition, column, val);
        return typeThis;
    }

    @Override
    public Children regexp(boolean condition, String column, Object val) {
        getWrapper().regexp(condition, column, val);
        return typeThis;
    }

    @Override
    public Children neOrIsNull(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().neOrIsNull(condition, column, val);
        return typeThis;
    }

    @Override
    public Children neOrIsNull(boolean condition, String column, Object val) {
        getWrapper().neOrIsNull(condition, column, val);
        return typeThis;
    }

    @Override
    public Children likeSql(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().likeSql(condition, column, val);
        return typeThis;
    }

    @Override
    public Children likeSql(boolean condition, String column, Object val) {
        getWrapper().likeSql(condition, column, val);
        return typeThis;
    }

    @Override
    public Children notLikeOrIsNull(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().notLikeOrIsNull(condition, column, val);
        return typeThis;
    }

    @Override
    public Children notLikeOrIsNull(boolean condition, String column, Object val) {
        getWrapper().notLikeOrIsNull(condition, column, val);
        return typeThis;
    }

    @Override
    public Children orderBySql(boolean condition, String val) {
        getWrapper().orderBySql(condition, val);
        return typeThis;
    }

    @Override
    public Children orderByField(boolean condition, SFunction<T, ?> column, Object... values) {
        getWrapper().orderByField(condition, column, values);
        return typeThis;
    }

    @Override
    public Children orderByField(boolean condition, String column, Object... values) {
        getWrapper().orderByField(condition, column, values);
        return typeThis;
    }

    @Override
    public Children notInOrIsNull(boolean condition, SFunction<T, ?> column, Object... values) {
        getWrapper().notInOrIsNull(condition, column, values);
        return typeThis;
    }

    @Override
    public Children notInOrIsNull(boolean condition, String column, Object... values) {
        getWrapper().notInOrIsNull(condition, column, values);
        return typeThis;
    }

    @Override
    public Children isEmpty(boolean condition, SFunction<T, ?> column) {
        getWrapper().isEmpty(condition, column);
        return typeThis;
    }

    @Override
    public Children isEmpty(boolean condition, String column) {
        getWrapper().isEmpty(condition, column);
        return typeThis;
    }

    @Override
    public Children limit(boolean condition, Number offset, Number limit) {
        getWrapper().limit(condition, offset, limit);
        return typeThis;
    }

    @Override
    public Children eq(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().eq(condition, column, val);
        return typeThis;
    }

    @Override
    public Children ne(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().ne(condition, column, val);
        return typeThis;
    }

    @Override
    public Children gt(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().gt(condition, column, val);
        return typeThis;
    }

    @Override
    public Children ge(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().ge(condition, column, val);
        return typeThis;
    }

    @Override
    public Children lt(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().lt(condition, column, val);
        return typeThis;
    }

    @Override
    public Children le(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().le(condition, column, val);
        return typeThis;
    }

    @Override
    public Children between(boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        getWrapper().between(condition, column, val1, val2);
        return typeThis;
    }

    @Override
    public Children notBetween(boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        getWrapper().notBetween(condition, column, val1, val2);
        return typeThis;
    }

    @Override
    public Children like(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().like(condition, column, val);
        return typeThis;
    }

    @Override
    public Children notLike(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().notLike(condition, column, val);
        return typeThis;
    }

    @Override
    public Children likeLeft(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().likeLeft(condition, column, val);
        return typeThis;
    }

    @Override
    public Children likeRight(boolean condition, SFunction<T, ?> column, Object val) {
        getWrapper().likeRight(condition, column, val);
        return typeThis;
    }

    @Override
    public <V> Children allEq(boolean condition, Map<String, V> params, boolean null2IsNull) {
        getWrapper().allEq(condition, params, null2IsNull);
        return typeThis;
    }

    @Override
    public <V> Children allEq(boolean condition, BiPredicate<String, V> filter, Map<String, V> params, boolean null2IsNull) {
        getWrapper().allEq(condition, filter, params, null2IsNull);
        return typeThis;
    }

    @Override
    public Children eq(boolean condition, String column, Object val) {
        getWrapper().eq(condition, column, val);
        return typeThis;
    }

    @Override
    public Children ne(boolean condition, String column, Object val) {
        getWrapper().ne(condition, column, val);
        return typeThis;
    }

    @Override
    public Children gt(boolean condition, String column, Object val) {
        getWrapper().gt(condition, column, val);
        return typeThis;
    }

    @Override
    public Children ge(boolean condition, String column, Object val) {
        getWrapper().ge(condition, column, val);
        return typeThis;
    }

    @Override
    public Children lt(boolean condition, String column, Object val) {
        getWrapper().lt(condition, column, val);
        return typeThis;
    }

    @Override
    public Children le(boolean condition, String column, Object val) {
        getWrapper().le(condition, column, val);
        return typeThis;
    }

    @Override
    public Children between(boolean condition, String column, Object val1, Object val2) {
        getWrapper().between(condition, column, val1, val2);
        return typeThis;
    }

    @Override
    public Children notBetween(boolean condition, String column, Object val1, Object val2) {
        getWrapper().notBetween(condition, column, val1, val2);
        return typeThis;
    }

    @Override
    public Children like(boolean condition, String column, Object val) {
        getWrapper().like(condition, column, val);
        return typeThis;
    }

    @Override
    public Children notLike(boolean condition, String column, Object val) {
        getWrapper().notLike(condition, column, val);
        return typeThis;
    }

    @Override
    public Children likeLeft(boolean condition, String column, Object val) {
        getWrapper().likeLeft(condition, column, val);
        return typeThis;
    }

    @Override
    public Children likeRight(boolean condition, String column, Object val) {
        getWrapper().likeRight(condition, column, val);
        return typeThis;
    }

    @Override
    public Children isNull(boolean condition, SFunction<T, ?> column) {
        getWrapper().isNull(condition, column);
        return typeThis;
    }

    @Override
    public Children isNotNull(boolean condition, SFunction<T, ?> column) {
        getWrapper().isNotNull(condition, column);
        return typeThis;
    }

    @Override
    public Children in(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        getWrapper().in(condition, column, coll);
        return typeThis;
    }

    @Override
    public Children in(boolean condition, SFunction<T, ?> column, Object... values) {
        getWrapper().in(condition, column, values);
        return typeThis;
    }

    @Override
    public Children notIn(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        getWrapper().notIn(condition, column, coll);
        return typeThis;
    }

    @Override
    public Children notIn(boolean condition, SFunction<T, ?> column, Object... values) {
        getWrapper().notIn(condition, column, values);
        return typeThis;
    }

    @Override
    public Children inSql(boolean condition, SFunction<T, ?> column, String inValue) {
        getWrapper().inSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children gtSql(boolean condition, SFunction<T, ?> column, String inValue) {
        getWrapper().gtSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children geSql(boolean condition, SFunction<T, ?> column, String inValue) {
        getWrapper().geSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children ltSql(boolean condition, SFunction<T, ?> column, String inValue) {
        getWrapper().ltSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children leSql(boolean condition, SFunction<T, ?> column, String inValue) {
        getWrapper().leSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children notInSql(boolean condition, SFunction<T, ?> column, String inValue) {
        getWrapper().notInSql(condition, column, inValue);
        return typeThis;
    }

    @Override
    public Children groupBy(SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().groupBy(column, columns);
        return typeThis;
    }

    @Override
    public Children groupBy(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().groupBy(condition, column, columns);
        return typeThis;
    }

    @Override
    public Children orderByAsc(SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().orderByAsc(column, columns);
        return typeThis;
    }

    @Override
    public Children orderByAsc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().orderByAsc(condition, column, columns);
        return typeThis;
    }

    @Override
    public Children orderByDesc(SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().orderByDesc(column, columns);
        return typeThis;
    }

    @Override
    public Children orderByDesc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().orderByDesc(condition, column, columns);
        return typeThis;
    }

    @Override
    public Children orderBy(boolean condition, boolean isAsc, SFunction<T, ?> column, SFunction<T, ?>... columns) {
        getWrapper().orderBy(condition, isAsc, column, columns);
        return typeThis;
    }
}
