package org.jeeqb.framework.plugin;

import javax.servlet.ServletContext;

/**
 * 基于WEB的插件抽象实现，具备Plugin接口的所有功能
 * 可在子类中注册Servlet,Listener,Filter等
 *
 * Created by rocky on 2017/12/27.
 */
public abstract class WebPlugin implements Plugin{

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }

    public abstract void register(ServletContext servletContext);

}
