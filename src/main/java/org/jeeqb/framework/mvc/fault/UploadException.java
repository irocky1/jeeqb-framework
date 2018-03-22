package org.jeeqb.framework.mvc.fault;

/**
 * 上传文件异常类
 * Created by rocky on 2018/3/21.
 */
public class UploadException extends RuntimeException {

    public UploadException() {
    }

    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadException(Throwable cause) {
        super(cause);
    }
}
