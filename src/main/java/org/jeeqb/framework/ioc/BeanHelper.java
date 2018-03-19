package org.jeeqb.framework.ioc;

import org.jeeqb.framework.aop.annotation.Aspect;
import org.jeeqb.framework.core.ClassHelper;
import org.jeeqb.framework.core.fault.InitializationError;
import org.jeeqb.framework.ioc.annotation.Bean;
import org.jeeqb.framework.mvc.annotation.Action;
import org.jeeqb.framework.tx.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化相关的Bean类
 * Created by rocky on 2018/1/1.
 */
public class BeanHelper {

    // Bean类 -》 Bean实例
    private static final Map<Class<?>,Object> beanMap = new HashMap<Class<?>, Object>();

    static {
        try{
            //获取应用包路径下所有的类
            List<Class<?>> classList =  ClassHelper.getClassList();
            for(Class<?> cls : classList){
                //处理带有 Bean/Serivce/Action/Aspect 注解的类
                if(cls.isAnnotationPresent(Bean.class) ||
                        cls.isAnnotationPresent(Action.class) ||
                        cls.isAnnotationPresent(Service.class) ||
                        cls.isAnnotationPresent(Aspect.class)){
                    //创建Bean实现
                    Object beanInstance = cls.newInstance();
                    //存入map中
                    beanMap.put(cls,beanInstance);
                }
            }
        }catch(Exception e){
            throw new InitializationError("初始化BeanHelper出错！",e);
        }
    }

    /**
     * 设置bean实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?> cls, Object obj) {
        beanMap.put(cls,obj);
    }

    /**
     * 获取Bean Map
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    /**
     * 获取Bean 实例
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if(!beanMap.containsKey(cls)){
            throw new RuntimeException("无法根据类名获取类实例"+cls);
        }
        return  (T) beanMap.get(cls);
    }

}
