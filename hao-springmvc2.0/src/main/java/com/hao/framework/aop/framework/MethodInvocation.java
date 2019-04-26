package com.hao.framework.aop.framework;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Keeper on 2019-04-25
 */
@Data
public class MethodInvocation implements JoinPoint {
    private Object proxy;
    private Method method;
    private Object target;
    private Class<?> targetClass;
    private Object[] args = new Object[0];
    private List<?> interceptorsAndDynamicMethodMatchers;


    public MethodInvocation(Object proxy, Method method, Object target, Class<?> targetClass, Object[] args, List<?> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object process(){
        return null;
    }
}
