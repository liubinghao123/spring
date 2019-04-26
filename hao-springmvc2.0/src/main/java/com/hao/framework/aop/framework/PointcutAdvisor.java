package com.hao.framework.aop.framework;

import lombok.Data;

/**
 * Created by Keeper on 2019-04-25
 */
@Data
public class PointcutAdvisor implements Advisor{
    private Pointcut pointcut;
    private Advise advise;

    public PointcutAdvisor(Pointcut pointcut, Advise advise) {
        this.pointcut = pointcut;
        this.advise = advise;
    }

    @Override
    public Advise getAdvise() {
        return this.advise;
    }
}
