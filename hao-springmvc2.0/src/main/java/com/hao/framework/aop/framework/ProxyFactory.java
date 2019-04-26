package com.hao.framework.aop.framework;

import com.hao.framework.aop.framework.apdater.AfterReturningAdviceInterceptor;
import com.hao.framework.aop.framework.apdater.MethodBeforeInterceptor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Keeper on 2019-04-25
 * 代理对象创建工厂
 */
@Data
public class ProxyFactory {
    private Advisor[] advisors;
    private Object targetSource;
    private Map<Method,List<Object>> methodCache = new HashMap<>();

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if(targetSource.getClass().getInterfaces().length > 0){
            return new JDKDynamicProxy(this);
        }
        return new CglibProxy(this);
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> aClass) {
        if(methodCache.containsKey(method)){
            return methodCache.get(method);
        }
        List<Object> interceptors = new ArrayList<>();
        for(Advisor advisor : advisors){
            if(advisor instanceof PointcutAdvisor){
                Advise advise =  advisor.getAdvise();
                if(advise instanceof BeforeAdvise){
                    interceptors.add(new MethodBeforeInterceptor((BeforeAdvise) advise));
                }
                if(advise instanceof  AfterAdvise){
                    interceptors.add(new AfterReturningAdviceInterceptor((AfterAdvise) advise));
                }
            }
        }
        methodCache.put(method,interceptors);
        return interceptors;
    }
}
