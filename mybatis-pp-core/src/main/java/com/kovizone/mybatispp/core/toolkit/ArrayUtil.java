package com.kovizone.mybatispp.core.toolkit;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * ArrayUtil
 *
 * @author KV
 * @since 2022/09/30
 */
public class ArrayUtil {

    public static Object[] parse(Object array) {
        if (array == null) {
            return null;
        }
        Object[] objects;
        if (array instanceof Collection) {
            return ((Collection<?>) array).toArray();
        }
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            objects = new Object[len];
            for (int i = 0; i < len; i++) {
                objects[i] = Array.get(array, i);
            }
        } else {
            objects = new Object[]{array};
        }
        return objects;
    }
}
