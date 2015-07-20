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

    @Override
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

    @Override
    public Resource addProperty(String name, Object value) {
        if (properties.containsKey(name)) {
            throw new ResourceException("Duplicate property: " + name);
        }

        properties.put(name, value);

        return this;
    }

    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public Resource setProperty(String key, Object value) {
        if (value != null) {
            properties.put(key, value);
        } else {
            properties.remove(key);
        }

        return this;
    }

    @Override
    public Resource addLink(String rel, String url) {
        return addLink(rel, url, false);
    }

    @Override
    public Resource addLink(String rel, String url, boolean isMultiple) {
        return addLink(new LinkDefinition(rel, url), isMultiple);
    }

    @Override
    public Resource addNamespace(String name, String href) {
        return addNamespace(new Namespace(name, href));
    }

    @Override
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

    @Override
    public Resource addNamespaces(Collection<Namespace> values) {
        if (values == null) {
            return this;
        }

        for (Namespace ns : values) {
            addNamespace(ns);
        }

        return this;
    }

    @Override
    public List<Namespace> getNamespaces() {
        return (namespaces == null ? Collections.<Namespace>emptyList() : Collections.unmodifiableList(namespaces));
    }

    @Override
    public boolean hasNamespaces() {
        return (namespaces != null && !namespaces.isEmpty());
    }

    @Override
    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public boolean hasProperties() {
        return (!properties.isEmpty());
    }

    @Override
    public Resource addLink(Link link) {
        return addLink(link, false);
    }

    @Override
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

    @Override
    public Resource addLinks(Collection<Link> links) {
        if (links == null) {
            throw new ResourceException("Cannot add null links collection to resource");
        }

        for (Link link : links) {
            addLink(link);
        }

        return this;
    }

    @Override
    public List<Link> getLinks() {
        return Collections.unmodifiableList(allLinks);
    }

    public Map<String, List<Link>> getLinksByRel() {
        return Collections.unmodifiableMap(linksByRel);
    }

    @Override
    public boolean hasLinks() {
        return (!linksByRel.isEmpty());
    }

    @Override
    public Resource addResource(String rel, Resource resource) {
        return addResource(rel, resource, false);
    }

    @Override
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

    @Override
    public Resource addResources(String rel, Collection<Resource> collection) {
        List<Resource> forRel = acquireResourcesForRel(rel);
        forRel.addAll(collection);
        arrayResourceRels.add(rel);

        return this;
    }

    @Override
    public Map<String, List<Resource>> getResources() {
        return Collections.unmodifiableMap(_getResources());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> getResources(String rel) {
        List<Resource> result = _getResources().get(rel);

        return (result == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(result));
    }

    @Override
    public boolean hasResources() {
        return (resources != null && !resources.isEmpty());
    }

    @Override
    public boolean isMultipleLinks(String rel) {
        return arrayLinkRels.contains(rel);
    }

    @Override
    public boolean isMultipleResources(String rel) {
        return arrayResourceRels.contains(rel);
    }

    @Override
    public Object removeProperty(String name) {
        return properties.remove(name);
    }

    @Override
    public boolean hasResources(String rel) {
        return _getResources().containsKey(rel);
    }

    @Override
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
