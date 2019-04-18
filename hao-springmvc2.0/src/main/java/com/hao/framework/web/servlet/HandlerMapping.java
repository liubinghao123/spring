package com.hao.framework.web.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by Keeper on 2019-04-18
 */
@Data
public class HandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern;

    public HandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }
}
