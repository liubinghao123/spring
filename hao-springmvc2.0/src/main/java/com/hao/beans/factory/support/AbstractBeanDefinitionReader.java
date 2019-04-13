package com.hao.beans.factory.support;

import com.hao.core.io.Resource;

import java.io.InputStream;

/**
 * Created by Keeper on 2019-04-12
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
   @Override
   public int loadBeanDefinitions(String location) {
      InputStream is = this.getClass().getClassLoader().getResourceAsStream("/" + location);
      Resource resource = new Resource();
      resource.setInputStream(is);
      return this.loadBeanDefinitions(resource);
   }

   @Override
   public int loadBeanDefinitions(String... locations) {
      int count = 0;
      if(locations == null) return count;
      for(String location : locations){
         count += loadBeanDefinitions(location);
      }
      return count;
   }

   @Override
   public int loadBeanDefinitions(Resource... resources) {
      int count = 0;
      if(resources == null) return count;
      for(Resource resource : resources){
         count += loadBeanDefinitions(resources);
      }
      return count;
   }
}
