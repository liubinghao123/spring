package com.hao.context.support;

import com.hao.beans.BeanWrapper;
import com.hao.beans.BeanWrapperImpl;
import com.hao.beans.config.BeanDefinition;
import com.hao.beans.factory.DefaultListableBeanFactory;
import com.hao.context.ApplicationContext;

import java.util.Map;

/**
 * Created by Keeper on 2019-04-12
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {
   public void refresh() throws Exception{
      //加载配置文件，将bean信息注册到ioc容器中
      DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
      loadBeanDefinitions(defaultListableBeanFactory);
      //创建非延迟加载的beanDefinition实例
      createBean(defaultListableBeanFactory);

   }

   protected void createBean(DefaultListableBeanFactory defaultListableBeanFactory)throws Exception{
      for(Map.Entry<String, BeanDefinition> entry : defaultListableBeanFactory.getBeanDefinitionMap().entrySet()){
         BeanDefinition beanDefinition = entry.getValue();
         if(!beanDefinition.isLayInit()){
            //创建BeanDefinition实例
            BeanWrapper beanWrapper =  doCreateBean(beanDefinition);
            //依赖注入
            populateBean();
         }
      }
   }

   private BeanWrapper doCreateBean(BeanDefinition beanDefinition)throws Exception {
      //创建BeanDefinition实例
      return createBeanInstance(beanDefinition);
   }

   private void populateBean() {
   }

   private BeanWrapper createBeanInstance(BeanDefinition beanDefinition)throws Exception {
     return instantiateBean(beanDefinition);
   }

   private BeanWrapper instantiateBean(BeanDefinition beanDefinition) throws Exception {
      Class<?> clzz = Class.forName(beanDefinition.getBeanClassName());
      Object instance = clzz.newInstance();
      return new BeanWrapperImpl(instance,clzz);
   }

   public abstract void loadBeanDefinitions(DefaultListableBeanFactory factory);
}
