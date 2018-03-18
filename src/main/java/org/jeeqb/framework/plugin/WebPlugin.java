package org.jeeqb.framework.plugin;

import javax.servlet.ServletContext;

/**
 * Created by rocky on 2017/12/27.
 */
public abstract class WebPlugin implements Plugin{

    public void init() {

    }
    public void destory() {

    }

    public abstract void register(ServletContext servletContext);

}
