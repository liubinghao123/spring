package com.hao.framework.aop.framework;

import java.lang.reflect.Method;

/**
 * Created by Keeper on 2019-04-25
 */
public class BeforeAdvise implements Advise{
    private Method method;

    public BeforeAdvise(Method method) {
        this.method = method;
    }

    public void before(Method method, Object[] args, Object target){

    }
}
