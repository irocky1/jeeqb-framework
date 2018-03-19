package org.jeeqb.framework.aop;

import org.jeeqb.framework.InstanceContext;
import org.jeeqb.framework.JEEQB;
import org.jeeqb.framework.aop.annotation.Aspect;
import org.jeeqb.framework.aop.annotation.AspectOrder;
import org.jeeqb.framework.aop.proxy.Proxy;
import org.jeeqb.framework.aop.proxy.ProxyManger;
import org.jeeqb.framework.core.ClassHelper;
import org.jeeqb.framework.core.ClassScanner;
import org.jeeqb.framework.core.fault.InitializationError;
import org.jeeqb.framework.ioc.BeanHelper;
import org.jeeqb.framework.plugin.PluginProxy;
import org.jeeqb.framework.tx.TransactionProxy;
import org.jeeqb.framework.tx.annotation.Service;
import org.jeeqb.framework.util.ClassUtil;
import org.jeeqb.framework.util.CollectionUtil;
import org.jeeqb.framework.util.StringUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP 框架
 * Created by rocky on 2018/3/19.
 */
public class AopHelper {

    private final static ClassScanner classScanner = InstanceContext.getClassScanner();

    static {
        try {
            /**
             * 1.代理类与目标类列表的映射关系
             * 2.目标类与代理类列表的映射关系
             * 3.创建代理实例
             */
            //存放代理类与目标类列表映射关系
            Map<Class<?>, List<Class<?>>> proxyMap = createProxyMap();
            //存放目标类与代理类列表映射关系
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            //遍历targetMap
            for(Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();

                //创建代理实例
                Object proxyInstance = ProxyManger.createProxy(targetClass,proxyList);
                //用代理实例覆盖目标实例，并放入bean容器
                BeanHelper.setBean(targetClass, proxyInstance);
            }
        }catch (Exception e){
            throw new InitializationError("初始化AOP框架出错!",e);
        }

    }

    /**
     * 目标类与代理类列表映射关系
     * @param proxyMap
     * @return
     */
    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        // 遍历 Proxy Map
        for (Map.Entry<Class<?>, List<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            // 分别获取 map 中的 key 与 value
            Class<?> proxyClass = proxyEntry.getKey();
            List<Class<?>> targetClassList = proxyEntry.getValue();
            // 遍历目标类列表
            for (Class<?> targetClass : targetClassList) {
                // 创建代理类（切面类）实例
                Proxy baseAspect = (Proxy) proxyClass.newInstance();
                // 初始化 Target Map
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(baseAspect);
                } else {
                    List<Proxy> baseAspectList = new ArrayList<Proxy>();
                    baseAspectList.add(baseAspect);
                    targetMap.put(targetClass, baseAspectList);
                }
            }
        }
        return targetMap;
    }

    private static Map<Class<?>,List<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>,List<Class<?>>> proxyMap = new LinkedHashMap<>();
        /**
         * 添加相关代理
         * 1。插件代理
         * 2。切面代理
         * 3。事务代理
         */
        addPluginProxy(proxyMap);
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);

        return  proxyMap;
    }

    /**
     * 事务代理
     * @param proxyMap
     * @throws Exception
     */
    private static void addTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        // 使用 TransactionProxy 代理所有 Service 类
        List<Class<?>> serviceClassList = ClassHelper.getClassListByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassList);
    }

    /**
     * 切面代理
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        // 获取切面类（所有继承于 BaseAspect 的类）
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListBySuper(AspectProxy.class);
        // 添加插件包下所有的切面类
        aspectProxyClassList.addAll(classScanner.getClassListBySuper(JEEQB.PLUGIN_PACKAGE, AspectProxy.class));
        // 排序切面类
        sortAspectProxyClassList(aspectProxyClassList);
        // 遍历切面类
        for (Class<?> aspectProxyClass : aspectProxyClassList) {
            // 判断 Aspect 注解是否存在
            if (aspectProxyClass.isAnnotationPresent(Aspect.class)) {
                // 获取 Aspect 注解
                Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
                // 创建目标类列表
                List<Class<?>> targetClassList = createTargetClassList(aspect);
                // 初始化 Proxy Map
                proxyMap.put(aspectProxyClass, targetClassList);
            }
        }
    }

    private static List<Class<?>> createTargetClassList(Aspect aspect) throws Exception {
        List<Class<?>> targetClassList = new ArrayList<Class<?>>();
        // 获取 Aspect 注解的相关属性
        String pkg = aspect.pkg();
        String cls = aspect.cls();
        Class<? extends Annotation> annotation = aspect.annotation();
        // 若包名不为空，则需进一步判断类名是否为空
        if (StringUtil.isNotEmpty(pkg)) {
            if (StringUtil.isNotEmpty(cls)) {
                // 若类名不为空，则仅添加该类
                targetClassList.add(ClassUtil.loadClass(pkg + "." + cls, false));
            } else {
                // 若注解不为空且不是 Aspect 注解，则添加指定包名下带有该注解的所有类
                if (annotation != null && !annotation.equals(Aspect.class)) {
                    targetClassList.addAll(classScanner.getClassListByAnnotation(pkg, annotation));
                } else {
                    // 否则添加该包名下所有类
                    targetClassList.addAll(classScanner.getClassList(pkg));
                }
            }
        } else {
            // 若注解不为空且不是 Aspect 注解，则添加应用包名下带有该注解的所有类
            if (annotation != null && !annotation.equals(Aspect.class)) {
                targetClassList.addAll(ClassHelper.getClassListByAnnotation(annotation));
            }
        }
        return targetClassList;
    }

    /**
     * 切面类排序
     * @param proxyClassList
     */
    private static void sortAspectProxyClassList(List<Class<?>> proxyClassList) {
        // 排序代理类列表
        Collections.sort(proxyClassList, new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> aspect1, Class<?> aspect2) {
                if (aspect1.isAnnotationPresent(AspectOrder.class) || aspect2.isAnnotationPresent(AspectOrder.class)) {
                    // 若有 Order 注解，则优先比较（序号的值越小越靠前）
                    if (aspect1.isAnnotationPresent(AspectOrder.class)) {
                        return getOrderValue(aspect1) - getOrderValue(aspect2);
                    } else {
                        return getOrderValue(aspect2) - getOrderValue(aspect1);
                    }
                } else {
                    // 若无 Order 注解，则比较类名（按字母顺序升序排列）
                    return aspect1.hashCode() - aspect2.hashCode();
                }
            }

            private int getOrderValue(Class<?> aspect) {
                return aspect.getAnnotation(AspectOrder.class) != null ? aspect.getAnnotation(AspectOrder.class).value() : 0;
            }
        });
    }

    /**
     * 插件代理
     * @param proxyMap
     * @throws Exception
     */
    private static void addPluginProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        // 获取插件包名下父类为 PluginProxy 的所有类（插件代理类）
        List<Class<?>> pluginProxyClassList = classScanner.getClassListBySuper(JEEQB.PLUGIN_PACKAGE, PluginProxy.class);
        if (CollectionUtil.isNotEmpty(pluginProxyClassList)) {
            // 遍历所有插件代理类
            for (Class<?> pluginProxyClass : pluginProxyClassList) {
                // 创建插件代理类实例
                PluginProxy pluginProxy = (PluginProxy) pluginProxyClass.newInstance();
                // 将插件代理类及其所对应的目标类列表放入 Proxy Map 中
                proxyMap.put(pluginProxyClass, pluginProxy.getTargetClassList());
            }
        }
    }

}
