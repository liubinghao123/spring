package com.hao.beans.factory.support;

import com.hao.beans.config.BeanDefinition;

/**
 * Created by Keeper on 2019-04-13
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition definition) throws Exception ;

    void removeBeanDefinition(String beanName)throws Exception;

    boolean containsBeanDefinition(String beanName)throws Exception;

}
