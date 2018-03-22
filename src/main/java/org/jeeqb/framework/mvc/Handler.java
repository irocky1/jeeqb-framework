package org.jeeqb.framework.mvc;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

/**
 * 处理器，封装Action相关信息
 * Created by rocky on 2018/3/21.
 */
public class Handler {

    private Class<?> actionClass;
    private Method actionMethod;
    private Matcher pathMatcher;

    public Handler(Class<?> actionClass, Method actionMethod) {
        this.actionClass = actionClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getActionClass() {
        return actionClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public Matcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(Matcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

}
