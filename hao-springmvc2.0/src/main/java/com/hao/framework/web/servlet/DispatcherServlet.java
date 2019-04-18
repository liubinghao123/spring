package com.hao.framework.web.servlet;

import com.hao.framework.beans.factory.DefaultListableBeanFactory;
import com.hao.framework.beans.factory.support.AnnotationBeanDefinitionReader;
import com.hao.framework.context.AnnotationConfigApplicationContext;
import com.hao.framework.context.ApplicationContext;
import com.hao.framework.stereotype.Controller;
import com.hao.framework.stereotype.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Keeper on 2019-04-18
 */
public class DispatcherServlet extends HttpServlet {
    private final String CONFIG_LOCATION = "contextConfigLocation";
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private Map<HandlerMapping,HandlerAdapter> handlerAdapterMap = new HashMap<>();
    private List<ViewResolve> resolves = new ArrayList<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doDispatcher(req,resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
       try {
           HandlerMapping handlerMapping = getHandler(req);
           Exception exception = null;
           HandlerExecutionChain mappedHandler = new HandlerExecutionChain();
           if(handlerMapping == null){
               resp.getWriter().write("404");
               return;
           }
           HandlerAdapter ha = this.handlerAdapterMap.get(handlerMapping);
           if(ha == null){
               resp.getWriter().write("404");
               return;
           }
           //TODO 1:参数注入  2：ModelAndView需根据不同的内容进行封装 3：实现自己的模板引擎，更加model参数替换占位符
           ModelAndView mv = ha.handle(req,resp,handlerMapping);
           processDispatchResult(req,resp,mappedHandler,mv,exception);
       } catch (Exception e) {
           e.printStackTrace();
           try {
               resp.getWriter().write("505");
           } catch (IOException e1) {
               e1.printStackTrace();
           }
       }
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, HandlerExecutionChain mappedHandler, ModelAndView mv, Exception exception) {
        if(mv == null || this.resolves.isEmpty())return;
        for(ViewResolve viewResolve : this.resolves){
            View view = viewResolve.resvolerViewName(mv.getView(),null);
            if(view != null){
                view.render(mv.getModelMap(),req,resp);
                return;
            }
        }
    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        String uri = req.getRequestURI();
        for(HandlerMapping handlerMapping : this.handlerMappings){
            Pattern pattern = handlerMapping.getPattern();
            Matcher matcher = pattern.matcher(uri);
            if(matcher.matches()){
               return handlerMapping;
            }
        }
        return null;
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(config.getInitParameter(CONFIG_LOCATION));
        initStrategies(applicationContext);
    }

    /**
     * 初始化九大组件
     * @param context
     */
    private void initStrategies(ApplicationContext context) {
        initMultipartResvoler(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
    }

    private void initHandlerAdapters(ApplicationContext context) {
        for(HandlerMapping handlerMapping : this.handlerMappings){
            this.handlerAdapterMap.put(handlerMapping,new HandlerAdapter());
        }
    }

    private void initThemeResolver(ApplicationContext context) {
    }

    private void initMultipartResvoler(ApplicationContext context) {
    }
    private void initLocaleResolver(ApplicationContext context){
        
    }
    private void initHandlerMappings(ApplicationContext context){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context;
        List<String> beanNames = defaultListableBeanFactory.getBeanDefinitionNames();
        if(beanNames.size() == 0)return ;
        for(String beanName : beanNames){
            Object controller = defaultListableBeanFactory.getBean(beanName);
            Class<?> clazz = controller.getClass();
            if(clazz.isAnnotationPresent(Controller.class)){
               String baseUrl = clazz.isAnnotationPresent(RequestMapping.class) ? clazz.getAnnotation(RequestMapping.class).value() : "";
               if(baseUrl == null || "".equals(baseUrl))continue;
               Method[] methods = clazz.getDeclaredMethods();
               for(Method method : methods){
                   if(method.isAnnotationPresent(RequestMapping.class)){
                       String methodUrl = method.getAnnotation(RequestMapping.class).value();

                       String regx = ("/" + baseUrl + "/" + methodUrl.replaceAll("\\*",".*")).replaceAll("/+","/");
                       Pattern pattern = Pattern.compile(regx);

                       this.handlerMappings.add(new HandlerMapping(controller,method,pattern));
                   }
               }
            }
        }
    }
    private void initHandlerExceptionResolvers(ApplicationContext context){
        
    }
    private void initViewResolvers(ApplicationContext context){
        AnnotationConfigApplicationContext configApplicationContext = (AnnotationConfigApplicationContext) context;
        AnnotationBeanDefinitionReader reader = (AnnotationBeanDefinitionReader) configApplicationContext.getReader();
        String templateDir = reader.getConfig().getProperty("template_dir");
        String filePath = this.getClass().getClassLoader().getResource(templateDir).getFile();
        File templateFile = new File(filePath);
        if(!templateFile.exists()){return;}
        File[] files = templateFile.listFiles();
        for(File file : files){
            if(file.isDirectory())continue;
            this.resolves.add(new ViewResolve(templateFile));
        }

    }
    


}
