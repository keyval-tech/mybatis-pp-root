# 简介

Mybatis-Plus的增强，所以取名Mybatis-PP，在MP基础上扩展，支持联表简化操作。

# 快速开始

## 0. 引入

- 引入MAVEN项目（pom.xml）

```xml
<dependency>
    <groupId>com.kovizone</groupId>
    <artifactId>mybatis-pp-boot-starter</artifactId>
    <version>3.5.2-1</version>
</dependency>
```

- 需要做出区分，与原MP类存在同名的类

```java
import com.kovizone.mybatispp.core.mapper.BaseMapper;
import com.kovizone.mybatispp.core.conditions.query.QueryWrapper;
import com.kovizone.mybatispp.core.conditions.update.UpdateWrapper;
```

## 1. Wrapper扩展

### 1.1 Lambda字段和String字段混用

```java
class Tests {
    public void test() {
        // Lambda字段和String字段混用
        new QueryWrapper<Person>().eq(Person::getId, 1).like("name", "张三");
    }
}
```

### 1.1 新增查询条件类方法

#### 1.1.1 binary 二进制匹配

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

#### 1.1.2 regexp 正则表达式匹配

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

#### 1.1.3 likeSql 自定义LIKE

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

#### 1.1.4 orderBySql 自定义ORDER BY

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

#### 1.1.5 orderByField 根据指定字段值顺序排序

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

#### 1.1.6 isEmpty 为NULL或为空字符串

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

#### 1.1.7 neOrIsNull 不等于或为空

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

#### 1.1.8 notInOrIsNull 不包括或为空

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.notInOrIsNull(Person::getName, "王", "陈");
        personMapper.selectList(queryWrapper);
        // 输出：(name NOT IN (?,?) OR name IS NULL)
    }
}
```

### 1.2 新增查询结果类方法

#### 1.2.1 distinct 去重

```java
class Tests {
    public void test() {
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.distinct(Person::getName, Person::getId);
        personMapper.selectList(queryWrapper);
        // 输出：SELECT DISTINCT name,id
    }
}
```

#### 1.2.1 leftJoin/rightJoin/innerJoin/fullJoin 左、右、内、全联查

```java
class Tests {
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

### 1.3 新增更新结果类方法

#### 1.3.1 concat 字符串拼接

```java
class Tests {
    public void test() {
        UpdateWrapper<Person> updateWrapper = personMapper.update();
        updateWrapper.eq(Person::getId, 1).concat(Person::getName, "三");
        personMapper.update(updateWrapper);
        // 输出：SET name=CONCAT_WS('',name,?)
    }
}
```

#### 1.3.2 incr 数字增加

```java
class Tests {
    public void test() {
        UpdateWrapper<Person> updateWrapper = personMapper.update();
        updateWrapper.eq(Person::getId, 1).incr(Person::getVersion, 1);
        personMapper.update(updateWrapper);
        // 输出：SET version=(version+?)
    }
}
```

#### 1.3.3 cas CAS乐观锁逻辑

```java
class Tests {
    public void test() {
        UpdateWrapper<Person> updateWrapper = personMapper.update();
        updateWrapper.eq(Person::getId, 1).cas(Person::getVersion, 1);
        personMapper.update(updateWrapper);
        // 输出：SET version=(version+?) WHERE version = 1
    }
}
```

## 2. Mapper扩展

### 2.1 生成包装类

```java
class Tests {
    public void test() {
        QueryWrapper<Person> query = personMapper.query();
        QueryChainWrapper<Person> queryChain = personMapper.queryChain();

        UpdateWrapper<Person> update = personMapper.update();
        UpdateChainWrapper<Person> updateChain = personMapper.updateChain();
    }
}
```

### 2.2 Lambda方式直接操作包装类

```java
class Tests {
    public void test() {
        personMapper.selectList(
                w -> w.eq(Person::getId, 1).eq("name", "张三")
        );
    }
}
```

## 3. 注解

### 3.1 @TableJoin和@TableJoins 配置联表信息

```java
class Tests {

    @TableJoins(
            @TableJoin(value = Job.class, on = {"person.job_id = job.id"})
    )
    @TableName("person")
    public static class Person {

        @TableId("id")
        private Integer id;

        private Integer jobId;
    }

    public void test() {
        // 需要注入实体类型
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper
                // 实体类标注@TableJoin时，可忽略join方法
                //.leftJoin(Job.class, On.eq(Person::getJobId, Job::getId))
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

### 3.2 @TableAlias 配置别名

```java
class Tests {

    @TableAlias("p")
    @TableName("person")
    public static class Person {

        @TableId("id")
        private Integer id;

        private Integer jobId;
    }

    @TableAlias("j")
    @TableName("job")
    public static class Job {

        @TableId("id")
        private Integer id;

        private String name;
    }

    public void test() {
        // 需要注入实体类型
        QueryWrapper<Person> queryWrapper = personMapper.query();
        queryWrapper.leftJoin(Job.class, On.eq(Person::getJobId, Job::getId))
                // String类字段会自动补充别名引用
                .eq("id", 1)
                // apply方法并不使用String类字段，所以不识别别名
                .apply("j.id = 2");
        // 使用联查专用方法
        personMapper.selectJoinList(queryWrapper);
        // 输出：
        // SELECT p.id,p.job_id FROM person AS p
        // LEFT JOIN job AS j ON (p.job_id=j.id) 
        // WHERE (p.id = ? AND j.id = 2)
    }
}
```