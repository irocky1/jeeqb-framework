package org.jeeqb.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handler 调用器
 *
 * Created by rocky on 2018/3/21.
 */
public interface HandlerInvoker {

    /**
     * 调用 Handler
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    void invoke(HttpServletRequest request, HttpServletResponse response,Handler handler) throws Exception;

}
