package com.kovizone.mybatispp.demo.query;

import com.kovizone.mybatispp.annotation.Operation;
import com.kovizone.mybatispp.annotation.WrapperModel;
import com.kovizone.mybatispp.annotation.WrapperModelProperty;
import com.kovizone.mybatispp.demo.entity.Person;
import lombok.Data;

import java.util.List;

/**
 * PersonQuery
 *
 * @author KV
 * @see Person
 * @since 2022/09/30
 */
@WrapperModel(model = Person.class, lastOrderBy = {"id"})
@Data
public class PersonQuery {

    @WrapperModelProperty(condition = "{0} != null && {0}.size() > 0", operation = Operation.IN)
    private List<Integer> id;

    @WrapperModelProperty(condition = "1.equals({0})", operation = Operation.ORDER_BY_DESC, arg = "name")
    private Integer orderByName;

    @WrapperModelProperty(operation = Operation.GE)
    public String getName() {
        return "王一王";
    }
}
