package org.jeeqb.framework.tx;

import org.jeeqb.framework.aop.proxy.Proxy;
import org.jeeqb.framework.aop.proxy.ProxyChain;
import org.jeeqb.framework.dao.DatabaseHelper;
import org.jeeqb.framework.tx.annotation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 事务代理
 * Created by rocky on 2018/3/19.
 */
public class TransactionProxy implements Proxy {

    private final static Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    /**
     * 定义线程局部变量，用于保存当前线程中是否进行了事务处理，默认为false-》未处理
     */
    private final static ThreadLocal<Boolean> flagContainer = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;
        //判断当前线程是否进行了事务处理
        boolean flag = flagContainer.get();
        //获取目标方法
        Method method = proxyChain.getTargetMethod();

        //若当前线程未进行事务处理，且在目标方法上定义了Transaction注解，则说明该方法需要进行事务处理
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            //设置当前线程已进行事务处理
            flagContainer.set(true);
            /**
             * 1。开启事务
             * 2。执行目标方法
             * 3。提交事务
             * 4。如果异常则回滚事务
             */
            try{
                //开启事务
                DatabaseHelper.beginTransaction();
                logger.debug("[JEEQB] begin transaction.");
                //执行目标方法
                result = proxyChain.doProxyChain();
                //提交事务
                DatabaseHelper.commitTransaction();
                logger.debug("[JEEQB] commit transaction.");
            }catch (Exception e){
                //回滚事务
                DatabaseHelper.rollbackTransaction();
                logger.debug("[JEEQB] rollback transaction.");
                throw  e;
            }finally {
                //移除线程变量
                flagContainer.remove();
            }


        }else {
            //执行目标方法
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
