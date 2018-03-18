package org.jeeqb.framework.plugin;

/**
 * 插件接口
 * Created by rocky on 2017/12/27.
 */
public interface Plugin {

    /**
     * 插件初始化
     */
    void init();

    /**
     * 插件销毁
     */
    void destory();

}
