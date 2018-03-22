package org.jeeqb.framework.mvc.fault;

/**
 * 认证异常类
 * Created by rocky on 2018/3/21.
 */
public class AuthcException extends RuntimeException {

    public AuthcException() {
    }

    public AuthcException(String message) {
        super(message);
    }

    public AuthcException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthcException(Throwable cause) {
        super(cause);
    }

}
