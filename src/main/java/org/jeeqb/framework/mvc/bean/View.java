package org.jeeqb.framework.mvc.bean;

import org.jeeqb.framework.core.bean.BaseBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装视图对象
 * Created by rocky on 2018/3/21.
 */
public class View extends BaseBean{

    /**
     * 视图路径
     */
    private String path;
    /**
     * 相关数据
     */
    private Map<String,Object> data;

    public View(String path) {
        this.path = path;
        this.data = new HashMap<>();
    }

    public View data(String key,Object value){
        data.put(key,value);
        return this;
    }

    public boolean isRedirect(){
        return path.startsWith("/");
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
