package com.strategicgains.hyperexpress.builder;

import com.strategicgains.hyperexpress.BuilderFactory;

public class DefaultBuilderFactory implements BuilderFactory {
    @Override
    public ConditionalLinkBuilder newLinkBuilder() {
        return new DefaultConditionalLinkBuilder();
    }

    @Override
    public ConditionalLinkBuilder newLinkBuilder(String urlPattern) {
        return new DefaultConditionalLinkBuilder(urlPattern);
    }

    @Override
    public UrlBuilder newUrlBuilder() {
        return new DefaultUrlBuilder();
    }

    @Override
    public TokenResolver newTokenResolver() {
        return new DefaultTokenResolver();
    }
}
