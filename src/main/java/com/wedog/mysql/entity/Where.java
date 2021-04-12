package com.wedog.mysql.entity;

import com.wedog.mysql.enums.AndOrEnum;
import com.wedog.mysql.enums.OperatorEnum;
import com.wedog.mysql.service.PropertyFunction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly
 */
@Data
public class Where<T> {
    private List<Condition> conditions = new ArrayList<>();

    @Data
    public class Condition {
        /**
         * 条件逻辑操作
         */
        private AndOrEnum andOrEnum;
        /**
         * 查询字段
         */
        private String property;
        /**
         * 值
         */
        private Object expect;
        /**
         * 操作符
         */
        private OperatorEnum operatorEnum;

        /**
         * 属性方法
         */
        private PropertyFunction<T> propertyFunction;
    }
}
