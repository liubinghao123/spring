package com.hao.test;

import com.hao.framework.stereotype.Autowired;
import com.hao.framework.stereotype.Controller;

/**
 * Created by Keeper on 2019-04-18
 */
@Controller
public class B {
    @Autowired
    private A a;
}
