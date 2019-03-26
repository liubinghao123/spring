package com.hao.mvc.controller;

import com.hao.annotion.Controller;
import com.hao.annotion.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Keeper on 2019-03-26
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @RequestMapping("/test1")
    public String test1(HttpServletRequest resquest, HttpServletResponse response,String name){
       return name + "同学你好";
    }
}
