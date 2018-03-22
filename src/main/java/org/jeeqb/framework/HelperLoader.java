package org.jeeqb.framework;

import org.jeeqb.framework.aop.AopHelper;
import org.jeeqb.framework.dao.DatabaseHelper;
import org.jeeqb.framework.ioc.BeanHelper;
import org.jeeqb.framework.ioc.IocHelper;
import org.jeeqb.framework.mvc.ActionHelper;
import org.jeeqb.framework.orm.EntityHelper;
import org.jeeqb.framework.plugin.PluginHelper;
import org.jeeqb.framework.util.ClassUtil;

/**
 *
 * 加载相应的Helper类
 *
 *
 * Created by rocky on 2017/12/26.
 */
public class HelperLoader {

    public static void init() {
        // 定义需要加载的 Helper 类
        Class<?>[] classList = {
                DatabaseHelper.class,
                EntityHelper.class,
                ActionHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                PluginHelper.class,
        };
        // 按照顺序加载类
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
