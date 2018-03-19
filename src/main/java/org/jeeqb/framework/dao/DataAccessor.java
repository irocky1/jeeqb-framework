package org.jeeqb.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据访问器
 * Created by rocky on 2018/3/19.
 */
public class DataAccessor {

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object[] params) {

        return null;
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object[] params) {
        return null;
    }

    public static  <K, V> Map<K,V> queryEntityMap(Class<V> entityClass, String sql, Object[] params) {
        return null;
    }

    public static Object[] queryArray(String sql, Object[] params) {
        return null;
    }

    public static List<Object[]> queryArrayList(String sql, Object[] params) {
        return null;
    }

    public static Map<String,Object> queryMap(String sql, Object[] params) {
        return null;
    }

    public static List<Map<String,Object>> queryMapList(String sql, Object[] params) {
        return null;
    }

    public static <T> T queryColumn(String sql, Object[] params) {
        return null;
    }

    public static <T> List<T> queryColumnList(String sql, Object[] params) {
        return null;
    }

    public static <T> Map<T,Map<String,Object>> queryColumnMap(String column, String sql, Object[] params) {
        return null;
    }

    public static long queryCount(String sql, Object[] params) {
        return 0l;
    }

    public static int update(String sql, Object[] params) {
        return 0;
    }

    public Serializable insertReturnPK(String sql, Object[] params) {
        return null;
    }
}
