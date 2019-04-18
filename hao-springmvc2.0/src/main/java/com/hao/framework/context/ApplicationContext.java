package com.hao.framework.context;

import com.hao.framework.beans.factory.BeanFactory;

/**
 * Created by Keeper on 2019-04-12
 */
public interface ApplicationContext extends BeanFactory {
    String getId();
    String getDisplayName();
    String getApplicationName();
}
