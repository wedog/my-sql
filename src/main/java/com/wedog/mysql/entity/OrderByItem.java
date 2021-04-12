package com.wedog.mysql.entity;

import com.wedog.mysql.enums.OrderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Function;

/**
 * @author ly
 */
@Data
public class OrderByItem<T, R> {
    /**
     * 属性方法
     */
    private Function<T, R> function;

    /**
     * 升序/降序
     */
    private OrderEnum orderEnum;
}
