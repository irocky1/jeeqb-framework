package org.jeeqb.framework.mvc.impl;

import org.jeeqb.framework.JEEQB;
import org.jeeqb.framework.mvc.HandlerExceptionResolver;
import org.jeeqb.framework.mvc.fault.AuthcException;
import org.jeeqb.framework.mvc.fault.AuthzException;
import org.jeeqb.framework.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认的handler异常解析器
 *
 * Created by rocky on 2018/3/22.
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

    private final static Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);

    @Override
    public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        Throwable cause = e.getCause();
        if(null == cause){
            logger.error(e.getMessage(),e);
            return;
        }

        if(cause instanceof AuthcException){
            if(WebUtil.isAJAX(request)){
                //403
                WebUtil.sendError(HttpServletResponse.SC_FORBIDDEN,"",response);
            }else  {
                //redirect
                WebUtil.redirectRequest(JEEQB.HOME_PAGE,request,response);
            }
        }else if(cause instanceof AuthzException){
            //403
            WebUtil.sendError(HttpServletResponse.SC_FORBIDDEN,"",response);
        }else {
            //500
            WebUtil.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,cause.getMessage(),response);
        }
    }
}
