package com.strategicgains.hyperexpress.domain;

import com.strategicgains.hyperexpress.exception.ResourceException;

public class Namespace implements Cloneable {
    private String name;
    private String href;
    private Boolean templated;

    public Namespace() {
        super();
    }

    public Namespace(String name, String href) {
        this();

        if (name == null) {
            throw new ResourceException("Namespace name cannot be null");
        }

        if (href == null) {
            throw new ResourceException("Namespace href cannot be null");
        }

        this.name = name;
        this.href = href;
    }

    public String name() {
        return name;
    }

    public String href() {
        return href;
    }

    public Namespace clone() {
        return new Namespace(name, href);
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof Namespace)) {
            return false;
        }

        return equals((Namespace) that);
    }

    public boolean equals(Namespace that) {
        if (that == null) {
            return false;
        }

        return (this.href.equals(that.href) && this.name.equals(that.name));
    }

    public int hashCode() {
        return 42 + name.hashCode() + href.hashCode();
    }

    public Namespace templated(Boolean templated) {
        this.templated = templated;

        return this;
    }

    public boolean isTemplated() {
        return (templated == null ? false : templated);
    }
}
