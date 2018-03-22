package org.jeeqb.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图解析器
 * Created by rocky on 2018/3/21.
 */
public interface ViewResolver {

    /**
     * 解析视图
     * @param request
     * @param response
     * @param actionResult Action方法返回数据
     */
    void resolve(HttpServletRequest request, HttpServletResponse response,Object actionResult);

}
