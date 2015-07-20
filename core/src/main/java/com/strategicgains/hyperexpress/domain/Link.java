package com.strategicgains.hyperexpress.domain;

public interface Link extends Cloneable {
    String getHref();

    Link setHref(String href);

    String getRel();

    Link setRel(String rel);

    Link set(String name, String value);

    String get(String name);

    boolean has(String name);

    boolean hasToken();

    Link clone();
}
