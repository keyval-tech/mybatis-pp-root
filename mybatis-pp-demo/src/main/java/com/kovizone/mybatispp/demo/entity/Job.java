package com.kovizone.mybatispp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.kovizone.mybatispp.annotation.TableAlias;
import lombok.Data;

/**
 * Job
 *
 * @author KV
 * @since 2022/10/11
 */
@Data
@TableAlias("j")
@TableName("job")
public class Job {

    @TableId("id")
    private Integer id;

    private String name;

    @Version
    private Integer version;
}
