package com.hao.framework.web.servlet;

import java.io.File;
import java.util.Locale;

/**
 * Created by Keeper on 2019-04-18
 */
public class ViewResolve {
    private final String DEFAULT_FILE_SUFFIX = ".html";
    private File rootFile;

    public ViewResolve(File rootFile) {
        this.rootFile = rootFile;
    }

    public View resvolerViewName(String viewName, Locale locale){
        if(viewName == null || "".equals(viewName.trim()))return null;
        viewName = viewName.endsWith(DEFAULT_FILE_SUFFIX) ? viewName : viewName + DEFAULT_FILE_SUFFIX;
        File file = new File((rootFile.getPath() + "/" + viewName).replaceAll("/+","/"));
        if(file.exists()){
            return new View(file);
        }
        return null;
    }
}
