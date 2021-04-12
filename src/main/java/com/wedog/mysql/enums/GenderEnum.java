package com.wedog.mysql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ly
 */

@Getter
@AllArgsConstructor
public enum GenderEnum {
    MALE("男性", 0),
    FEMALE("女性", 1),
    UNKNOW("未知", 2);

    private String title;
    private int value;

    public static GenderEnum getByValue(int value) {
        GenderEnum[] values = values();
        for (GenderEnum genderEnum : values) {
            if (Objects.equals(genderEnum.value, value)) {
                return genderEnum;
            }
        }
        return null;
    }
}
