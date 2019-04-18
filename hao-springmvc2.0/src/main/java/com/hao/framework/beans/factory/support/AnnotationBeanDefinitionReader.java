package com.hao.framework.beans.factory.support;

import com.hao.framework.beans.config.BeanDefinition;
import com.hao.framework.beans.factory.DefaultListableBeanFactory;
import com.hao.framework.io.Resource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Keeper on 2019-04-12
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private final String SCANN_PACKAGE = "scan_package";
    private Properties config = new Properties();
    private List<String> classNames = new ArrayList<>();

    public AnnotationBeanDefinitionReader(DefaultListableBeanFactory beanFactory) {
        this.registry = beanFactory;
    }

    public int registerBeanDefinitions(List<BeanDefinition> beanDefinitions) throws Exception{
        return doRegisterBeanDefinitions(beanDefinitions);
    }

    private int doRegisterBeanDefinitions(List<BeanDefinition> beanDefinitions) throws Exception {
        if(beanDefinitions.size() == 0)return 0;
        for(BeanDefinition beanDefinition : beanDefinitions){
            this.registry.registerBeanDefinition(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
        return beanDefinitions.size();
    }

    @Override
    public int loadBeanDefinitions(Resource resource) {
       return doLoadBeanDefinitions(resource);
    }

    public int doLoadBeanDefinitions(Resource resource){
        try {
            config.load(resource.getInputStream());
            this.doScanner(config.getProperty(SCANN_PACKAGE));

            if(classNames.size() == 0) return 0;
            List<BeanDefinition> beanDefinitions = new ArrayList<>();
            for(String className : classNames){
                Class<?> clazz = Class.forName(className);
                RootBeanDefinition beanDefinition = new RootBeanDefinition();
                beanDefinition.setBeanClassName(className);
                beanDefinition.setFactoryBeanName(firstToLowerCase(clazz.getSimpleName()));

                beanDefinitions.add(beanDefinition);
            }
            return registerBeanDefinitions(beanDefinitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void doScanner(String packageName) {
        if(packageName == null || "".equals(packageName))return;
        URL url = this.getClass().getResource("/" + packageName.replaceAll("\\.","/"));

        File file = new File(url.getFile());
        if(!file.exists()) return;
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                doScanner(packageName + "." + f.getName());
            }else{
                classNames.add(packageName + "." + f.getName().replace(".class",""));
            }
        }
    }

    private String firstToLowerCase(String name){
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    public Properties getConfig() {
        return config;
    }
}
