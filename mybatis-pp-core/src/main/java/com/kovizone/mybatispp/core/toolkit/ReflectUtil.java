package com.kovizone.mybatispp.core.toolkit;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ReflectUtil
 *
 * @author KV
 * @since 2022/09/30
 */
public class ReflectUtil {

    public static boolean isReturnVoid(Method method) {
        return "void".equals(method.getReturnType().getName());
    }

    public static Object methodInvoke(Method method, Object object, Object... args) {
        if (method == null || object == null) {
            return null;
        }
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Annotation> T getAnnotation(AnnotatedElement annotatedElement, Class<T> annoClass) {
        if (annotatedElement != null && annotatedElement.isAnnotationPresent(annoClass)) {
            return annotatedElement.getDeclaredAnnotation(annoClass);
        }
        return null;
    }

    public static List<Method> getMethodList(Class<?> clazz) {
        List<Method> methodList = new ArrayList<>();
        Class<?> heap = clazz;
        while (heap != null && !heap.equals(Object.class)) {
            Method[] declaredMethods = heap.getDeclaredMethods();
            methodList.addAll(Arrays.asList(declaredMethods));
            heap = heap.getSuperclass();
        }
        return methodList;
    }

    public static List<Field> getFieldList(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> heap = clazz;
        while (heap != null && !heap.equals(Object.class)) {
            Field[] declaredFields = heap.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            heap = heap.getSuperclass();
        }
        return fieldList;
    }

    public static Object getFieldValue(Object obj, Field field) {
        if (obj instanceof Class) {
            obj = null;
        }
        try {
            if (obj == null) {
                return field.get(obj);
            }
            try {
                Method getter = getMethod(obj.getClass(), "get" + StrUtil.upperFirstCase(field.getName()));
                return getter.invoke(obj);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                field.setAccessible(true);
                return field.get(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        if (clazz != null) {
            try {
                return clazz.getMethod(name, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

