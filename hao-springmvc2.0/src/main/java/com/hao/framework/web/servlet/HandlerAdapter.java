package com.hao.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Keeper on 2019-04-18
 */
public class HandlerAdapter {

    public HandlerAdapter() {

    }
    public  boolean support(Object handler){
        return (handler instanceof HandlerMapping);
    }

    public  ModelAndView handle(HttpServletRequest req, HttpServletResponse resp,Object handler){
       if(this.support(handler)){
           HandlerMapping handlerMapping = (HandlerMapping) handler;
           Object instance = handlerMapping.getController();
           Method method = handlerMapping.getMethod();
           //TODO参数设置
           try {
               Object result = method.invoke(instance);
               //如果是ModelAndView对象就直接返回
               if(result instanceof ModelAndView){
                   return (ModelAndView) result;
               }
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           }
       }
       return null;
    }

}
