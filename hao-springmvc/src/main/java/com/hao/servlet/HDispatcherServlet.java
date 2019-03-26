package com.hao.servlet;

import com.hao.annotion.Autowired;
import com.hao.annotion.Controller;
import com.hao.annotion.RequestMapping;
import com.hao.annotion.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by Keeper on 2019-03-26
 * http请求核心调度器
 */
public class HDispatcherServlet extends HttpServlet {
    private static final String PROPERTIES_NAME = "application.properties";
    private Properties properties = new Properties();
    private List<String> classNames = new ArrayList<String>();
    private Map<String,Object> ioc = new HashMap<String,Object>();
    private List<HandlerMapping> mappings = new ArrayList<HandlerMapping>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1：加载配置文件，读取文件信息
        try {
            loadProperties(config);
            //2：扫描类
            doScanner(properties.getProperty("scanpackage"));
            //3：初始化类
            doInstance();
            //4：di注入
            doAutowired();
            //5：处理url与method的对应关系
            doHandlerMapping();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void doHandlerMapping() {
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object instance = entry.getValue();
            String baseUrl = "";
            if(!instance.getClass().isAnnotationPresent(Controller.class))continue;

            if(instance.getClass().isAnnotationPresent(RequestMapping.class)){
                baseUrl = instance.getClass().getAnnotation(RequestMapping.class).value();
            }
            Method[] methods = instance.getClass().getDeclaredMethods();
            for(Method method : methods){
                if(!method.isAnnotationPresent(RequestMapping.class))continue;
                String url = "/" + baseUrl + "/" + method.getAnnotation(RequestMapping.class).value();
                url = url.replaceAll("/+","/");

                HandlerMapping handlerMapping = new HandlerMapping();
                handlerMapping.setInstance(instance);
                handlerMapping.setUrl(url);
                handlerMapping.setMethod(method);
                mappings.add(handlerMapping);
            }
        }

    }
    //注入依赖
    private void doAutowired() throws IllegalAccessException {
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object instance = entry.getValue();
            Field[] fields = instance.getClass().getDeclaredFields();
            for(Field field : fields){
                if(field.isAnnotationPresent(Autowired.class)){
                    String required = field.getAnnotation(Autowired.class).required();
                    required = "".equals(required) ? firstToLowerCase(field.getName()) : required;
                    if(ioc.containsKey(required)){
                        field.setAccessible(true);
                        field.set(instance,ioc.get(required));
                    }
                }
            }
        }
    }
    //初始化类
    private void doInstance() throws Exception {
        if(classNames.size() > 0){
            for(String className : classNames){
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)){
                    String value = clazz.isAnnotationPresent(Controller.class) ? clazz.getAnnotation(Controller.class).value() : clazz.getAnnotation(Service.class).value();
                    value = "".equals(value) ? firstToLowerCase(clazz.getSimpleName()) : value;
                    if(ioc.containsKey(value))
                        throw new Exception("bean name 不允许重复，bean ：" + value);
                    ioc.put(value,clazz.newInstance());
                }
            }
        }
    }
    //扫描类
    private void doScanner(String packagePath) {
        URL url = this.getClass().getClassLoader().getResource("/" + packagePath.replaceAll("\\.","/"));
        File[] files = new File(url.getFile()).listFiles();
        for(File file : files){
            if(file.isDirectory()){
                doScanner(packagePath + "." + file.getName());
            }else{
                classNames.add(packagePath + "." +file.getName().replaceAll(".class",""));
            }
        }
    }

    //加载配置文件信息
    private void loadProperties(ServletConfig config) throws IOException {
        String propertiesFile = config.getInitParameter(PROPERTIES_NAME);
        properties.load(this.getClass().getResourceAsStream("/" + propertiesFile));
    }

    //请求处理
    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String requestUrl = req.getRequestURI();
            if(mappings.size() == 0) {
                resp.getWriter().write("404");
                return;
            }
            HandlerMapping targetMapping = null;
            for(HandlerMapping handlerMapping : mappings){
                if(requestUrl.equals(handlerMapping.getUrl())){
                    targetMapping = handlerMapping;
                    break;
                }
            }
            if(targetMapping == null) {
                resp.getWriter().write("404");
                return;
            }
            String name = req.getParameter("name");
            Object obj = targetMapping.getMethod().invoke(targetMapping.getInstance(),req,resp,name);
            resp.getWriter().write(obj.toString());
        }catch (Exception e ){
            resp.getWriter().write("505");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6：请求处理
        doDispatcher(req,resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    private String firstToLowerCase(String name){
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    public class HandlerMapping{
        private String url;
        private Method method;
        private Object instance;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Object getInstance() {
            return instance;
        }

        public void setInstance(Object instance) {
            this.instance = instance;
        }
    }
}