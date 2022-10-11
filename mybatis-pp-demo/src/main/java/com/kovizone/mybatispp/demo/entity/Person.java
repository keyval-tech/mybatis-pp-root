package com.kovizone.mybatispp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * Person
 *
 * @author KV
 * @since 2022/09/28
 */
@Data
@TableName("person p")
public class Person {

    @TableId("id")
    private Integer id;

    private String name;

    private Integer jobId;

    private Integer score;

    @Version
    private Integer version;
}
