package com.hao.framework.aop.framework;

import lombok.Data;
import org.omg.CORBA.portable.InvokeHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by Keeper on 2019-04-25
 */
@Data
public class JDKDynamicProxy implements AopProxy , InvocationHandler {
    private ProxyFactory proxyFactory;

    public JDKDynamicProxy(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    @Override
    public Object getProxy() {
        Object targetSource = proxyFactory.getTargetSource();
        return Proxy.newProxyInstance(targetSource.getClass().getClassLoader()
                ,targetSource.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object targetSource = this.proxyFactory.getTargetSource();
        //获取应用在此方法上的advise Interceptor列表
        List<Object> chain = this.proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(method,targetSource.getClass());
        MethodInvocation methodInvocation = new MethodInvocation(proxy,method,
                targetSource,targetSource.getClass(),args,chain);
        return methodInvocation.process();
    }
}
