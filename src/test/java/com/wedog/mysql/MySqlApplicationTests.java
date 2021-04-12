package com.wedog.mysql;

import com.wedog.mysql.entity.*;
import com.wedog.mysql.enums.AndOrEnum;
import com.wedog.mysql.enums.GenderEnum;
import com.wedog.mysql.enums.OperatorEnum;
import com.wedog.mysql.enums.OrderEnum;
import com.wedog.mysql.service.SqlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class MySqlApplicationTests {

    @Autowired
    private SqlService sqlService;

    private List<User> userList;

    @BeforeEach
    void before() {
        log.info("<===============begin===================>");
        userList = new ArrayList<>();
        // 构造数据，超大数据量暂未考虑
        for (int i = 0; i < 1000; i++) {
            User user = User.builder()
                    .id(Long.valueOf(i))
                    .age((i + 1) * 10)
                    .userName("name" + i)
                    .genderEnum(GenderEnum.getByValue(i % 2))
                    .build();
            userList.add(user);
        }
        log.info("<===============userList.size===================>" + userList.size());
    }

    @Test
    void TestQueryByWhere() {
        // 构造where条件
        Where<User> where = new Where();
        Where<User>.Condition condition1 = where.new Condition();
        condition1.setPropertyFunction(User::getId);
        condition1.setExpect(1L);
        condition1.setAndOrEnum(AndOrEnum.AND);
        condition1.setOperatorEnum(OperatorEnum.EQUAL);
        Where<User>.Condition condition2 = where.new Condition();
        condition2.setPropertyFunction(User::getAge);
        condition2.setExpect(9020);
        condition2.setAndOrEnum(AndOrEnum.OR);
        condition2.setOperatorEnum(OperatorEnum.GREATER);
        List conditions = new ArrayList();
        conditions.add(condition1);
        conditions.add(condition2);
        where.setConditions(conditions);

        // 执行查询
        List<?> result = sqlService.query(userList, where, null, null, null);

        Assert.isTrue(((User) result.get(0)).getId() == 1L, "筛选错误！");
    }

    @Test
    void TestQueryByOrder() {
        // 构造order
        OrderByItem<User, Long> orderByItem1 = new OrderByItem<>();
        orderByItem1.setFunction(User::getId);
        orderByItem1.setOrderEnum(OrderEnum.DESC);
        OrderByItem<User, Integer> orderByItem2 = new OrderByItem<>();
        orderByItem2.setFunction(User::getAge);
        orderByItem2.setOrderEnum(OrderEnum.ASC);
        List<OrderByItem> orderByItemList = new ArrayList<>();
        orderByItemList.add(orderByItem1);
        orderByItemList.add(orderByItem2);
        OrderBy orderBy = OrderBy.builder().orderByItemList(orderByItemList).build();

        // 执行查询
        List<?> result = sqlService.query(userList, null, orderBy, null, null);

        Assert.isTrue(((User) result.get(0)).getId() == 999, "排序错误！");
    }

    @Test
    void TestQueryByGroup() {
        // 构造分组
        GroupByItem<User> groupByItem1 = new GroupByItem<>();
        groupByItem1.setPropertyFunction(User::getUserName);
        GroupByItem<User> groupByItem2 = new GroupByItem<>();
        groupByItem2.setPropertyFunction(User::getGenderEnum);
        List<GroupByItem> groupByItems = new ArrayList<>();
        groupByItems.add(groupByItem1);
        groupByItems.add(groupByItem2);
        GroupBy groupBy = GroupBy.builder().groupByItems(groupByItems).build();

        // 执行查询
        List<?> result = sqlService.query(userList, null, null, groupBy, null);

        Assert.isTrue(result.size() <= 1, "返回数据条数不对！");
    }

    @Test
    void TestQueryByLimit() {
        // 构造limit
        int maxSize = 5;
        Limit limit = Limit.builder().maxSize(maxSize).build();

        // 执行查询
        List<?> result = sqlService.query(userList, null, null, null, limit);

        Assert.isTrue(result.size() == maxSize, "返回数据条数不对！");
    }

}
