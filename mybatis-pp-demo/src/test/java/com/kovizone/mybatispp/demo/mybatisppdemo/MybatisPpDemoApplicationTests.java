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
        personMapper.selectAll();
    }

    @Test
    void contextLoads2() {
        personMapper.queryWrapper()
                .inWrapper(Person::getJobId, jobMapper.queryWrapper().eq(Job::getId, 1));
    }
}