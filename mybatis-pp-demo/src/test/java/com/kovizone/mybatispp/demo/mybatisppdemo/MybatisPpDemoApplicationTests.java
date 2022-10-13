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

                .leftJoin(Job.class)
                .leftJoin(Hobby.class, on -> on.eqColumn(Person::getHobbyId, Hobby::getId))

                .eq(Person::getId, 1)
                .func(Job.class, w -> w.eq(Job::getId, 2))
                .func(Hobby.class, w -> w.eq(Hobby::getId, 3))
                .joinMaps();

        System.out.println(JSONUtil.toJsonStr(maps));
    }
}