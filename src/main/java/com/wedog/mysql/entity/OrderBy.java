package com.wedog.mysql.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly
 */
@Data
@Builder
public class OrderBy {
    private List<OrderByItem> orderByItemList = new ArrayList<>();
}
