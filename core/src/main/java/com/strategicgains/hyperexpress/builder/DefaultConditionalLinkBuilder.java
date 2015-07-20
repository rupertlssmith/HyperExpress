package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.List;

import com.strategicgains.hyperexpress.domain.Link;

public class DefaultConditionalLinkBuilder extends DefaultLinkBuilder implements ConditionalLinkBuilder {
    private boolean optional = false;
    private List<String> conditionals = new ArrayList<String>();

    public DefaultConditionalLinkBuilder() {
        super();
    }

    public DefaultConditionalLinkBuilder(String urlPattern) {
        super(urlPattern);
    }

    public DefaultConditionalLinkBuilder(DefaultLinkBuilder builder) {
        super(builder);
    }

    public DefaultConditionalLinkBuilder(DefaultConditionalLinkBuilder builder) {
        super(builder);
        this.conditionals = new ArrayList<String>(builder.conditionals);
    }

    public void optional() {
        optional = true;
    }

    public void ifBound(String token) {
        if (token == null) {
            return;
        }

        if (token.startsWith("{") && token.endsWith("}")) {
            conditionals.add(token);
        } else {
            conditionals.add("{" + token + "}");
        }
    }

    public void ifNotBound(String token) {
        if (token == null) {
            return;
        }

        if (token.startsWith("{") && token.endsWith("}")) {
            conditionals.add("!" + token);
        } else {
            conditionals.add("!{" + token + "}");
        }
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean hasConditionals() {
        return (conditionals != null && !conditionals.isEmpty());
    }

    public List<String> getConditionals() {
        return conditionals;
    }

    public Link build(Object object, TokenResolver tokenResolver) {
        Link link = super.build(object, tokenResolver);

        if (hasConditionals()) {
            for (String conditional : conditionals) {
                String value = tokenResolver.resolve(conditional, object);

                // not bound
                if ((value.startsWith("{") && value.endsWith("}")) ||
                        value.trim().equalsIgnoreCase(Boolean.FALSE.toString())) {
                    return null;
                }
                // desire not bound
                else if (value.startsWith("!")) {
                    if (!(value.startsWith("!{") && value.endsWith("}")) &&
                            !value.substring(1).trim().equalsIgnoreCase(Boolean.FALSE.toString())) {
                        return null;
                    }
                }
            }
        } else if (isOptional() && link.hasToken()) {
            return null;
        }

        return link;
    }

    public Link build() {
        Link link = super.build();

        if (isOptional() && link.hasToken()) {
            return null;
        }

        return link;
    }

    public Link build(TokenResolver tokenResolver) {
        Link link = super.build(tokenResolver);

        if (isOptional() && link.hasToken()) {
            return null;
        }

        return link;
    }
}
