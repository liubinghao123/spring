package com.hao.beans.factory.support;

import com.hao.core.io.Resource;

/**
 * Created by Keeper on 2019-04-12
 */
public interface BeanDefinitionReader {
   int loadBeanDefinitions(String location);
   int loadBeanDefinitions(String... locations);

   int loadBeanDefinitions(Resource resource);
   int loadBeanDefinitions(Resource... resource);
}
