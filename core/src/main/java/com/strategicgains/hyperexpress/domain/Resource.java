package com.strategicgains.hyperexpress.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Resource {
    Resource addNamespace(String name, String href);

    Resource addNamespace(Namespace namespace);

    Resource addNamespaces(Collection<Namespace> namespaces);

    List<Namespace> getNamespaces();

    boolean hasNamespaces();

    Resource addProperty(String name, Object value);

    Object getProperty(String name);

    boolean hasProperty(String name);

    Resource setProperty(String name, Object value);

    Object removeProperty(String name);

    Map<String, Object> getProperties();

    boolean hasProperties();

    Resource addLink(Link link);

    Resource addLink(Link link, boolean isMultiple);

    Resource addLink(String rel, String href);

    Resource addLink(String rel, String href, boolean isMultiple);

    Resource addLinks(Collection<Link> links);

    List<Link> getLinks();

    boolean hasLinks();

    Resource addResource(String rel, Resource resource);

    Resource addResource(String rel, Resource resource, boolean isMultiple);

    Resource addResources(String rel, Collection<Resource> resources);

    Map<String, List<Resource>> getResources();

    List<Resource> getResources(String rel);

    boolean hasResources();

    boolean hasResources(String rel);

    Resource from(Resource from);

    boolean isMultipleLinks(String rel);

    boolean isMultipleResources(String rel);
}
