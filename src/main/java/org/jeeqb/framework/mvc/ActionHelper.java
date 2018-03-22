package org.jeeqb.framework.mvc;

import org.jeeqb.framework.core.ClassHelper;
import org.jeeqb.framework.mvc.annotation.Action;
import org.jeeqb.framework.mvc.annotation.Request;
import org.jeeqb.framework.util.ArrayUtil;
import org.jeeqb.framework.util.CollectionUtil;
import org.jeeqb.framework.util.StringUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化Action 配置
 *
 * Created by rocky on 2018/3/22.
 */
public class ActionHelper {

    /**
     * Action Map (http请求与Action方法的映射)
     */
    private final static Map<Requester,Handler> actionMap = new LinkedHashMap<>();

    static {
        // 获取所有 Action 类
        List<Class<?>> actionClassList = ClassHelper.getClassListByAnnotation(Action.class);
        if (CollectionUtil.isNotEmpty(actionClassList)) {
            // 定义两个 Action Map
            Map<Requester, Handler> commonActionMap = new HashMap<Requester, Handler>(); // 存放普通 Action Map
            Map<Requester, Handler> regexpActionMap = new HashMap<Requester, Handler>(); // 存放带有正则表达式的 Action Map
            // 遍历 Action 类
            for (Class<?> actionClass : actionClassList) {
                // 获取并遍历该 Action 类中所有的方法
                Method[] actionMethods = actionClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(actionMethods)) {
                    for (Method actionMethod : actionMethods) {
                        // 处理 Action 方法
                        handleActionMethod(actionClass, actionMethod, commonActionMap, regexpActionMap);
                    }
                }
            }
            // 初始化最终的 Action Map（将 Common 放在 Regexp 前面）
            actionMap.putAll(commonActionMap);
            actionMap.putAll(regexpActionMap);
        }
    }

    private static void handleActionMethod(Class<?> actionClass, Method actionMethod, Map<Requester, Handler> commonActionMap, Map<Requester, Handler> regexpActionMap) {
        // 判断当前 Action 方法是否带有 Request 注解
        if (actionMethod.isAnnotationPresent(Request.GET.class)) {
            String requestPath = actionMethod.getAnnotation(Request.GET.class).value();
            putActionMap("GET", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.POST.class)) {
            String requestPath = actionMethod.getAnnotation(Request.POST.class).value();
            putActionMap("POST", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.PUT.class)) {
            String requestPath = actionMethod.getAnnotation(Request.PUT.class).value();
            putActionMap("PUT", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.DELETE.class)) {
            String requestPath = actionMethod.getAnnotation(Request.DELETE.class).value();
            putActionMap("DELETE", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        }
    }

    private static void putActionMap(String requestMethod, String requestPath, Class<?> actionClass, Method actionMethod, Map<Requester, Handler> commonActionMap, Map<Requester, Handler> regexpActionMap) {
        // 判断 Request Path 中是否带有占位符
        if (requestPath.matches(".+\\{\\w+\\}.*")) {
            // 将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
            requestPath = StringUtil.replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
            // 将 Requester 与 Handler 放入 Regexp Action Map 中
            regexpActionMap.put(new Requester(requestMethod, requestPath), new Handler(actionClass, actionMethod));
        } else {
            // 将 Requester 与 Handler 放入 Common Action Map 中
            commonActionMap.put(new Requester(requestMethod, requestPath), new Handler(actionClass, actionMethod));
        }
    }

    /**
     * 获取 Action Map
     */
    public static Map<Requester, Handler> getActionMap() {
        return actionMap;
    }


}
