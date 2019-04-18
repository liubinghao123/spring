package com.hao.framework.beans.factory;

import com.hao.framework.beans.config.BeanDefinition;
import com.hao.framework.beans.factory.support.AbstractBeanFactory;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Keeper on 2019-04-12
 */
@Data
public class DefaultListableBeanFactory extends AbstractBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private final List<String> beanDefinitionNames = new ArrayList<>();


    @Override
    public Object getBean(Class<?> clazz) {
        return getBean(clazz.getSimpleName());
    }

    @Override
    public Object getBean(String name) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null)return null;
        try {
            //从三级缓存中获取bean
            Object singletonObject = getSingleton(name);
            if(singletonObject == null){
                singletonObject = addSingleton(name,()->{
                    try {
                        return createBean(beanDefinition);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
            }
            return singletonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition definition) throws Exception {
        BeanDefinition oldDefinition = this.beanDefinitionMap.get(beanName);
        if(oldDefinition != null){
            throw new Exception("bean name is exits , [ " + beanName + " ]");
        }
        this.beanDefinitionMap.put(beanName,definition);
        beanDefinitionNames.add(beanName);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws Exception {
        BeanDefinition oldDefinition = this.beanDefinitionMap.get(beanName);
        if(oldDefinition == null){
            throw new Exception("map not contain beanDefinition , " + beanName);
        }
        this.beanDefinitionMap.remove(beanName);
    }


    @Override
    public boolean containsBeanDefinition(String beanName) throws Exception {
        return false;
    }
}
