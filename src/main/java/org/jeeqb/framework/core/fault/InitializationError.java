package org.jeeqb.framework.core.fault;

/**
 * 初始化错误
 * Created by rocky on 2017/12/27.
 */
public class InitializationError extends Error{

    public InitializationError() {
    }

    public InitializationError(String message) {
        super(message);
    }

    public InitializationError(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializationError(Throwable cause) {
        super(cause);
    }
}
