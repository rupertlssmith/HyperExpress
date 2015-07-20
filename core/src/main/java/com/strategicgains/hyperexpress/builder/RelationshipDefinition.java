package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.strategicgains.hyperexpress.BuilderFactory;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.exception.RelationshipException;
import com.strategicgains.hyperexpress.util.Strings;

public class RelationshipDefinition {
    private static final String COLLECTION_SUFFIX = ".Collection";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String HREFLANG = "hreflang";
    private static final String TEMPLATED = "templated";
    private static final String DEPRECATION = "deprecation";
    private static final String NAME = "name";
    private static final String PROFILE = "profile";
    private static final String LENGTH = "length";

    private BuilderFactory factory = new DefaultBuilderFactory();
    private Map<String, Namespace> namespaces = new LinkedHashMap<>();
    private Map<String, Set<String>> arrayRelsByClass = new HashMap<String, Set<String>>();
    private Map<String, List<ConditionalLinkBuilder>> linkBuildersByClass = new LinkedHashMap<>();
    private List<ConditionalLinkBuilder> linkBuildersForClass;
    private ConditionalLinkBuilder linkBuilder;
    private Set<String> arrayRels;
    private String lastClassName;
    private Map<String, String> relNamesByClass = new HashMap<String, String>();

    public RelationshipDefinition builderFactory(BuilderFactory factory) {
        this.factory = factory;

        return this;
    }

    public RelationshipDefinition addNamespaces(Namespace... namespaces) {
        if (namespaces == null) {
            return this;
        }

        for (Namespace namespace : namespaces) {
            addNamespace(namespace);
        }

        return this;
    }

    public RelationshipDefinition addNamespace(Namespace namespace) {
        if (namespace == null) {
            return this;
        }

        if (namespaces.containsKey(namespace.name())) {
            throw new RelationshipException("Duplicate namespace: " + namespace.name());
        }

        namespaces.put(namespace.name(), namespace.clone());

        return this;
    }

    public RelationshipDefinition forCollectionOf(Class<?> forClass) {
        if (forClass == null) {
            return this;
        }

        return forClassName(forClass.getName() + COLLECTION_SUFFIX);
    }

    public RelationshipDefinition asRel(String name) {
        if (lastClassName == null) {
            throw new RelationshipException("Attempt to call asRel() before forCollectionOf()");
        }

        relNamesByClass.put(lastClassName, name);

        return this;
    }

    public String getCollectionRelFor(Class<?> forClass) {
        String rel = relNamesByClass.get(forClass.getName() + COLLECTION_SUFFIX);

        if (rel == null) {
            rel = Strings.pluralize(((Class<?>) forClass).getSimpleName().toLowerCase());
        }

        return rel;
    }

    public RelationshipDefinition forClass(Class<?> forClass) {
        if (forClass == null) {
            return this;
        }

        return forClassName(forClass.getName());
    }

    public RelationshipDefinition rel(String rel, String href) {
        return rel(rel, factory.newLinkBuilder(href));
    }

    public RelationshipDefinition rel(String rel, ConditionalLinkBuilder builder) {
        builder.rel(rel);
        this.linkBuilder = builder;

        if (linkBuildersForClass == null) {
            throw new RelationshipException("Attempt to call rel() before forClass() or forCollectionOf()");
        }

        linkBuildersForClass.add(builder);

        return this;
    }

    public RelationshipDefinition rels(String name, String href) {
        return rels(name, factory.newLinkBuilder(href));
    }

    public RelationshipDefinition rels(String name, ConditionalLinkBuilder builder) {
        arrayRels.add(name);

        return rel(name, builder);
    }

    public RelationshipDefinition title(String title) {
        return attribute(TITLE, title);
    }

    public RelationshipDefinition hreflang(String value) {
        return attribute(HREFLANG, value);
    }

    public RelationshipDefinition type(String type) {
        return attribute(TYPE, type);
    }

    public RelationshipDefinition name(String name) {
        return attribute(NAME, name);
    }

