package com.hao.beans.config;

import lombok.Data;

/**
 * Created by Keeper on 2019-04-12
 */
public interface BeanDefinition {
     void setParentName(String parentName);

     String getParentName();

     void setBeanClassName(String beanClassName);

     String getBeanClassName();

     void setFactoryBeanName(String factoryBeanName);

     String getFactoryBeanName();

     void setLayInit(boolean layInit);

     boolean isLayInit();

     void setScope(boolean scope);

     boolean isScope();

     void setSingleton(boolean singleton);

     boolean isSingleton();
}
