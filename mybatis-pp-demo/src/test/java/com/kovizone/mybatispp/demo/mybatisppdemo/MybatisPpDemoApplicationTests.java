package com.kovizone.mybatispp.demo.mybatisppdemo;

import cn.hutool.json.JSONUtil;
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

    private static final Class<Job> JOB = Job.class;
    private static final Class<Hobby> HOBBY = Hobby.class;

    @Test
    void contextLoads() {

        final Class<Job> JOB = Job.class;
        final Class<Hobby> HOBBY = Hobby.class;

        List<Map<String, Object>> maps = personMapper.queryChain()

                // Person类已通过注解配置了和Job的ON关系片段
                .leftJoin(JOB)

                .leftJoin(HOBBY, on -> on.eqColumn(Person::getHobbyId, Hobby::getId))
                // 等同于 leftJoin(Hobby.class, "p.hobby_id = h.id")

                .eq(Person::getId, 1)
                .func(JOB, w -> w.eq(Job::getId, 2))
                .func(HOBBY, w -> w.eq(Hobby::getId, 3))

                .joinMaps();

        System.out.println(JSONUtil.toJsonStr(maps));
    }
}