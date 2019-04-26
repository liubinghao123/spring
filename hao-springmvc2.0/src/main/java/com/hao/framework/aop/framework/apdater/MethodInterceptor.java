package com.hao.framework.aop.framework.apdater;

import com.hao.framework.aop.framework.MethodInvocation;

/**
 * Created by Keeper on 2019-04-26
 */
public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