    public RelationshipDefinition templated(boolean value) {
        if (value) {
            return attribute(TEMPLATED, Boolean.TRUE.toString());
        }

        return attribute(TEMPLATED, null);
    }

    public RelationshipDefinition deprecation(String value) {
        return attribute(DEPRECATION, value);
    }

    public RelationshipDefinition profile(String value) {
        return attribute(PROFILE, value);
    }

    public RelationshipDefinition length(String value) {
        return attribute(LENGTH, value);
    }

    public RelationshipDefinition optional() {
        if (linkBuilder == null) {
            throw new RelationshipException("Attempt to set optional on null link. Call 'rel()' first.");
        }

        linkBuilder.optional();

        return this;
    }

    public RelationshipDefinition ifBound(String token) {
        if (linkBuilder == null) {
            throw new RelationshipException("Attempt to set ifBound() on null link: " + token +
                ". Call 'rel()' first.");
        }

        linkBuilder.ifBound(token);

        return this;
    }

    public RelationshipDefinition ifNotBound(String token) {
        if (linkBuilder == null) {
            throw new RelationshipException("Attempt to set ifNotBound() on null link: " + token +
                ". Call 'rel()' first.");
        }

        linkBuilder.ifNotBound(token);

        return this;
    }

    public RelationshipDefinition attribute(String name, String value) {
        if (linkBuilder == null) {
            throw new RelationshipException("Attempt to set attribute on null link: " + name + ". Call 'rel()' first.");
        }

        linkBuilder.set(name, value);

        return this;
    }

    public RelationshipDefinition withQuery(String querySegment) {
        if (linkBuilder == null) {
            throw new RelationshipException("Attempt to set query-string segment on null link: " + querySegment +
                ". Call 'rel()' first.");
        }

        linkBuilder.withQuery(querySegment);

        return this;
    }

    public List<LinkBuilder> getLinkBuilders(Class<?> forClass) {
        if (forClass == null) {
            return Collections.emptyList();
        }

        if (forClass.isArray()) {
            return getLinkBuildersForName(forClass.getComponentType().getName() + COLLECTION_SUFFIX);
        }

        return getLinkBuildersForName(forClass.getName());
    }

    public List<LinkBuilder> getCollectionLinkBuilders(Class<?> componentType) {
        if (componentType == null) {
            return Collections.emptyList();
        }

        return getLinkBuildersForName(componentType.getName() + COLLECTION_SUFFIX);
    }

    public Map<String, Namespace> getNamespaces() {
        return Collections.unmodifiableMap(namespaces);
    }

    public boolean isArrayRel(Class<?> objectType, String rel) {
        return isArrayRel(objectType.getName(), rel);
    }

    public boolean isCollectionArrayRel(Class<?> objectType, String rel) {
        return isArrayRel(objectType.getName() + COLLECTION_SUFFIX, rel);
    }

    private RelationshipDefinition forClassName(String name) {
        if (name == null) {
            return this;
        }

        lastClassName = name;
        linkBuildersForClass = linkBuildersByClass.get(name);

        if (linkBuildersForClass == null) {
            linkBuildersForClass = new ArrayList<>();
            linkBuildersByClass.put(name, linkBuildersForClass);
        }

        arrayRels = arrayRelsByClass.get(name);

        if (arrayRels == null) {
            arrayRels = new HashSet<String>();
            arrayRelsByClass.put(name, arrayRels);
        }

        return this;
    }

    private boolean isArrayRel(String className, String rel) {
        Set<String> rels = arrayRelsByClass.get(className);

        return (rels == null ? false : rels.contains(rel));
    }

    private List<LinkBuilder> getLinkBuildersForName(String className) {
        List<ConditionalLinkBuilder> builders = linkBuildersByClass.get(className);

        return (builders != null ? Collections.<LinkBuilder>unmodifiableList(builders)
                                 : Collections.<LinkBuilder>emptyList());
    }
}
