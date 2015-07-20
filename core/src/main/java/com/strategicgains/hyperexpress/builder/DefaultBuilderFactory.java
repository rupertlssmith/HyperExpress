package com.strategicgains.hyperexpress.builder;

import com.strategicgains.hyperexpress.BuilderFactory;

public class DefaultBuilderFactory implements BuilderFactory {
    public ConditionalLinkBuilder newLinkBuilder() {
        return new DefaultConditionalLinkBuilder();
    }

    public ConditionalLinkBuilder newLinkBuilder(String urlPattern) {
        return new DefaultConditionalLinkBuilder(urlPattern);
    }

    public UrlBuilder newUrlBuilder() {
        return new DefaultUrlBuilder();
    }

    public TokenResolver newTokenResolver() {
        return new DefaultTokenResolver();
    }
}
