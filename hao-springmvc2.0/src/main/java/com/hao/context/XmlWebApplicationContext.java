package com.hao.context;

import com.hao.beans.factory.DefaultListableBeanFactory;
import com.hao.beans.factory.support.BeanDefinitionReader;
import com.hao.beans.factory.support.XmlBeanDefinitionReader;
import com.hao.context.support.AbstractApplicationContext;

/**
 * Created by Keeper on 2019-04-12
 */
public class XmlWebApplicationContext extends AbstractApplicationContext {
    private String[] locations;
    public XmlWebApplicationContext(String... locations){
        this.locations = locations;
    }

    @Override
    public void loadBeanDefinitions(DefaultListableBeanFactory factory) {
        loadBeanDefinitions(new XmlBeanDefinitionReader());
    }

    public void loadBeanDefinitions(BeanDefinitionReader reader) {
        reader.loadBeanDefinitions(locations);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return null;
    }

    @Override
    public Object getBean(String name) {
        return null;
    }


}