package com.hao.framework.aop.framework;

import java.util.regex.Pattern;

/**
 * Created by Keeper on 2019-04-25
 */
public class JdkRegexpMethodPointcut implements Pointcut{
    private Pattern pattern;

    public JdkRegexpMethodPointcut(Pattern pattern) {
        this.pattern = pattern;
    }
}
