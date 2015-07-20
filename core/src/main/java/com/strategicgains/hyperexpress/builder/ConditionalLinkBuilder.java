package com.strategicgains.hyperexpress.builder;

import java.util.List;

public interface ConditionalLinkBuilder extends LinkBuilder {
    void optional();

    void ifBound(String token);

    void ifNotBound(String token);

    boolean isOptional();

    boolean hasConditionals();

    List<String> getConditionals();
}
