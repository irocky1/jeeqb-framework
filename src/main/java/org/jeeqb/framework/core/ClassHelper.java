package org.jeeqb.framework.core;

import org.jeeqb.framework.InstanceContext;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 根据条件获取相关类
 * Created by rocky on 2017/12/28.
 */
public class ClassHelper {

    /**
     * 获取基础包名
     */
    private static final String backPage = ConfigHelper.getString("app.base_package");

    /**
     * 获取类扫描器
     */
    private static ClassScanner classScanner = InstanceContext.getClassScanner();

    /**
     * 获取基础包名所有类
     * @return
     */
    public static List<Class<?>> getClassList(){
        return classScanner.getClassList(backPage);
    }

    /**
     * 获取基础包中指定注解的相关类
     * @param annotationClass
     * @return
     */
    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass){
        return classScanner.getClassListByAnnotation(backPage,annotationClass);
    }

    /**
     * 获取基础包名中的指定父类或接口
     * @param superClass
     * @return
     */
    public static List<Class<?>> getClassListBySuper(Class<?> superClass){
        return classScanner.getClassListBySuper(backPage,superClass);
    }
    
}
