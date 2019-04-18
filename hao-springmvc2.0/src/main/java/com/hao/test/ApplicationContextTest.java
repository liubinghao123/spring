package com.hao.test;

import com.hao.framework.context.AnnotationConfigApplicationContext;
import com.hao.framework.context.ApplicationContext;

import java.lang.reflect.Field;

/**
 * Created by Keeper on 2019-04-15
 */
public class ApplicationContextTest {
    public static void main(String[] args) throws IllegalAccessException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("application.properties");
        Object object = applicationContext.getBean("a");
        Field fileB = object.getClass().getDeclaredFields()[0];
        fileB.setAccessible(true);
        Object b  = fileB.get(object);

        Field fileA = b.getClass().getDeclaredFields()[0];
        fileA.setAccessible(true);
        Object a =fileA.get(b);
        System.out.println(object);
        System.out.println(b);
        System.out.println(a);
    }
}
