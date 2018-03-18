package org.jeeqb.framework.core.impl;

import org.jeeqb.framework.core.ClassScanner;
import org.jeeqb.framework.core.impl.support.AnnotationClassTemplate;
import org.jeeqb.framework.core.impl.support.ClassTemplate;
import org.jeeqb.framework.core.impl.support.SuperClassTemplate;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器默认实现 
 * Created by rocky on 2017/12/28.
 */
public class DefaultClassScanner implements ClassScanner {

    @Override
    public List<Class<?>> getClassList(String packageName) {
        return new ClassTemplate(packageName){
            protected boolean checkAddClass(Class<?> cls) {
                String className = cls.getName();
                String pkgName = className.substring(0,className.lastIndexOf("."));
                return pkgName.startsWith(packageName);
            }
        }.getClassList();
    }

    @Override
    public List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return new AnnotationClassTemplate(packageName,annotationClass){
            @Override
            protected boolean checkAddClass(Class<?> cls) {
                return cls.isAnnotationPresent(annotationClass);
            }
        }.getClassList();
    }

    @Override
    public List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass) {
        return new SuperClassTemplate(packageName,superClass){
            @Override
            protected boolean checkAddClass(Class<?> cls) {
                return superClass.isAssignableFrom(cls) && !superClass.equals(cls);
            }
        }.getClassList();
    }
}
