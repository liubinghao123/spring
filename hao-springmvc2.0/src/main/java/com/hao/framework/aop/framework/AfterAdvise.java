package com.hao.framework.aop.framework;

import java.lang.reflect.Method;

/**
 * Created by Keeper on 2019-04-25
 */
public class AfterAdvise implements Advise{
    private Method adviseMethod;

    public AfterAdvise(Method adviseMethod) {
        this.adviseMethod = adviseMethod;
    }

    public  void after(Method method,Object[] args,Object target){

    }
}
