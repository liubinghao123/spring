package com.hao.framework.beans.factory.config;

/**
 * Created by Keeper on 2019-04-23
 */
public interface BeanPostProcessor {
    default Object postProcessBeforeInitialization(Object bean,String beanName)throws Exception{
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean,String beanName)throws Exception{
        return bean;
    }
}
