package com.wedog.mysql.entity;

import com.wedog.mysql.service.PropertyFunction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author ly
 */
@Data
public class GroupByItem<T> {
    private PropertyFunction<T> propertyFunction;
}
