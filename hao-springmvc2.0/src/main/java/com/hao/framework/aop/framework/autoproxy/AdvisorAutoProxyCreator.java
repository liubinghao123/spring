package com.hao.framework.aop.framework.autoproxy;

import com.hao.framework.aop.framework.Advisor;
import com.hao.framework.aop.framework.ProxyFactory;
import com.hao.framework.beans.factory.config.BeanPostProcessor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keeper on 2019-04-23
 */
@Data
public class AdvisorAutoProxyCreator implements BeanPostProcessor {
    private List<Advisor> advisorList =  new ArrayList<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        //获取当前bean的advise
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean,beanName);
        //如果没有advise就直接返回当前对象
        if(specificInterceptors!=null && specificInterceptors.length == 0){
            return bean;
        }
        return createProxy(bean.getClass(),beanName,specificInterceptors,bean);
    }
    //创建的代理对象
    private Object createProxy(Class<?> clazz, String beanName, Object[] specificInterceptors,Object targetSource) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setAdvisors(specificInterceptors);
        proxyFactory.setTargetSource(targetSource);

        return proxyFactory.getProxy();
    }

    public Object[] getAdvicesAndAdvisorsForBean(Object bean,String beanName){
        //TODO 判断是否作用在pointcut中
        return advisorList.toArray();
    }
}
