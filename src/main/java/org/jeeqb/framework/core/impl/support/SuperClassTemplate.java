package org.jeeqb.framework.core.impl.support;

/**
 * 获取父类的类模板
 * Created by rocky on 2017/12/29.
 */
public abstract class SuperClassTemplate extends ClassTemplate{

    protected final Class<?> superClass;

    protected SuperClassTemplate(String packageName,Class<?> superClass){
        super(packageName);
        this.superClass = superClass;
    }

}
