package com.wedog.mysql.service;

import com.wedog.mysql.entity.*;

import java.util.List;

/**
 * @author ly
 */
public interface SqlService {
    /**
     * sql查询服务
     *
     * @param data
     * @param where
     * @param orderBy
     * @param groupBy
     * @param limit
     * @return
     */
    List<? extends Object> query(List<User> data, Where where, OrderBy orderBy, GroupBy groupBy, Limit limit);
}
