package com.kovizone.mybatispp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.kovizone.mybatispp.annotation.JoinType;
import com.kovizone.mybatispp.annotation.TableAlias;
import com.kovizone.mybatispp.annotation.TableJoin;
import com.kovizone.mybatispp.annotation.TableJoins;
import lombok.Data;

/**
 * Person
 *
 * @author KV
 * @since 2022/09/28
 */
@Data
@TableAlias("p")
@TableJoins({
        @TableJoin(defaultType = JoinType.INNER, value = Job.class),
        @TableJoin(value = Hobby.class, on = "p.hobby_id = h.id"),
})
@TableName("person")
public class Person {

    @TableId("id")
    private Integer id;

    private String name;

    /**
     * @see Job#getId()
     */
    private Integer jobId;

    /**
     * @see Hobby#getId()
     */
    private Integer hobbyId;

    private Integer score;

    @Version
    private Integer version;

    @TableLogic(delval = "1", value = "0")
    private Integer deleted;
}
