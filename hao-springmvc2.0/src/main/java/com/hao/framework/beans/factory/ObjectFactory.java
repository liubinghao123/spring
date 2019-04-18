package com.hao.framework.beans.factory;

/**
 * Created by Keeper on 2019-04-18
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject() throws RuntimeException;
}
