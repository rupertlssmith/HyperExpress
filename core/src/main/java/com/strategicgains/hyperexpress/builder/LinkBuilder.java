package com.strategicgains.hyperexpress.builder;

import com.strategicgains.hyperexpress.domain.Link;

public interface LinkBuilder {
    LinkBuilder baseUrl(String url);

    LinkBuilder withQuery(String query);

    void clearAttributes();

    void clearQueries();

    String urlPattern();

    LinkBuilder urlPattern(String pattern);

    LinkBuilder rel(String rel);

    String rel();

    LinkBuilder title(String title);

    String title();

    LinkBuilder type(String type);

    String type();

    String get(String name);

    LinkBuilder set(String name, String value);

    Link build();

    Link build(TokenResolver tokenResolver);

    Link build(Object object, TokenResolver tokenResolver);
}
