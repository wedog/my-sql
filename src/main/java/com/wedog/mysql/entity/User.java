package com.wedog.mysql.entity;

import com.wedog.mysql.enums.GenderEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author ly
 */
@Data
@Builder
public class User {
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    private GenderEnum genderEnum;
}
