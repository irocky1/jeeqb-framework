package org.jeeqb.framework.mvc;

/**
 * 处理器映射
 * Created by rocky on 2018/3/21.
 */
public interface HandlerMapping {

    /**
     * 获取 Handler处理器
     * @param actionMethod  请求方法
     * @param requestPath  请求路径
     * @return
     */
    Handler getHandler(String actionMethod,String requestPath);

}
