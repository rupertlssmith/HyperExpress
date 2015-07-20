package com.strategicgains.hyperexpress;

import com.strategicgains.hyperexpress.builder.ConditionalLinkBuilder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.builder.UrlBuilder;

public interface BuilderFactory {
    ConditionalLinkBuilder newLinkBuilder();

    ConditionalLinkBuilder newLinkBuilder(String urlPattern);

    UrlBuilder newUrlBuilder();

    TokenResolver newTokenResolver();
}
