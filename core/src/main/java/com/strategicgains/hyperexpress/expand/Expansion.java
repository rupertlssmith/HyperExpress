package com.strategicgains.hyperexpress.expand;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.strategicgains.hyperexpress.domain.Resource;

public class Expansion implements Iterable<String> {
    private Set<String> rels;

    private String mediaType;

    public Expansion(String mediaType) {
        super();
        this.mediaType = mediaType;
    }

    public Expansion(String mediaType, List<String> rels) {
        this(mediaType);
        this.rels = (rels == null ? null : new HashSet<String>(rels));
    }

    public Expansion addExpansion(String rel) {
        if (rels == null) {
            rels = new HashSet<String>();
        }

        rels.add(rel);

        return this;
    }

    public boolean isEmpty() {
        return (rels == null || rels.isEmpty());
    }

    public boolean contains(String rel) {
        return (isEmpty() ? false : rels.contains(rel));
    }

    public Iterator<String> iterator() {
        return (isEmpty() ? Collections.<String>emptySet().iterator() : rels.iterator());
    }

    public String getMediaType() {
        return mediaType;
    }
}
