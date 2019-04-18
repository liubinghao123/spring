package com.hao.framework.beans.factory;

/**
 * Created by Keeper on 2019-04-12
 */
public interface BeanFactory {
    /**
     * 通过类型获取bean
     * @param clazz
     * @return
     */
    Object getBean(Class<?> clazz);

    /**
     * 通过名称获取bean
     * @param name
     * @return
     */
    Object getBean(String name);
}
