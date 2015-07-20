package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.List;

import com.strategicgains.hyperexpress.util.Strings;

public class DefaultUrlBuilder implements UrlBuilder {
    private String baseUrl;
    private String urlPattern;
    private List<String> queries;

    public DefaultUrlBuilder() {
        super();
    }

    public DefaultUrlBuilder(String urlPattern) {
        this();

        if (urlPattern == null) {
            throw new NullPointerException(
                "URL Pattern cannot be null using this constructor. Use the no-argument constructor, UrlBuilder().");
        }

        this.urlPattern = urlPattern;
    }

    public DefaultUrlBuilder urlPattern(String urlPattern) {
        this.urlPattern = urlPattern;

        return this;
    }

    public String urlPattern() {
        return urlPattern;
    }

    public DefaultUrlBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;

        return this;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public DefaultUrlBuilder withQuery(String query) {
        queries().add(query);

        return this;
    }

    public void clearQueries() {
        if (queries != null) {
            queries.clear();
        }
    }

    @Override
    public DefaultUrlBuilder clone() {
        DefaultUrlBuilder b = new DefaultUrlBuilder(this.urlPattern);
        b.baseUrl = this.baseUrl;
        b.queries = (this.queries == null ? null : new ArrayList<String>(this.queries));

        return b;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("baseUrl: ");
        s.append(baseUrl == null ? "null" : baseUrl);
        s.append(", urlPattern: ");
        s.append(urlPattern == null ? "null" : urlPattern);
        printQueryStrings(s);

        return s.toString();
    }

    public String build() {
        return build(null);
    }

    public String build(TokenResolver tokenResolver) {
        return build(buildFullUrlPattern(), null, tokenResolver);
    }

    public String build(Object object, TokenResolver tokenResolver) {
        return build(buildFullUrlPattern(), object, tokenResolver);
    }

    public String build(String urlPattern, TokenResolver tokenResolver) {
        return build(urlPattern, null, tokenResolver);
    }

    public String build(String urlPattern, Object object, TokenResolver tokenResolver) {
        if (tokenResolver == null) {
            return appendQueryString(urlPattern, null);
        }

        String url = tokenResolver.resolve(urlPattern, object);

        return appendQueryString(url, tokenResolver);
    }

    private String buildFullUrlPattern() {
        if (urlPattern == null) {
            throw new IllegalStateException("Null URL pattern");
        }

        return (baseUrl == null ? urlPattern : baseUrl + urlPattern);
    }

    private String appendQueryString(String url, TokenResolver tokenResolver) {
        if (queries == null || queries.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean hasQuery = url.contains("?");

        for (String query : queries) {
            String boundQuery = null;

            if (Strings.hasToken(query)) {
                boundQuery = resolveQueryString(query, tokenResolver);
            } else {
                boundQuery = query;
            }

            if (boundQuery != null) {
                sb.append(queryDelimiter(hasQuery));
                sb.append(boundQuery);
                hasQuery = true;
            }
        }

        return (hasQuery ? sb.toString() : url);
    }

    private String resolveQueryString(String query, TokenResolver tokenResolver) {
        if (tokenResolver != null) {
            String[] resolved = tokenResolver.resolveMulti(query);
            String all = String.join(queryDelimiter(true), resolved);

            return (Strings.hasToken(all) ? null : all);
        }

        return null;
    }

    private String queryDelimiter(boolean hasQuery) {
        return (hasQuery ? "&" : "?");
    }

    private void printQueryStrings(StringBuilder s) {
        boolean isFirst = true;
        s.append("}, query-strings: {");

        if (queries != null) {
            for (String query : queries) {
                if (!isFirst) {
                    s.append(", ");
                } else {
                    isFirst = false;
                }

                s.append(query);
            }
        }

        s.append("}");
    }

    private List<String> queries() {
        if (queries == null) {
            queries = new ArrayList<String>();
        }

        return queries;
    }
}
