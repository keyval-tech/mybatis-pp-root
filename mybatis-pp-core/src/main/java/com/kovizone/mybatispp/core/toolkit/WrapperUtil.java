package com.kovizone.mybatispp.core.toolkit;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.kovizone.mybatispp.annotation.*;
import com.kovizone.mybatispp.core.conditions.AbstractExtendWrapper;
import com.kovizone.mybatispp.core.conditions.query.ExtendQuery;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.conditions.update.ExtendUpdate;
import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * WrapperUtil
 *
 * @author KV
 * @since 2022/09/30
 */
public class WrapperUtil {

    private final static ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 转为查询包装类
     *
     * @param obj 查询参数对象
     * @param <T> 实体类型
     * @return 查询包装类
     */
    public static <T> QueryWrapper<T> query(Object obj) {
        return new QueryWrapper<T>().func(w -> wrapperModelProcessor(w, obj));
    }

    /**
     * 转为更新包装类
     *
     * @param obj 更新参数对象
     * @param <T> 实体类型
     * @return 更新包装类
     */
    public static <T> UpdateWrapper<T> update(Object obj) {
        return new UpdateWrapper<T>().func(w -> wrapperModelProcessor(w, obj));
    }

    @SuppressWarnings("unchecked")
    public static <T, W extends AbstractExtendWrapper<T, W>> void wrapperModelProcessor(W wrapper, Object object) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            WrapperModel wrapperModel = ReflectUtil.getAnnotation(clazz, WrapperModel.class);

            // 实体类型
            if (wrapper.getEntityClass() == null) {
                Class<?> modelClass = wrapperModel != null ? wrapperModel.model() : DefaultType.class;
                if (DefaultType.isDefaultType(wrapperModel)) {
                    wrapper.setEntityClass((Class<T>) wrapperModel.model());
                }
            }

            // 自定义SQL
            String[] applyArr = wrapperModel != null ? wrapperModel.where() : new String[]{};
            for (String apply : applyArr) {
                wrapper.apply(apply);
            }

            // 自定义排序
            String[] orderByArr = wrapperModel != null ? wrapperModel.orderBy() : new String[]{};
            for (String orderBy : orderByArr) {
                wrapper.orderBySql(orderBy);
            }

            // 处理WrapperModelProperty注解
            List<Field> fieldList = ReflectUtil.getFieldList(clazz);
            for (Field field : fieldList) {
                wrapperModelPropertyProcessor(wrapper, object, field);
            }

            List<Method> methodList = ReflectUtil.getMethodList(clazz);
            for (Method method : methodList) {
                wrapperModelPropertyProcessor(wrapper, object, method);
            }

