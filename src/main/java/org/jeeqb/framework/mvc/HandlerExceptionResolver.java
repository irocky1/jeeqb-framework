package org.jeeqb.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Handler 异常解析器
 *
 * Created by rocky on 2018/3/22.
 */
public interface HandlerExceptionResolver {

    /**
     * 解析异常
     *
     * @param request
     * @param response
     * @param e
     */
    void resolveHandlerException(HttpServletRequest request, HttpServletResponse response,Exception e);

}
