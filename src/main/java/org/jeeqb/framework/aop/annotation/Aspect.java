package org.jeeqb.framework.aop.annotation;

import java.lang.annotation.*;

/**
 * 定义切面类
 * Created by rocky on 2018/1/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 包名
     * @return
     */
    String pkg() default "";

    /**
     * 类名
     * @return
     */
    String cls() default "";

    /**
     * 注解类
     * @return
     */
    Class<? extends Annotation> annotation() default Aspect.class;
}
