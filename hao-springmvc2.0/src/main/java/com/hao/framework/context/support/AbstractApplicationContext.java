package com.hao.framework.context.support;

import com.hao.framework.beans.factory.DefaultListableBeanFactory;
import com.hao.framework.context.ApplicationContext;

/**
 * Created by Keeper on 2019-04-12
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {
   public void refresh() throws Exception{
      //加载配置文件，将bean信息注册到ioc容器中
      loadBeanDefinitions(this);
   }

   public abstract void loadBeanDefinitions(DefaultListableBeanFactory factory);
}
