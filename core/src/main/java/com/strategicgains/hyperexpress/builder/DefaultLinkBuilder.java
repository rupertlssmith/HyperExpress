package com.strategicgains.hyperexpress.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkDefinition;

public class DefaultLinkBuilder implements LinkBuilder {
    private static final String REL_TYPE = "rel";
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private UrlBuilder urlBuilder;
    private Map<String, String> attributes = new HashMap<String, String>();

    public DefaultLinkBuilder() {
        super();
        urlBuilder = new DefaultUrlBuilder();
    }

    public DefaultLinkBuilder(String urlPattern) {
        super();
        urlBuilder = new DefaultUrlBuilder(urlPattern);
    }

    public DefaultLinkBuilder(DefaultLinkBuilder that) {
        super();
        this.urlBuilder = that.urlBuilder.clone();
        this.attributes = new HashMap<String, String>(that.attributes);
    }

    public DefaultLinkBuilder baseUrl(String url) {
        urlBuilder.baseUrl(url);

        return this;
    }

    public DefaultLinkBuilder withQuery(String query) {
        urlBuilder.withQuery(query);

        return this;
    }

    public void clearAttributes() {
        attributes.clear();
    }

    public void clearQueries() {
        urlBuilder.clearQueries();
    }

    public String urlPattern() {
        return urlBuilder.urlPattern();
    }

    public DefaultLinkBuilder urlPattern(String pattern) {
        urlBuilder.urlPattern(pattern);

        return this;
    }

    public DefaultLinkBuilder rel(String rel) {
        return set(REL_TYPE, rel);
    }

    public String rel() {
        return get(REL_TYPE);
    }

    public DefaultLinkBuilder title(String title) {
        return set(TITLE, title);
    }

    public String title() {
        return get(TITLE);
    }

    public DefaultLinkBuilder type(String type) {
        return set(TYPE, type);
    }

    public String type() {
        return get(TYPE);
    }

    public String get(String name) {
        return attributes.get(name);
    }

    public DefaultLinkBuilder set(String name, String value) {
        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }

        return this;
    }

    public Link build() {
        return build(null);
    }

    public Link build(TokenResolver tokenResolver) {
        return createLink(urlBuilder.build(tokenResolver));
    }

    public Link build(Object object, TokenResolver tokenResolver) {
        return createLink(urlBuilder.build(object, tokenResolver));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getSimpleName());
        s.append("{");

        boolean isFirst = true;

        for (Entry<String, String> entry : attributes.entrySet()) {
            if (!isFirst) {
                s.append(", ");
            } else {
                isFirst = false;
            }

            s.append(entry.getKey());
            s.append("=");
            s.append(entry.getValue());
        }

        s.append("}");

        return s.toString();
    }

    private Link createLink(String url) {
        Link link = new LinkDefinition(attributes.get(REL_TYPE), url);

        for (Entry<String, String> entry : attributes.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(REL_TYPE)) {
                link.set(entry.getKey(), entry.getValue());
            }
        }

        return link;
    }
}
