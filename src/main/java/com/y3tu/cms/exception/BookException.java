package com.y3tu.cms.exception;

import com.y3tu.tools.kit.exception.ExceptionUtil;

/**
 * 工具异常
 *
 * @author y3tu
 */
public class BookException extends RuntimeException {
    public BookException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String messageTemplate, Object... params) {
        super(String.format(messageTemplate, params));
    }

    public BookException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BookException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public BookException(Throwable throwable, String messageTemplate, Object... params) {
        super(String.format(messageTemplate, params), throwable);
    }
}
