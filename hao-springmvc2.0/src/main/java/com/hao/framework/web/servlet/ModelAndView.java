package com.hao.framework.web.servlet;

import lombok.Data;

import java.util.Map;

/**
 * Created by Keeper on 2019-04-18
 */
@Data
public class ModelAndView {
    private String view;
    private Map<String,Object> modelMap;

    public ModelAndView(String view, Map<String, Object> modelMap) {
        this.view = view;
        this.modelMap = modelMap;
    }

    public ModelAndView(String view) {
        this.view = view;
    }
}
