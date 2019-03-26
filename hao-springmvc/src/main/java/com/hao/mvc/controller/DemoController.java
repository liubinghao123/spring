package com.hao.mvc.controller;

import com.hao.annotion.Autowired;
import com.hao.annotion.Controller;
import com.hao.annotion.RequestMapping;
import com.hao.annotion.RequestParams;
import com.hao.mvc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Keeper on 2019-03-26
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test1")
    public String test1(HttpServletRequest resquest, HttpServletResponse response,@RequestParams(value = "name") String[] name){
       return name[0] + "" + name[1]+ "同学你好";
    }
}
