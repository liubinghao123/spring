package com.hao.framework.aop.framework.apdater;

import com.hao.framework.aop.framework.BeforeAdvise;
import com.hao.framework.aop.framework.MethodInvocation;

/**
 * Created by Keeper on 2019-04-26
 */
public class MethodBeforeInterceptor implements MethodInterceptor {
    private BeforeAdvise advise;

    public MethodBeforeInterceptor(BeforeAdvise advise) {
        this.advise = advise;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advise.before(invocation.getMethod(),invocation.getArgs(),invocation.getTarget());
        return invocation.process();
    }
}
