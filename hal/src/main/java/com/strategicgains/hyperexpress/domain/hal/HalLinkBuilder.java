package com.strategicgains.hyperexpress.domain.hal;

import com.strategicgains.hyperexpress.builder.DefaultConditionalLinkBuilder;

public class HalLinkBuilder extends DefaultConditionalLinkBuilder {
    private static final String TEMPLATED = "templated";
    private static final String DEPRECATION = "deprecation";
    private static final String NAME = "name";
    private static final String PROFILE = "profile";
    private static final String HREFLANG = "hreflang";

    public HalLinkBuilder(String urlPattern) {
        super(urlPattern);
    }

    public HalLinkBuilder templated(boolean value) {
        if (value == false) {
            set(TEMPLATED, null);
        } else {
            set(TEMPLATED, Boolean.TRUE.toString());
        }

        return this;
    }

    public HalLinkBuilder type(String type) {
        return (HalLinkBuilder) super.type(type);
    }

    public HalLinkBuilder deprecation(String deprecation) {
        return (HalLinkBuilder) set(DEPRECATION, deprecation);
    }

    public HalLinkBuilder name(String name) {
        return (HalLinkBuilder) set(NAME, name);
    }

    public HalLinkBuilder profile(String profile) {
        return (HalLinkBuilder) set(PROFILE, profile);
    }

    public HalLinkBuilder title(String title) {
        return (HalLinkBuilder) super.title(title);
    }

    public HalLinkBuilder hreflang(String hreflang) {
        return (HalLinkBuilder) set(HREFLANG, hreflang);
    }

    public HalLinkBuilder baseUrl(String url) {
        return (HalLinkBuilder) super.baseUrl(url);
    }

    public HalLinkBuilder rel(String rel) {
        return (HalLinkBuilder) super.rel(rel);
    }

    public HalLinkBuilder set(String name, String value) {
        return (HalLinkBuilder) super.set(name, value);
    }
}
