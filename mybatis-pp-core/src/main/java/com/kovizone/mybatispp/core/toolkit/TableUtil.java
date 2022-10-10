package com.kovizone.mybatispp.core.toolkit;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableUtil
 *
 * @author KV
 * @since 2022/09/30
 */
public class TableUtil {

    private final static Map<Class<?>, Map<String, String>> CLASS_PROPERTY_COLUMN_CACHE = new HashMap<>();

    public static String getColumnByProperty(Class<?> clazz, String property) {
        if (clazz == null || StrUtil.isEmpty(property)) {
            return null;
        }
        Map<String, String> propertyColumnMap = CLASS_PROPERTY_COLUMN_CACHE.get(clazz);
        if (propertyColumnMap == null) {
            propertyColumnMap = new HashMap<>();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
            if (tableInfo != null) {
                if (StrUtil.isNotEmpty(tableInfo.getKeyColumn())) {
                    propertyColumnMap.put(tableInfo.getKeyProperty(), tableInfo.getKeyColumn());
                }
                List<TableFieldInfo> tableInfoFieldList = tableInfo.getFieldList();
                if (tableInfoFieldList != null && !tableInfoFieldList.isEmpty()) {
                    for (TableFieldInfo tableFieldInfo : tableInfoFieldList) {
                        propertyColumnMap.put(tableFieldInfo.getProperty(), tableFieldInfo.getColumn());
                    }
                }
            }
            CLASS_PROPERTY_COLUMN_CACHE.put(clazz, propertyColumnMap);
        }
        return propertyColumnMap.get(property);
    }

}
