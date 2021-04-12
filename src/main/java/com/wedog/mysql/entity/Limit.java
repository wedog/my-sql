package com.wedog.mysql.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author ly
 */
@Data
@Builder
public class Limit {
    private int maxSize = 100;
}
