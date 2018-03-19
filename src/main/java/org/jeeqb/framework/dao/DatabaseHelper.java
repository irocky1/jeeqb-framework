package org.jeeqb.framework.dao;

import org.apache.commons.io.FileUtils;
import org.jeeqb.framework.InstanceContext;
import org.jeeqb.framework.core.ConfigHelper;
import org.jeeqb.framework.ds.DataSourceFactory;
import org.jeeqb.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * 数据库相关操作
 * Created by rocky on 2018/3/19.
 */
public class DatabaseHelper {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    /**
     * 定义线程局部变量，让每一个线程都拥有自己的连接
     */
    private static final ThreadLocal<Connection> connectContainer = new ThreadLocal<>();

    /**
     * 获取数据源工厂
     */
    private static final DataSourceFactory dataSourceFactory = InstanceContext.getDataSourceFactory();

    /**
     * 数据访问器
     */
    private static final DataAccessor dataAccessor = InstanceContext.getDataAccessor();

    /**
     * 数据库类型
     */
    private final static String databaseType = ConfigHelper.getString("jeeqb.framework.jdbc.type");

    /**
     * 获取数据库类型
     * @return
     */
    public static String getDatabaseType() {
        return databaseType;
    }

    public static DataSource getDataSource(){
        return dataSourceFactory.getDataSource();
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = connectContainer.get();
            if(connection == null){
                //若不存在，刚从datasource 中获取 connection
                connection = getDataSource().getConnection();
                if(null != connection){
                    connectContainer.set(connection);
                }
            }
        }catch (SQLException e){
            logger.error("获取数据库连接出错!",e);
            throw new RuntimeException(e);
        }
        return  connection;
    }

    /**
     * 开启事务
     * @throws Exception
     */
    public static void beginTransaction() throws Exception{
        Connection connection = getConnection();
        if(null != connection){
            try {
                connection.setAutoCommit(false);
            }catch (SQLException e){
                logger.error("开启事务出错!",e);
                throw new RuntimeException(e);
            }finally {
                connectContainer.set(connection);
            }
        }

    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection connection = getConnection();
        if(null != connection){
            try {
                connection.commit();
                connection.close();
            }catch (SQLException e){
                logger.error("提交事务出错!",e);
                throw new RuntimeException(e);
            }finally {
                connectContainer.remove();
            }
        }

    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection connection = getConnection();
        if(null != connection){
            try {
                connection.rollback();
                connection.close();
            }catch (SQLException e){
                logger.error("回滚事务出错!",e);
                throw new RuntimeException(e);
            }finally {
                connectContainer.remove();
            }
        }
    }

    /**
     * 初始化sql脚本
     * @param sqlPath
     */
    public static void initSQL(String sqlPath){
        try {
            File sqlFile = new File(ClassUtil.getClassPath() + sqlPath);
            List<String> sqlList = FileUtils.readLines(sqlFile);
            for (String sql : sqlList) {
                update(sql);
            }
        }catch (Exception e){
            logger.error("初始化SQL脚本出错",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 SQL 语句查询 Entity
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntity(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntityList(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 映射（Field Name => Field Value）
     */
    public static <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntityMap(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段（单条记录）
     */
    public static Object[] queryArray(String sql, Object... params) {
        return dataAccessor.queryArray(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段列表（多条记录）
     */
    public static List<Object[]> queryArrayList(String sql, Object... params) {
        return dataAccessor.queryArrayList(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段（单条记录）
     */
    public static Map<String, Object> queryMap(String sql, Object... params) {
        return dataAccessor.queryMap(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段列表（多条记录）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        return dataAccessor.queryMapList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段（单条记录）
     */
    public static <T> T queryColumn(String sql, Object... params) {
        return dataAccessor.queryColumn(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段列表（多条记录）
     */
    public static <T> List<T> queryColumnList(String sql, Object... params) {
        return dataAccessor.queryColumnList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段映射（多条记录）
     */
    public static <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params) {
        return dataAccessor.queryColumnMap(column, sql, params);
    }

    /**
     * 根据 SQL 语句查询记录条数
     */
    public static long queryCount(String sql, Object... params) {
        return dataAccessor.queryCount(sql, params);
    }

    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int update(String sql, Object... params) {
        return dataAccessor.update(sql, params);
    }

    /**
     * 执行插入语句，返回插入后的主键
     */
    public static Serializable insertReturnPK(String sql, Object... params) {
        return dataAccessor.insertReturnPK(sql, params);
    }


}
