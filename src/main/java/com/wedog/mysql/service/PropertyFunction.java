package com.wedog.mysql.service;

/**
 * @author ly
 */
@FunctionalInterface
public interface PropertyFunction<T> {

    /**
     * property 方法
     *
     * @param o
     * @return
     */
    Object getProperty(T o);
}
