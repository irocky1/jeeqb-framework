package org.jeeqb.framework.plugin;

import org.jeeqb.framework.InstanceContext;
import org.jeeqb.framework.JEEQB;
import org.jeeqb.framework.core.ClassScanner;
import org.jeeqb.framework.core.fault.InitializationError;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化插件
 *
 * Created by rocky on 2018/3/20.
 */
public class PluginHelper {

    /**
     * 插件列表
     */
    private static final List<Plugin> pluginList = new ArrayList<>();

    /**
     * 类扫描器
     */
    private static final ClassScanner classScanner = InstanceContext.getClassScanner();

    static {
        try {
            //获取所有插件列表
            List<Class<?>> pluginClassList = classScanner.getClassListBySuper(JEEQB.PLUGIN_PACKAGE, Plugin.class);
            for (Class<?> pluginClass : pluginClassList) {
                Plugin plugin = (Plugin) pluginClass.newInstance();
                plugin.init();
                pluginList.add(plugin);
            }
        }catch (Exception e){
            throw new InitializationError("初始化插件出错!",e);
        }
    }

    /**
     * 获取插件列表
     * @return
     */
    public static List<Plugin> getPluginList() {
        return pluginList;
    }
}
