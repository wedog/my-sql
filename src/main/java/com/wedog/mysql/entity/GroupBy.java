package com.wedog.mysql.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author ly
 */
@Data
@Builder
public class GroupBy {
    private List<GroupByItem> groupByItems = new ArrayList<>();
}
