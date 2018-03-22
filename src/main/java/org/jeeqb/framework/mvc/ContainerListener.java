package org.jeeqb.framework.mvc;

import org.jeeqb.framework.HelperLoader;
import org.jeeqb.framework.JEEQB;
import org.jeeqb.framework.plugin.Plugin;
import org.jeeqb.framework.plugin.PluginHelper;
import org.jeeqb.framework.plugin.WebPlugin;
import org.jeeqb.framework.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 容器监听器
 * Created by rocky on 2018/3/21.
 */
@WebListener
public class ContainerListener implements ServletContextListener{

    /**
     * 当容器初始化时调用
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获取ServletContext
        ServletContext servletContext = sce.getServletContext();
        //init helper
        HelperLoader.init();

        //add servlet mapping
        addServletMapping(servletContext);

        //register web plugin
        registerWebPlugin(servletContext);
    }

    private void registerWebPlugin(ServletContext servletContext) {
        List<Plugin> pluginList = PluginHelper.getPluginList();
        for(Plugin plugin : pluginList){
            if(plugin instanceof WebPlugin){
                WebPlugin webPlugin = (WebPlugin) plugin;
                webPlugin.register(servletContext);
            }
        }
    }

    private void addServletMapping(ServletContext servletContext) {
        //Default Servlet mapping static resources
        registerDefaultServlet(servletContext);

        //JspServlet mapping jsp request
        registerJspServlet(servletContext);
    }

    private void registerJspServlet(ServletContext servletContext) {
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        String jspPath = JEEQB.JSP_PATH;
        if(StringUtil.isNotEmpty(jspPath)){
            jspServlet.addMapping(jspPath+"*");
        }
    }

    private void registerDefaultServlet(ServletContext servletContext) {
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
        String wwwPath = JEEQB.WWW_PATH;
        if(StringUtil.isNotEmpty(wwwPath)){
            defaultServlet.addMapping(wwwPath+"*");
        }
    }

    /**
     * 当容器销毁时调用
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //destory plugin
        destoryPlugin();
    }

    private void destoryPlugin() {
        List<Plugin> pluginList = PluginHelper.getPluginList();
        for(Plugin plugin : pluginList){
            plugin.destory();
        }
    }
}
