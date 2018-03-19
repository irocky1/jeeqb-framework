package org.jeeqb.framework.plugin;

import org.jeeqb.framework.aop.proxy.Proxy;

import java.util.List;

/**
 * 插件代理
 * Created by rocky on 2018/3/19.
 */
public abstract class PluginProxy implements Proxy{

    public abstract List<Class<?>> getTargetClassList();

}
