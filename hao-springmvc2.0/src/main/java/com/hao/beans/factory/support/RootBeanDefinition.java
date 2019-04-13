package com.hao.beans.factory.support;

/**
 * Created by Keeper on 2019-04-13
 */
public class RootBeanDefinition extends AbstractBeanDefinition{
    @Override
    public void setParentName(String parentName) {

    }
    @Override
    public String getParentName() {
        return null;
    }

    @Override
    public void setBeanClassName(String beanClassName) {

    }

    @Override
    public String getBeanClassName() {
        return null;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {

    }

    @Override
    public String getFactoryBeanName() {
        return null;
    }

    @Override
    public void setLayInit(boolean layInit) {

    }

    @Override
    public boolean isLayInit() {
        return false;
    }

    @Override
    public void setScope(boolean scope) {

    }

    @Override
    public boolean isScope() {
        return false;
    }

    @Override
    public void setSingleton(boolean singleton) {

    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
