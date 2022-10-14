package com.kovizone.mybatispp.demo.mybatisppdemo;

import cn.hutool.json.JSONUtil;
import com.kovizone.mybatispp.core.conditions.query.On;
import com.kovizone.mybatispp.demo.entity.Hobby;
import com.kovizone.mybatispp.demo.entity.Job;
import com.kovizone.mybatispp.demo.entity.Person;
import com.kovizone.mybatispp.demo.mapper.JobMapper;
import com.kovizone.mybatispp.demo.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPpDemoApplicationTests {

    @Resource
    private PersonMapper personMapper;

    @Resource
    private JobMapper jobMapper;

    private static final Class<Person> PERSON = Person.class;
    private static final Class<Job> JOB = Job.class;
    private static final Class<Hobby> HOBBY = Hobby.class;

    @Test
    void contextLoads() {

        List<Map<String, Object>> maps = personMapper.queryChain()

                .leftJoin(JOB, On.eq(Person::getJobId, Job::getId))

                .func(PERSON, p -> p.eq(Person::getId, 1))
                .func(JOB, j -> j.eq(Job::getId, 2))
                .func(HOBBY, h -> h.eq(Hobby::getId, 3))

                .joinMaps();

        System.out.println(JSONUtil.toJsonStr(maps));
    }
}