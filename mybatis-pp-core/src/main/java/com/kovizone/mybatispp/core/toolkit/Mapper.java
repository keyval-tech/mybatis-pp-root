package com.kovizone.mybatispp.core.toolkit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 映射器
 * <p>
 * 拦截任何异常（若发生异常转为null）
 *
 * @author KV
 * @since 2022/10/14
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper<T> {

    private T data;

    public T get() {
        return data;
    }

    public static <T> Mapper<T> of(T t) {
        return new Mapper<>(t);
    }

    public static <T, R> Mapper<R> of(T t, Function<T, R> mapper) {
        return new Mapper<>(t).map(mapper);
    }

    public static <T, R> R map(T t, Function<T, R> mapper) {
        return Mapper.of(t).map(mapper).get();
    }

    public Mapper<T> filter(Function<T, Boolean> filter) {
        if (data != null && filter.apply(data)) {
            return this;
        }
        return Mapper.of(null);
    }

    public Mapper<T> func(Consumer<T> consumer) {
        try {
            if (data != null) {
                consumer.accept(data);
            }
        } catch (Exception ignore) {
        }
        return this;
    }

    public <R> Mapper<R> map(Function<T, R> mapper) {
        try {
            if (data != null) {
                return Mapper.of(mapper.apply(data));
            }
        } catch (Exception ignore) {
        }
        return of(null);
    }

    public <R> R get(Function<T, R> function) {
        return map(function).get();
    }
}
