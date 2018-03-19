package org.jeeqb.framework.aop.proxy;

/**
 * 代理接口
 *
 * Created by rocky on 2018/3/19.
 */
public interface Proxy {


    /**
     * 执行链式代理
     *
     * @param proxyChain  代理链
     * @return  目标返回值
     * @throws Throwable 异常
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
