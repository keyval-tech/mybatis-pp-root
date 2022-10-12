package com.kovizone.mybatispp.demo.mybatisppdemo;

import com.kovizone.mybatispp.demo.entity.Job;
import com.kovizone.mybatispp.demo.entity.Person;
import com.kovizone.mybatispp.demo.mapper.JobMapper;
import com.kovizone.mybatispp.demo.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MybatisPpDemoApplicationTests {

    @Resource
    private PersonMapper personMapper;

    @Resource
    private JobMapper jobMapper;

    @Test
    void contextLoads() {
        personMapper.queryWrapper()
                .leftJoin(jobMapper, on -> on
                        .eqColumn(Person::getJobId, Job::getId)
                        .eqColumn(Person::getName, Job::getName)
                        .apply("job.id > 0")
                )
                .and(w -> w
                        .eq(Person::getId, 1)
                        .or()
                        .where(personMapper, perssonWrapper -> perssonWrapper.le(Person::getId, 1))
                        .or()
                        .where(jobMapper, jobWrapper -> jobWrapper.ge(Job::getId, 2))
                )
                .eq(Person::getId, 3)
                .joinList();
    }
}