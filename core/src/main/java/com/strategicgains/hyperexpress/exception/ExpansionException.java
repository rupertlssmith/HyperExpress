package com.strategicgains.hyperexpress.exception;

public class ExpansionException extends RuntimeException {
    private static final long serialVersionUID = -1729155706564587435L;

    public ExpansionException(String message) {
        super(message);
    }

    public ExpansionException(Throwable throwable) {
        super(throwable);
    }

    public ExpansionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
