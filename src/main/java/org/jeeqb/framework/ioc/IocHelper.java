package org.jeeqb.framework.ioc;

import org.jeeqb.framework.core.ClassHelper;
import org.jeeqb.framework.core.fault.InitializationError;
import org.jeeqb.framework.ioc.annotation.Impl;
import org.jeeqb.framework.ioc.annotation.Inject;
import org.jeeqb.framework.util.ArrayUtil;
import org.jeeqb.framework.util.CollectionUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * IOC 容器
 * Created by rocky on 2018/3/19.
 */
public class IocHelper {

    static {
        try {
            /**
             * 获取遍历所有的Bean类，
             */
            Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 获取 Bean 类与 Bean 实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取 Bean 类中所有的字段（不包括父类中的方法）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    // 遍历所有的 Bean 字段
                    for (Field beanField : beanFields) {
                        // 判断当前 Bean 字段是否带有 Inject 注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 获取 Bean 字段对应的接口
                            Class<?> interfaceClass = beanField.getType();
                            // 获取 Bean 字段对应的实现类
                            Class<?> implementClass = findImplementClass(interfaceClass);
                            // 若存在实现类，则执行以下代码
                            if (implementClass != null) {
                                // 从 Bean Map 中获取该实现类对应的实现类实例
                                Object implementInstance = beanMap.get(implementClass);
                                // 设置该 Bean 字段的值
                                if (implementInstance != null) {
                                    beanField.setAccessible(true); // 将字段设置为 public
                                    beanField.set(beanInstance, implementInstance); // 设置字段初始值
                                } else {
                                    throw new InitializationError("依赖注入失败！类名：" + beanClass.getSimpleName() + "，字段名：" + interfaceClass.getSimpleName());
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new InitializationError("初始化IOC容器时出错！",e);
        }
    }

    /**
     * 查询实现类
     * @param interfaceClass
     * @return
     */
    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        /**
         * 判断接口是否标注了 Impl 注解
         */
        if(interfaceClass.isAnnotationPresent(Impl.class)){
            //获取强制指定的实现类
            implementClass = interfaceClass.getAnnotation(Impl.class).value();
        }else {
            //获取该接口的所有实现类
            List<Class<?>> implementClassList =  ClassHelper.getClassListBySuper(interfaceClass);
            if(CollectionUtil.isNotEmpty(implementClassList)){
                //默认取第一个实现类
                implementClass = implementClassList.get(0);
            }
        }
        return implementClass;
    }

}
