package com.kovizone.mybatispp.demo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.kovizone.mybatispp.core.conditions.query.QueryChainWrapper;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.conditions.update.UpdateChainWrapper;
import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Tests {

    @Data
    @TableName("person")
    public static class Person {

        @TableId("id")
        private Integer id;

        private String name;

        private Integer jobId;
    }

    @Data
    @TableName("job")
    public static class Job {

        @TableId("id")
        private Integer id;

        private String name;
    }

    @Mapper
    public interface PersonMapper extends BaseMapper<Person> {
    }

    @Mapper
    public interface JobMapper extends BaseMapper<Job> {
    }

    @Resource
    private PersonMapper personMapper;

    @Test
    public void test() {
        QueryWrapper<Person> query = personMapper.query();
        QueryChainWrapper<Person> queryChain = personMapper.queryChain();

        UpdateWrapper<Person> update = personMapper.update();
        UpdateChainWrapper<Person> updateChain = personMapper.updateChain();


        personMapper.selectList(
                w -> w.eq(Person::getId, 1)
                        .eq("name", "张三")
        );
    }

    @Data
    @TableName("hobby")
    public static class Hobby {

        @TableId("id")
        private Integer id;

        private String name;

        @Version
        private Integer version;
    }

    @Mapper
    public interface HobbyMapper extends BaseMapper<Hobby> {
    }

}