package com.hao.framework.context;

import com.hao.framework.beans.factory.DefaultListableBeanFactory;
import com.hao.framework.beans.factory.support.AnnotationBeanDefinitionReader;
import com.hao.framework.beans.factory.support.BeanDefinitionReader;
import com.hao.framework.context.support.AbstractApplicationContext;

/**
 * Created by Keeper on 2019-04-12
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {
    private BeanDefinitionReader reader;
    private String[] locations;
    public AnnotationConfigApplicationContext(String... locations){
        this.locations = locations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void  loadBeanDefinitions(DefaultListableBeanFactory factory) {
        this.reader = new AnnotationBeanDefinitionReader(factory);
        loadBeanDefinitions(reader);
        //创建非延迟加载的beanDefinition实例
        try {
            createBean(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public BeanDefinitionReader getReader() {
        return reader;
    }

}
