package com.strategicgains.hyperexpress.builder;

public interface TokenBinder<T> {
    void bind(T object, TokenResolver resolver);
}
