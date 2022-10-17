package com.kovizone.mybatispp.annotation;

/**
 * 默认类型标记
 *
 * @author KV
 * @since 2022/10/17
 */
public final class DefaultType {

    public static boolean isDefaultType(WrapperModel wrapperModel) {
        return DefaultType.class.equals(wrapperModel.model());
    }

    public static boolean isDefaultType(WrapperModelProperty wrapperModelProperty) {
        return DefaultType.class.equals(wrapperModelProperty.model());
    }

}
