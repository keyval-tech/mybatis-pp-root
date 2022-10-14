# 简介

Mybatis-Plus的增强，所以取名Mybatis-PP，在MP基础上扩展，支持联表简化操作。

# 功能增强和快速开始

## 1. 包含Mybatis-Plus完整功能，不做修改

## 2. Wrapper功能扩展

### 2.1 Lambda字段和String字段混用

```java
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;

@SpringBootTest
class Tests {

    @Data
    @TableName("person")
    public static class Person {

        @TableId("id")
        private Integer id;

        private String name;

        @Version
        private Integer version;
    }

    @Mapper
    public interface PersonMapper extends BaseMapper<Person> {
    }

    @Resource
    private PersonMapper personMapper;

    @Test
    public void test() {
        // Lambda字段和String字段混用
        new QueryWrapper<Person>().eq(Person::getId, 1).like("name", "张三");
    }
}
```

### 2.1 新增查询条件类方法

#### 2.1.1 binary 二进制匹配

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.binary(Person::getId, 1);
        personMapper.selectList(queryWrapper);
        // 输出：BINARY id = ?
    }
}
```

#### 2.1.2 regexp 正则表达式匹配

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.regexp(Person::getName, "王*");
        personMapper.selectList(queryWrapper);
        // 输出：name REGEXP ?
    }
}
```

#### 2.1.3 likeSql 自定义LIKE

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.likeSql(Person::getName, "张%三");
        personMapper.selectList(queryWrapper);
        // 输出：name LIKE ?
    }
}
```

#### 2.1.4 orderBySql 自定义ORDER BY

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.orderBySql("name DESC");
        personMapper.selectList(queryWrapper);
        // 输出：ORDER BY name DESC
    }
}
```

#### 2.1.5 orderByField 根据指定字段值顺序排序

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.orderByField(Person::getId, 2, 1, 3);
        personMapper.selectList(queryWrapper);
        // 输出：ORDER BY FIELD(id,?,?,?) ASC
    }
}
```

#### 2.1.6 isEmpty 为NULL或为空字符串

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.isEmpty(Person::getName);
        personMapper.selectList(queryWrapper);
        // 输出：(name IS NULL OR name = '')
    }
}
```

#### 2.1.7 neOrIsNull 不等于或为空

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.neOrIsNull(Person::getName, "王");
        personMapper.selectList(queryWrapper);
        // 输出：(name <> '' OR name IS NULL)
    }
}
```

#### 2.1.8 notInOrIsNull 不包括或为空

```java
class Tests {
    @Test
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.notInOrIsNull(Person::getName, "王", "陈");
        personMapper.selectList(queryWrapper);
        // 输出：(name NOT IN (?,?) OR name IS NULL)
    }
}
```

### 2.2 新增查询结果类方法

#### 2.2.1 distinct 去重

```java
class Tests {
    @Test
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.distinct(Person::getName, Person::getId);
        personMapper.selectList(queryWrapper);
        // 输出：SELECT DISTINCT name,id
    }
}
```

#### 2.2.1 leftJoin/rightJoin/innerJoin/fullJoin 左、右、内、全联查

```java
class Tests {

    @Data
    @TableName("person")
    public static class Person {

        @TableId("id")
        private Integer id;

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
        // 需要注入实体类型
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.leftJoin(Job.class, On.eq(Person::getJobId, Job::getId))
                // 使用func调用联查表的包装方法
                .func(Job.class, jw -> jw.eq(Job::getId, 1));
        // 使用联查专用方法
        personMapper.selectJoinList(queryWrapper);
        // 输出：
        // SELECT person.id,person.job_id 
        // FROM person 
        // LEFT JOIN job ON (person.job_id=job.id) 
        // WHERE (job.id = ?)
    }
}
```

### 2.3 新增更新结果类方法

#### 2.3.1 concat 字符串拼接

```java
class Tests {
    @Test
    public void test() {
        UpdateWrapper<Person> updateWrapper = personMapper.update();
        updateWrapper.eq(Person::getId, 1).concat(Person::getName, "三");
        personMapper.update(updateWrapper);
        // 输出：SET name=CONCAT_WS('',name,?)
    }
}
```

#### 2.3.2 incr 数字增加

```java
class Tests {
    @Test
    public void test() {
        UpdateWrapper<Person> updateWrapper = personMapper.update();
        updateWrapper.eq(Person::getId, 1).incr(Person::getVersion, 1);
        personMapper.update(updateWrapper);
        // 输出：SET version=(version+?)
    }
}
```

#### 2.3.3 cas CAS乐观锁逻辑

```java
class Tests {
    @Test
    public void test() {
        UpdateWrapper<Person> updateWrapper = personMapper.update();
        updateWrapper.eq(Person::getId, 1).cas(Person::getVersion, 1);
        personMapper.update(updateWrapper);
        // 输出：SET version=(version+?) WHERE version = 1
    }
}
```

## 3. Mapper功能扩展

### 3.1 生成包装类

```java
class Tests {
    @Test
    public void test() {
        QueryWrapper<Person> query = personMapper.query();
        QueryChainWrapper<Person> queryChain = personMapper.queryChain();

        UpdateWrapper<Person> update = personMapper.update();
        UpdateChainWrapper<Person> updateChain = personMapper.updateChain();
    }
}
```


### 3.2 Lambda方式直接操作包装类

```java
class Tests {
    @Test
    public void test() {
        personMapper.selectList(
                w -> w.eq(Person::getId, 1)
                        .eq("name", "张三")
        );
    }
}
```