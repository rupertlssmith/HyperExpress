package com.strategicgains.hyperexpress.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.strategicgains.hyperexpress.util.Strings;

public class LinkDefinition implements Link {
    private static final String REL_TYPE = "rel";
    private static final String HREF = "href";

    private Map<String, String> attributes = new HashMap<String, String>();

    public LinkDefinition(String rel, String href) {
        super();
        setRel(rel);
        setHref(href);
    }

    public LinkDefinition(LinkDefinition that) {
        super();

        if (that != null) {
            this.attributes.putAll(that.attributes);
        }
    }

    @Override
    public LinkDefinition clone() {
        return new LinkDefinition(this);
    }

    @Override
    public String getHref() {
        return get(HREF);
    }

    @Override
    public LinkDefinition setHref(String href) {
        set(HREF, href);

        return this;
    }

    @Override
    public String getRel() {
        return get(REL_TYPE);
    }

    @Override
    public LinkDefinition setRel(String rel) {
        set(REL_TYPE, rel);

        return this;
    }

    @Override
    public LinkDefinition set(String name, String value) {
        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }

        return this;
    }

    @Override
    public String get(String name) {
        return attributes.get(name);
    }

    @Override
    public boolean has(String name) {
        return (get(name) != null);
    }

    @Override
    public boolean hasToken() {
        return Strings.hasToken(getHref());
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

    @Override
    public int hashCode() {
        return 31 + ((attributes == null) ? 0 : attributes.hashCode());
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that == null) {
            return false;
        }

        if (getClass() != that.getClass()) {
            return false;
        }

        return equals((LinkDefinition) that);
    }

    public boolean equals(LinkDefinition that) {
        if (attributes == null) {
            if (that.attributes != null) {
                return false;
            }
        } else if (!attributes.equals(that.attributes)) {
            return false;
        }

        return true;
    }
}
