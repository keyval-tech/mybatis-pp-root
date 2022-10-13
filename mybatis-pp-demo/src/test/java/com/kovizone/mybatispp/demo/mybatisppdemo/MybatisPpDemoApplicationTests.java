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

    @Test
    void contextLoads() {
        List<Map<String, Object>> maps = personMapper.queryChain()
                .eq(Person::getId, 1)
                .leftJoin(Job.class, on -> on.eqColumn(Person::getJobId, Job::getId))
                .leftJoin(Hobby.class, on -> on.eqColumn(Person::getHobbyId, Hobby::getId))
                .joinMaps();

        System.out.println(JSONUtil.toJsonStr(maps));
    }
}