package com.kovizone.mybatispp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * Hobby
 *
 * @author KV
 * @since 2022/10/11
 */
@Data
@TableName("hobby")
public class Hobby {

    @TableId("id")
    private Integer id;

    private String name;

    @Version
    private Integer version;
}
