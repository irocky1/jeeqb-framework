package org.jeeqb.framework.mvc;

/**
 * 封装Request相关信息
 * Created by rocky on 2018/3/21.
 */
public class Requester {

    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求路径
     */
    private String path;

    public Requester(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
