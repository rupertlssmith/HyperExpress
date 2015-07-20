package com.strategicgains.hyperexpress.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.strategicgains.hyperexpress.exception.ResourceException;

public abstract class AbstractResource implements Resource {
    private List<Namespace> namespaces;
    private Map<String, List<Link>> linksByRel = new LinkedHashMap<String, List<Link>>();
    private List<Link> allLinks = new ArrayList<Link>();
    private Map<String, Object> properties = new LinkedHashMap<String, Object>();
    private Map<String, List<Resource>> resources;
    private Set<String> arrayLinkRels = new HashSet<String>();
    private Set<String> arrayResourceRels = new HashSet<String>();

    public Resource from(Resource that) {
        addNamespaces(that.getNamespaces());
        addLinks(that.getLinks());

        for (Map.Entry<String, Object> entry : that.getProperties().entrySet()) {
            this.addProperty(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, List<Resource>> entry : that.getResources().entrySet()) {
            this.addResources(entry.getKey(), entry.getValue());
        }

        return this;
    }

    public Resource addProperty(String name, Object value) {
        if (properties.containsKey(name)) {
            throw new ResourceException("Duplicate property: " + name);
        }

        properties.put(name, value);

        return this;
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public Resource setProperty(String key, Object value) {
        if (value != null) {
            properties.put(key, value);
        } else {
            properties.remove(key);
        }

        return this;
    }

    public Resource addLink(String rel, String url) {
        return addLink(rel, url, false);
    }

    public Resource addLink(String rel, String url, boolean isMultiple) {
        return addLink(new LinkDefinition(rel, url), isMultiple);
    }

    public Resource addNamespace(String name, String href) {
        return addNamespace(new Namespace(name, href));
    }

    public Resource addNamespace(Namespace namespace) {
        if (namespace == null) {
            throw new ResourceException("Cannot add null namespace");
        }

        if (namespaces == null) {
            namespaces = new ArrayList<Namespace>();
        }

        if (!namespaces.contains(namespace)) {
            namespaces.add(namespace);
        }

        return this;
    }

    public Resource addNamespaces(Collection<Namespace> values) {
        if (values == null) {
            return this;
        }

        for (Namespace ns : values) {
            addNamespace(ns);
        }

        return this;
    }

    public List<Namespace> getNamespaces() {
        return (namespaces == null ? Collections.<Namespace>emptyList() : Collections.unmodifiableList(namespaces));
    }

    public boolean hasNamespaces() {
        return (namespaces != null && !namespaces.isEmpty());
    }

    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public boolean hasProperties() {
        return (!properties.isEmpty());
    }

    public Resource addLink(Link link) {
        return addLink(link, false);
    }

    public Resource addLink(Link link, boolean isMultiple) {
        if (link == null) {
            throw new ResourceException("Cannot add null link");
        }

        if (link.getRel() == null) {
            throw new ResourceException("Cannot link with null 'rel'");
        }

        acquireLinksForRel(link.getRel()).add(link);
        allLinks.add(link);

        if (isMultiple) {
            arrayLinkRels.add(link.getRel());
        }

        return this;
    }

    public Resource addLinks(Collection<Link> links) {
        if (links == null) {
            throw new ResourceException("Cannot add null links collection to resource");
        }

        for (Link link : links) {
            addLink(link);
        }

        return this;
    }

    public List<Link> getLinks() {
        return Collections.unmodifiableList(allLinks);
    }

    public Map<String, List<Link>> getLinksByRel() {
        return Collections.unmodifiableMap(linksByRel);
    }

    public boolean hasLinks() {
        return (!linksByRel.isEmpty());
    }

    public Resource addResource(String rel, Resource resource) {
        return addResource(rel, resource, false);
    }

    public Resource addResource(String rel, Resource resource, boolean isMultiple) {
        if (rel == null) {
            throw new ResourceException("Cannot embed resource using null 'rel'");
        }

        if (resource == null) {
            throw new ResourceException("Cannot embed null resource");
        }

        List<Resource> forRel = acquireResourcesForRel(rel);
        forRel.add(resource);

        if (isMultiple) {
            arrayResourceRels.add(rel);
        }

        return this;
    }

    public Resource addResources(String rel, Collection<Resource> collection) {
        List<Resource> forRel = acquireResourcesForRel(rel);
        forRel.addAll(collection);
        arrayResourceRels.add(rel);

        return this;
    }

    public Map<String, List<Resource>> getResources() {
        return Collections.unmodifiableMap(_getResources());
    }

    @SuppressWarnings("unchecked")
    public List<Resource> getResources(String rel) {
        List<Resource> result = _getResources().get(rel);

        return (result == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(result));
    }

    public boolean hasResources() {
        return (resources != null && !resources.isEmpty());
    }

    public boolean isMultipleLinks(String rel) {
        return arrayLinkRels.contains(rel);
    }

    public boolean isMultipleResources(String rel) {
        return arrayResourceRels.contains(rel);
    }

    public Object removeProperty(String name) {
        return properties.remove(name);
    }

    public boolean hasResources(String rel) {
        return _getResources().containsKey(rel);
    }

    public boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    private Map<String, List<Resource>> _getResources() {
        return (resources == null ? Collections.<String, List<Resource>>emptyMap() : resources);
    }

    private List<Resource> acquireResourcesForRel(String rel) {
        if (resources == null) {
            resources = new HashMap<String, List<Resource>>();
        }

        List<Resource> forRel = resources.get(rel);

        if (forRel == null) {
            forRel = new ArrayList<Resource>();
            resources.put(rel, forRel);
        }

        return forRel;
    }

    private List<Link> acquireLinksForRel(String rel) {
        List<Link> forRel = linksByRel.get(rel);

        if (forRel == null) {
            forRel = new ArrayList<Link>();
            linksByRel.put(rel, forRel);
        }

        return forRel;
    }
}
