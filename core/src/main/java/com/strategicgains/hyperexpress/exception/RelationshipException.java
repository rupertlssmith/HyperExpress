package com.strategicgains.hyperexpress.exception;

public class RelationshipException extends RuntimeException {
    private static final long serialVersionUID = -1729155706564587435L;

    public RelationshipException(String message) {
        super(message);
    }

    public RelationshipException(Throwable throwable) {
        super(throwable);
    }

    public RelationshipException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
