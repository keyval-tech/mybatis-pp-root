package com.kovizone.mybatispp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.kovizone.mybatispp.annotation.TableAlias;
import com.kovizone.mybatispp.annotation.TableJoin;
import com.kovizone.mybatispp.annotation.TableJoins;
import lombok.Data;

/**
 * Job
 *
 * @author KV
 * @since 2022/10/11
 */
@Data
@TableAlias("j")
@TableJoins({
        @TableJoin(value = Person.class, on = "j.id = p.job_id"),
})
@TableName("job")
public class Job {

    @TableId("id")
    private Integer id;

    private String name;

    @Version
    private Integer version;
}
