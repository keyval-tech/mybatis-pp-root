package com.kovizone.mybatispp.core.conditions.interfaces;

import com.baomidou.mybatisplus.core.conditions.interfaces.Nested;
import org.apache.ibatis.annotations.Param;

/**
 * ExtendNested
 *
 * @author KV
 * @since 2022/10/09
 */
public interface ExtendNested<Param, Children> extends Nested<Param, Children> {
}

