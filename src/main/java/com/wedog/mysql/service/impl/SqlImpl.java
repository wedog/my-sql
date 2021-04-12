package com.wedog.mysql.service.impl;

import com.wedog.mysql.entity.*;
import com.wedog.mysql.enums.AndOrEnum;
import com.wedog.mysql.enums.OperatorEnum;
import com.wedog.mysql.service.PropertyFunction;
import com.wedog.mysql.service.SqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author ly
 */
@Slf4j
@Service
public class SqlImpl implements SqlService {

    @Override
    public List<User> query(List<User> data, Where where, OrderBy orderBy, GroupBy groupBy, Limit limit) {
        if (CollectionUtils.isEmpty(data)) {
            return data;
        }

        // 过滤
        if (Objects.nonNull(where) && where.getConditions().size() > 0) {
            Predicate<User> predicate = makePredicate(where);
            data = data.stream().filter(predicate).collect(Collectors.toList());
        }

        // 排序
        List<User> userList = data.stream()
                .sorted(Comparator.comparing(User::getAge).reversed().thenComparing(User::getUserName, Comparator.naturalOrder()))
                .collect(Collectors.toList());

        return userList;
    }

    /**
     * 根据where构造predicate
     *
     * @param where
     * @return
     */
    private Predicate<User> makePredicate(Where where) {
        List<Where.Condition> conditions = where.getConditions();
        Where.Condition condition = conditions.get(0);
        PropertyFunction propertyFunction = condition.getPropertyFunction();
        Predicate<User> userPredicate = o -> {
            Object expect = condition.getExpect();
            Object value = propertyFunction.getProperty(o);
            return compare(value, expect, condition.getOperatorEnum());
        };
        for (int i = 1; i < conditions.size(); i++) {
            Where.Condition condTmp = conditions.get(i);
            PropertyFunction pfTmp = condTmp.getPropertyFunction();
            AndOrEnum andOrEnum = condTmp.getAndOrEnum();
            if (Objects.equals(AndOrEnum.AND, andOrEnum)) {
                userPredicate = userPredicate.and(o -> {
                    Object value = pfTmp.getProperty(o);
                    Object expect = condTmp.getExpect();
                    return compare(value, expect, condTmp.getOperatorEnum());
                });
            } else if (Objects.equals(AndOrEnum.OR, andOrEnum)) {
                userPredicate = userPredicate.or(o -> {
                    Object value = pfTmp.getProperty(o);
                    Object expect = condTmp.getExpect();
                    return compare(value, expect, condTmp.getOperatorEnum());
                });
            }
        }
        return userPredicate;
    }

    /**
     * 根据操作符号做比较
     *
     * @param value
     * @param expect
     * @param operatorEnum
     * @return
     */
    private boolean compare(Object value, Object expect, OperatorEnum operatorEnum) {
        if (Objects.isNull(value)) {
            return false;
        }
        return operatorEnum.compare(value, expect);
    }

    /**
     * 根据属性名获取getXXX()
     *
     * @param property
     * @return
     */
    private Object findAccessMethod(String property) {
        Method[] methods = User.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (("get" + property).toLowerCase().equals(methods[i].getName().toLowerCase())) {
                return methods[i];
            }
        }
        return null;
    }

    /**
     * 根据属性名获取Field
     *
     * @param property
     * @return
     */
    private Field findFieldByName(String property) {
        try {
            Field declaredField = User.class.getDeclaredField(property);
            return declaredField;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

}
