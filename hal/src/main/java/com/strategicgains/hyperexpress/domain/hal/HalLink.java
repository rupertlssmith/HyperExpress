package com.strategicgains.hyperexpress.domain.hal;

import java.util.regex.Pattern;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;

public class HalLink {
    // The attributes from 'LinkDefinition' that a HAL link cares about.
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String TEMPLATED = "templated";
    public static final String PROFILE = "profile";
    public static final String NAME = "name";
    public static final String HREFLANG = "hreflang";
    public static final String HREF = "href";
    public static final String DEPRECATION = "deprecation";

    // Regular expression for the hasTemplate() method.
    private static final String TEMPLATE_REGEX = "\\{(\\w*?)\\}";
    private static final Pattern TEMPLATE_PATTERN = Pattern.compile(TEMPLATE_REGEX);

    @Required
    private String href;

    private String name;

    @RegexValidation(
        pattern = "[A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*", nullable = true, message = "MUST be a language tag [RFC3066]"
    )
    private String hreflang;

    private String title;

    private Boolean templated;

    private String type;

    private String deprecation;

    private String profile;

    public HalLink() {
        super();
    }

    public HalLink(Link that) {
        this();
        this.setHref(that.getHref());
        this.setDeprecation(that.get(DEPRECATION));
        this.setHreflang(that.get(HREFLANG));
        this.setName(that.get(NAME));
        this.setProfile(that.get(PROFILE));
        this.setTemplated(that.has(TEMPLATED) ? Boolean.valueOf(that.get(TEMPLATED)) : null);
        this.setTitle(that.get(TITLE));
        this.setType(that.get(TYPE));
    }

    public String getHref() {
        return href;
    }

    public HalLink setHref(String href) {
        this.href = href;

        return this;
    }

    public String getName() {
        return name;
    }

    public HalLink setName(String name) {
        this.name = name;

        return this;
    }

    public String getHreflang() {
        return hreflang;
    }

    public HalLink setHreflang(String hreflang) {
        this.hreflang = hreflang;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public HalLink setTitle(String title) {
        this.title = title;

        return this;
    }

    public Boolean getTemplated() {
        return templated;
    }

    public boolean hasTemplate() {
        return TEMPLATE_PATTERN.matcher(getHref()).find();
    }

    public HalLink setTemplated(Boolean templated) {
        this.templated = templated;

        return this;
    }

    public String getType() {
        return type;
    }

    public HalLink setType(String type) {
        this.type = type;

        return this;
    }

    public String getDeprecation() {
        return deprecation;
    }

    public HalLink setDeprecation(String deprecation) {
        this.deprecation = deprecation;

        return this;
    }

    public String getProfile() {
        return profile;
    }

    public HalLink setProfile(String profile) {
        this.profile = profile;

        return this;
    }
}
