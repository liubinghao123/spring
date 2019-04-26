package com.hao.framework.aop.framework;

/**
 * Created by Keeper on 2019-04-25
 */
public class CglibProxy implements AopProxy {
    private ProxyFactory proxyFactory;

    public CglibProxy(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    @Override
    public Object getProxy() {
        return null;
    }
}
