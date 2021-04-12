package com.wedog.mysql;

import com.wedog.mysql.entity.User;
import com.wedog.mysql.entity.Where;
import com.wedog.mysql.enums.AndOrEnum;
import com.wedog.mysql.enums.GenderEnum;
import com.wedog.mysql.enums.OperatorEnum;
import com.wedog.mysql.service.SqlService;
import lombok.extern.slf4j.Slf4j;
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

    @Test
    void contextLoads() {

    }

    @Test
    void TestQuery() {
        // 构造数据
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = User.builder()
                    .id(Long.valueOf(i))
                    .age((i + 1) * 10)
                    .userName("name" + i)
                    .genderEnum(GenderEnum.getByValue(i % 2))
                    .build();
            userList.add(user);
        }

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
        List<User> query = sqlService.query(userList, where, null, null, null);

        log.info(query.toString());

        Assert.notNull(query, "error");
    }

}
