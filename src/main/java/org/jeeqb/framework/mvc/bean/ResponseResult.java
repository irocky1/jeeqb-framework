package org.jeeqb.framework.mvc.bean;

import org.jeeqb.framework.core.bean.BaseBean;

/**
 * 封装返回结果
 *
 * Created by rocky on 2018/3/21.
 */
public class ResponseResult extends BaseBean{

    /**
     * 成功标识
     */
    private boolean success;
    /**
     * 错误代码
     */
    private int error;
    /**
     * 返回相关数据
     */
    private Object data;

    public ResponseResult(boolean success) {
        this.success = success;
    }

    public ResponseResult(int error) {
        this.error = error;
    }

    public ResponseResult(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
