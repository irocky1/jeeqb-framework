package org.jeeqb.framework.mvc.bean;

import org.jeeqb.framework.core.bean.BaseBean;
import org.jeeqb.framework.util.CastUtil;

import java.util.Map;

/**
 * 封装请求参数
 *
 * Created by rocky on 2018/3/21.
 */
public class RequestParams extends BaseBean {

    private final Map<String,Object> fieldMap;

    public RequestParams(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public String getString(String name) {
        return CastUtil.castString(get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(get(name));
    }

    public long getLong(String name) {
        return CastUtil.castLong(get(name));
    }

    public int getInt(String name) {
        return CastUtil.castInt(get(name));
    }

    private Object get(String name) {
        return fieldMap.get(name);
    }


}
