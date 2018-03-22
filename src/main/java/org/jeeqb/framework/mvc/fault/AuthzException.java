package org.jeeqb.framework.mvc.fault;

/**
 * 授权异常类
 * Created by rocky on 2018/3/21.
 */
public class AuthzException extends RuntimeException {

    public AuthzException() {
    }

    public AuthzException(String message) {
        super(message);
    }

    public AuthzException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthzException(Throwable cause) {
        super(cause);
    }
}
