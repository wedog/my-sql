package com.wedog.mysql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ly
 */

@Getter
@AllArgsConstructor
public enum AndOrEnum {
    AND("and", 0),
    OR("or", 1);

    private String title;
    private int value;

    public static AndOrEnum getByValue(int value) {
        AndOrEnum[] values = values();
        for (AndOrEnum andOrEnum : values) {
            if (Objects.equals(andOrEnum.value, value)) {
                return andOrEnum;
            }
        }
        return null;
    }
}
