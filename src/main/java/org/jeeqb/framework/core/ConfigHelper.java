package org.jeeqb.framework.core;

import org.jeeqb.framework.JEEQB;
import org.jeeqb.framework.util.PropertiesUtil;

import java.util.Map;
import java.util.Properties;

/**
 * 获取属性文件中的属性值
 *
 * @author rocky
 * @since 1.0
 */
public class ConfigHelper {

    /**
     * 属性文件对象
     */
    private static final Properties configProps = PropertiesUtil.loadProps(JEEQB.CONFIG_PROPS);

    /**
     * 获取 String 类型的属性值
     */
    public static String getString(String key) {
        return PropertiesUtil.getString(configProps, key);
    }

    /**
     * 获取 String 类型的属性值（可指定默认值）
     */
    public static String getString(String key, String defaultValue) {
        return PropertiesUtil.getString(configProps, key, defaultValue);
    }

    /**
     * 获取 int 类型的属性值
     */
    public static int getInt(String key) {
        return PropertiesUtil.getNumber(configProps, key);
    }

    /**
     * 获取 int 类型的属性值（可指定默认值）
     */
    public static int getInt(String key, int defaultValue) {
        return PropertiesUtil.getNumber(configProps, key, defaultValue);
    }

    /**
     * 获取 boolean 类型的属性值
     */
    public static boolean getBoolean(String key) {
        return PropertiesUtil.getBoolean(configProps, key);
    }

    /**
     * 获取 int 类型的属性值（可指定默认值）
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return PropertiesUtil.getBoolean(configProps, key, defaultValue);
    }

    /**
     * 获取指定前缀的相关属性
     *
     */
    public static Map<String, Object> getMap(String prefix) {
        return PropertiesUtil.getMap(configProps, prefix);
    }

}
