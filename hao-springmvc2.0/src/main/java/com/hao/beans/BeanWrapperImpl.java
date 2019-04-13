package com.hao.beans;

/**
 * Created by Keeper on 2019-04-13
 */
public class BeanWrapperImpl implements  BeanWrapper{
    private Class<?> beanWrapperClass;
    private Object beanWrapperInstance;

    public BeanWrapperImpl(Object beanWrapperInstance,Class<?> beanWrapperClass){
        this.beanWrapperInstance = beanWrapperInstance;
        this.beanWrapperClass = beanWrapperClass;
    }
    @Override
    public Object getWrapperInstance() {
        return this.beanWrapperInstance;
    }

    @Override
    public Class<?> getWrapperClass() {
        return this.beanWrapperClass;
    }
}
