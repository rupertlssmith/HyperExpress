package com.strategicgains.hyperexpress.exception;

public class ResourceException extends RuntimeException {
    private static final long serialVersionUID = -1729155706564587435L;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(Throwable throwable) {
        super(throwable);
    }

    public ResourceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
