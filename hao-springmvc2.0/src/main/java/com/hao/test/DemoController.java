package com.hao.test;

import com.hao.framework.stereotype.Controller;
import com.hao.framework.stereotype.RequestMapping;
import com.hao.framework.web.servlet.ModelAndView;

/**
 * Created by Keeper on 2019-04-15
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @RequestMapping("/test1")
    public ModelAndView test1(){
        return new ModelAndView("/first",null);
    }

}
