package org.jeeqb.framework.orm;

import org.jeeqb.framework.core.ClassHelper;
import org.jeeqb.framework.orm.annotation.Column;
import org.jeeqb.framework.orm.annotation.Entity;
import org.jeeqb.framework.orm.annotation.Table;
import org.jeeqb.framework.util.ArrayUtil;
import org.jeeqb.framework.util.ClassUtil;
import org.jeeqb.framework.util.MapUtil;
import org.jeeqb.framework.util.StringUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化 Entity 结构
 *
 * Created by rocky on 2018/3/19.
 */
public class EntityHelper {

    //实体类 --》 表名
    private final static Map<Class<?>,String> entityClassTableNameMap = new HashMap<>();

    //实体类 -》字段名-》列名
    private final static Map<Class<?>,Map<String,String>> entityClassFieldMapMap = new HashMap<>();

    static {
       List<Class<?>> entityClassList = ClassHelper.getClassListByAnnotation(Entity.class);
       for(Class<?> entityClass : entityClassList){
           initEntityClassTableNameMap(entityClass);
           initEntityClassFieldMapMap(entityClass);
       }
    }

    /**
     * 初始化实体类与表名的映射关系
     * @param entityClass
     */
    private static void initEntityClassTableNameMap(Class<?> entityClass) {
        String tableName = "";
        //判断该实体类上是否有Table注解
        if(entityClass.isAnnotationPresent(Table.class)){
            //已存在，则使用注解中定义的表名
            tableName = entityClass.getAnnotation(Table.class).value();
        }else {
            //不存在，则将实体类名转换成下划线风格的表名
            tableName = StringUtil.camelhumpToUnderline(entityClass.getSimpleName());
        }

        entityClassTableNameMap.put(entityClass,tableName);
    }

    /**
     * 初始化实体类对应的字段名，列名
     * @param entityClass
     */
    private static void initEntityClassFieldMapMap(Class<?> entityClass) {
        //遍历实体类中的所有属性
        Field[] fields = entityClass.getDeclaredFields();
        if(ArrayUtil.isNotEmpty(fields)){
            Map<String,String> fieldMap = new HashMap<>();
            for(Field field : fields){
                String fieldName = field.getName();
                String columnName = "";
                //判断该字段上是否有 Column注解
                if(field.isAnnotationPresent(Column.class)){
                    //存在，则使用注解中的value
                    columnName = field.getAnnotation(Column.class).value();
                }else {
                    //不存在，则使用字段名按下划线风格
                    columnName = StringUtil.camelhumpToUnderline(field.getName());
                }
                fieldMap.put(fieldName,columnName);
            }
            entityClassFieldMapMap.put(entityClass,fieldMap);
        }
    }

    public static String getTableName(Class<?> entityClass) {
        return entityClassTableNameMap.get(entityClass);
    }

    public static Map<String,String> getFieldMap(Class<?> entityClass){
        return entityClassFieldMapMap.get(entityClass);
    }

    public static <T> Map<String,String> getColumnMap(Class<T> entityClass) {
        return MapUtil.invert(getFieldMap(entityClass));
    }

    public static String getColumnName(Class<?> entityClass, String fieldName) {
        String columnName = getFieldMap(entityClass).get(fieldName);
        return StringUtil.isNotEmpty(columnName) ? columnName : fieldName;
    }
}
