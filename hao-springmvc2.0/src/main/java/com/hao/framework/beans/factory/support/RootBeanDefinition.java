package com.hao.framework.beans.factory.support;

import lombok.Data;

/**
 * Created by Keeper on 2019-04-13
 */
@Data
public class RootBeanDefinition extends AbstractBeanDefinition{
    private String parentName;
    private String beanClassName;
    private String factoryBeanName;
    private boolean layInit;
    private boolean scope;
    private boolean singleton;

}