            // 自定义排序（滞后的）
            String[] lastOrderBy = wrapperModel != null ? wrapperModel.lastOrderBy() : new String[]{};
            for (String orderBy : lastOrderBy) {
                wrapper.orderBySql(orderBy);
            }
        }
    }

    private static <T, W extends AbstractExtendWrapper<T, W>> void wrapperModelPropertyProcessor(W wrapper, Object object, Method method) {
        // 仅支持入参为空，返回非void的方法
        if (method.getParameters().length == 0 && !ReflectUtil.isReturnVoid(method)) {
            WrapperModelProperty wrapperModelProperty = ReflectUtil.getAnnotation(method, WrapperModelProperty.class);
            if (wrapperModelProperty != null) {
                String property = StrUtil.lowerFirstCase(StrUtil.removeAnyPrefix(method.getName(), "get", "is"));
                Object memberValue = ReflectUtil.methodInvoke(method, object);
                wrapperModelPropertyProcessor(wrapper, object, property, wrapperModelProperty, memberValue);
            }
        }
    }

    private static <T, W extends AbstractExtendWrapper<T, W>> void wrapperModelPropertyProcessor(W wrapper, Object object, Field field) {
        WrapperModelProperty wrapperModelProperty = ReflectUtil.getAnnotation(field, WrapperModelProperty.class);
        if (wrapperModelProperty != null) {
            String property = field.getName();
            Object memberValue = ReflectUtil.getFieldValue(object, field);
            wrapperModelPropertyProcessor(wrapper, object, property, wrapperModelProperty, memberValue);
        }
    }

    private static <T, W extends AbstractExtendWrapper<T, W>> void wrapperModelPropertyProcessor(W wrapper, Object object, String property, WrapperModelProperty wrapperModelProperty, Object memberValue) {
        if (wrapperModelProperty != null) {
            Object arg = wrapperModelProperty.arg().isEmpty() ? memberValue : wrapperModelProperty.arg();

            // 条件判断
            Expression expression = EXPRESSION_PARSER.parseExpression(wrapperModelProperty.condition().replace("{0}", "#arg"));
            EvaluationContext context = new StandardEvaluationContext();
            context.setVariable("arg", memberValue);
            Boolean conditionResult = (Boolean) expression.getValue(context);

            // 执行操作
            if (conditionResult != null && conditionResult) {
                String[] columnArr = wrapperModelProperty.column();
                Operation operation = wrapperModelProperty.operation();
                if (columnArr.length == 0) {
                    String column = TableUtil.getColumnByProperty(wrapper.getEntityClass(), property);
                    operation(wrapper, column, operation, arg);
                } else if (!operation.isSupportOr() || columnArr.length == 1) {
                    for (String column : columnArr) {
                        operation(wrapper, column, operation, arg);
                    }
                } else {
                    wrapper.nested(subWrapper -> {
                        for (int i = 0; i < columnArr.length; i++) {
                            operation(subWrapper.or(i != 0), columnArr[i], operation, arg);
                        }
                    });
                }
            }
        }
    }

    private static <T, W extends AbstractExtendWrapper<T, W>> void operation(W wrapper, String column, Operation operation, Object arg) {
        switch (operation) {
            case EQ:
                wrapper.eq(column, arg);
                break;
            case NE:
                wrapper.ne(column, arg);
                break;
            case NE_OR_IS_NULL:
                wrapper.neOrIsNull(column, arg);
                break;
            case GE:
                wrapper.ge(column, arg);
                break;
            case GT:
                wrapper.gt(column, arg);
                break;
            case LE:
                wrapper.le(column, arg);
                break;
            case LT:
                wrapper.lt(column, arg);
                break;
            case LIKE:
                wrapper.like(column, arg);
                break;
            case LIKE_SQL:
                wrapper.likeSql(column, arg);
                break;
            case LIKE_LEFT:
                wrapper.likeLeft(column, arg);
                break;
            case LIKE_RIGHT:
                wrapper.likeRight(column, arg);
                break;
            case NOT_LIKE:
                wrapper.notLike(column, arg);
                break;
            case NOT_LIKE_OR_IS_NULL:
                wrapper.notLikeOrIsNull(column, arg);
                break;
            case IN:
                wrapper.in(column, ArrayUtil.parse(arg));
                break;
            case NOT_IN:
                wrapper.notIn(column, ArrayUtil.parse(arg));
                break;
            case NOT_IN_OR_IS_NULL:
                wrapper.notInOrIsNull(column, ArrayUtil.parse(arg));
                break;
            case IS_NULL:
                wrapper.isNull(StrUtil.defaultIfEmpty(column, String.valueOf(arg)));
                break;
            case IS_NOT_NULL:
                wrapper.isNotNull(StrUtil.defaultIfEmpty(column, String.valueOf(arg)));
                break;
            case BINARY:
                wrapper.binary(column, arg);
                break;
            case REGEXP:
                wrapper.regexp(column, arg);
                break;
            case ORDER_BY:
                wrapper.orderBySql(StrUtil.defaultIfEmpty(column, String.valueOf(arg)));
                break;
            case ORDER_BY_ASC:
                wrapper.orderByAsc(StrUtil.defaultIfEmpty(column, String.valueOf(arg)));
                break;
            case ORDER_BY_DESC:
                wrapper.orderByDesc(StrUtil.defaultIfEmpty(column, String.valueOf(arg)));
                break;
            case SET:
                updateWrapperConsumer(wrapper, w -> w.set(column, arg));
                break;
            case CONCAT:
                updateWrapperConsumer(wrapper, w -> w.concat(column, arg));
                break;
            case INCR:
                updateWrapperConsumer(wrapper, w -> w.incr(column, arg));
                break;
            case CAS:
                updateWrapperConsumer(wrapper, w -> w.cas(column, arg));
                break;
            case SELECT:
                queryWrapperConsumer(wrapper, w -> w.select(StrUtil.defaultIfEmpty(column, String.valueOf(arg))));
                break;
            case DISTINCT:
                queryWrapperConsumer(wrapper, w -> w.distinct(StrUtil.defaultIfEmpty(column, String.valueOf(arg))));
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, W extends AbstractExtendWrapper<T, W>> void updateWrapperConsumer(W wrapper, Consumer<ExtendUpdate<T, W>> consumer) {
        if (consumer != null && Update.class.isAssignableFrom(wrapper.getClass())) {
            consumer.accept(((ExtendUpdate<T, W>) wrapper));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, W extends AbstractExtendWrapper<T, W>> void queryWrapperConsumer(W wrapper, Consumer<ExtendQuery<T, W>> consumer) {
        if (consumer != null && Update.class.isAssignableFrom(wrapper.getClass())) {
            consumer.accept((ExtendQuery<T, W>) wrapper);
        }
    }

    /**
     * 获取entityClass标记的TableJoin对象
     *
     * @param entityClass    主实体类
     * @param joinEntityType 连接实体类
     * @param <T1>           主实体
     * @param <T2>           连接实体
     * @return TableJoin
     */
    public static <T1, T2> TableJoin getTableJoin(Class<T1> entityClass, Class<T2> joinEntityType) {
        if (entityClass != null) {
            TableJoin tableJoin = ReflectUtil.getAnnotation(entityClass, TableJoin.class);
            if (tableJoin != null && tableJoin.value().equals(joinEntityType)) {
                return tableJoin;
            }
            TableJoins tableJoins = ReflectUtil.getAnnotation(entityClass, TableJoins.class);
            if (tableJoins != null) {
                for (TableJoin tableJoinNode : tableJoins.value()) {
                    if (tableJoinNode.value().equals(joinEntityType)) {
                        return tableJoinNode;
                    }
                }
            }
        }
        return null;
    }

    private final static Map<Class<?>, String> TABLE_ALIAS_CACHE = new HashMap<>();

    /**
     * 获取实体映射表别名（若无，尝试读取表名）
     *
     * @param entityClass 实体类
     * @return 表别名
     */
    public static String getTableAlias(Class<?> entityClass) {
        if (entityClass == null) {
            return null;
        }
        return TABLE_ALIAS_CACHE.computeIfAbsent(entityClass, k -> {
            String tableAlias = ObjectUtil.map(ReflectUtil.getAnnotation(k, TableAlias.class), TableAlias::value);
            if (tableAlias == null) {
                tableAlias = ObjectUtil.map(ReflectUtil.getAnnotation(k, TableName.class), TableName::value);
            }
            return tableAlias;
        });
    }
}