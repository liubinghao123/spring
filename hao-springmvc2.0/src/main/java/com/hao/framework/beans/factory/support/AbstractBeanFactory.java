package com.hao.framework.beans.factory.support;

import com.hao.framework.beans.BeanWrapper;
import com.hao.framework.beans.BeanWrapperImpl;
import com.hao.framework.beans.config.BeanDefinition;
import com.hao.framework.beans.factory.BeanFactory;
import com.hao.framework.beans.factory.DefaultListableBeanFactory;
import com.hao.framework.beans.factory.ObjectFactory;
import com.hao.framework.stereotype.Autowired;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Keeper on 2019-04-12
 */
public abstract class AbstractBeanFactory implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, RootBeanDefinition> mergedBeanDefinition = new ConcurrentHashMap<>();
    private Map<String,BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    private Map<String,Object> singleObjects = new ConcurrentHashMap<>();
    private Map<String,Object> earlySingleObejcts = new ConcurrentHashMap<>();
    private Map<String, ObjectFactory<?>> singleFactories = new ConcurrentHashMap<>();


    protected void createBean(DefaultListableBeanFactory defaultListableBeanFactory)throws Exception{
        for(Map.Entry<String, BeanDefinition> entry : defaultListableBeanFactory.getBeanDefinitionMap().entrySet()){
            BeanDefinition beanDefinition = entry.getValue();
            if(!beanDefinition.isLayInit()){
                createBean(beanDefinition);
            }
        }
    }
    protected Object createBean(BeanDefinition beanDefinition)throws Exception {
        //创建BeanDefinition实例
        BeanWrapper beanWrapper =  null;
        if(factoryBeanInstanceCache.containsKey(beanDefinition.getFactoryBeanName())){
            beanWrapper = factoryBeanInstanceCache.get(beanDefinition.getFactoryBeanName());
        }else{
            beanWrapper = doCreateBean(beanDefinition);
            final Object wrapperInstance = beanWrapper.getWrapperInstance();
            factoryBeanInstanceCache.put(beanDefinition.getFactoryBeanName(),beanWrapper);
            addSingletonFactories(beanDefinition.getFactoryBeanName(),()->{
                return wrapperInstance;
            });
        }
        //依赖注入
        populateBean(beanDefinition.getFactoryBeanName(),beanDefinition,beanWrapper);
        return beanWrapper.getWrapperInstance();
    }

    private BeanWrapper doCreateBean(BeanDefinition beanDefinition)throws Exception {
        //创建BeanWrapper实例
        return createBeanInstance(beanDefinition);
    }

    private void populateBean(String beanName,BeanDefinition mbd,BeanWrapper beanWrapper) {
        Object wrapperInstance = beanWrapper.getWrapperInstance();
        Class<?> wrapperClass = beanWrapper.getWrapperClass();
        if(wrapperInstance == null)return;
        Field[] fields = wrapperClass.getDeclaredFields();
        for(Field field : fields){
            if(!field.isAnnotationPresent(Autowired.class))continue;
            field.setAccessible(true);
            try {
                field.set(wrapperInstance,getBean(firstToLowerCase(field.getType().getSimpleName())));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private BeanWrapper createBeanInstance(BeanDefinition beanDefinition)throws Exception {
        return instantiateBean(beanDefinition);
    }

    private BeanWrapper instantiateBean(BeanDefinition beanDefinition) throws Exception {
        Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
        Object instance = clazz.newInstance();

        return new BeanWrapperImpl(instance,clazz);
    }

    /**
     * 从三级缓存中获取bean实例
     * @param beanName
     * @return
     */
    public Object getSingleton(String beanName){
       Object singletonObejct = this.singleObjects.get(beanName);
       if(singletonObejct == null){
            synchronized (this.singleObjects){
                singletonObejct =  this.earlySingleObejcts.get(beanName);
                if(singletonObejct == null){
                   ObjectFactory<?> objectFactory = this.singleFactories.get(beanName);
                   if(objectFactory != null){
                       singletonObejct = objectFactory.getObject();
                       this.earlySingleObejcts.put(beanName,singletonObejct);
                       this.singleFactories.remove(beanName);
                   }
                }
            }
       }
       return singletonObejct;
    }

    public Object addSingleton(String beanName,ObjectFactory<?> objectFactory){
        Object singletonObject = objectFactory.getObject();
        if(singletonObject != null){
            synchronized (this.singleObjects){
                this.earlySingleObejcts.remove(beanName);
                this.singleFactories.remove(beanName);
                this.singleObjects.put(beanName,singletonObject);
            }
        }
        return singletonObject;
    }

    public void addSingletonFactories(String beanName,ObjectFactory<?> objectFactory){
       synchronized (this.singleObjects){
           if(!this.singleObjects.containsKey(beanName)){
               this.singleFactories.put(beanName,objectFactory);
               this.earlySingleObejcts.remove(beanName);
           }
       }
    }
    private String firstToLowerCase(String name){
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }
}
