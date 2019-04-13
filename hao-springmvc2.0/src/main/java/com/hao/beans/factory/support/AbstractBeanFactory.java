package com.hao.beans.factory.support;

import com.hao.beans.factory.BeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Keeper on 2019-04-12
 */
public abstract class AbstractBeanFactory implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, RootBeanDefinition> mergedBeanDefinition = new ConcurrentHashMap<>();

}
