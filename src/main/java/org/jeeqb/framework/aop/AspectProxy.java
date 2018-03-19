package org.jeeqb.framework.aop;

import org.jeeqb.framework.aop.proxy.Proxy;
import org.jeeqb.framework.aop.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * Created by rocky on 2018/3/19.
 */
public abstract class AspectProxy implements Proxy{

    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);


    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            logger.error("AOP 异常", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return null;
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {

    }

    public void before(Class<?> cls, Method method, Object[] params) {

    }

    public void end() {
        logger.info("proxy.end");
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params){
        return true;
    }

    public void begin() {
        logger.info("proxy.begin");
    }


}
