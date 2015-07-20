package com.strategicgains.hyperexpress.builder;

public interface UrlBuilder extends Cloneable {
    UrlBuilder clone();

    String urlPattern();

    UrlBuilder urlPattern(String urlPattern);

    UrlBuilder baseUrl(String baseUrl);

    String baseUrl();

    UrlBuilder withQuery(String query);

    void clearQueries();

    String build();

    String build(TokenResolver tokenResolver);

    String build(Object object, TokenResolver tokenResolver);

    String build(String urlPattern, TokenResolver tokenResolver);

    String build(String urlPattern, Object object, TokenResolver tokenResolver);
}
