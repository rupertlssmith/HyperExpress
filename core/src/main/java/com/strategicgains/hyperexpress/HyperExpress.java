package com.strategicgains.hyperexpress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.strategicgains.hyperexpress.builder.DefaultBuilderFactory;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.builder.RelationshipDefinition;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Resource;

public class HyperExpress {
    private static final HyperExpress INSTANCE = new HyperExpress();

    private DefaultResourceFactory resourceFactory;
    private RelationshipDefinition relationshipDefinition;
    private ThreadLocal<TokenResolver> tokenResolver;
    private BuilderFactory builderFactory;

    private HyperExpress() {
        resourceFactory = new DefaultResourceFactory();
        relationshipDefinition = new RelationshipDefinition();
        tokenResolver = new ThreadLocal<TokenResolver>();
        builderFactory = new DefaultBuilderFactory();
    }

    public static void builderFactory(BuilderFactory factory) {
        INSTANCE.builderFactory = factory;
    }

    public static void registerResourceFactoryStrategy(ResourceFactoryStrategy factoryStrategy, String contentType) {
        INSTANCE._registerResourceFactoryStrategy(factoryStrategy, contentType);
    }

    public static Resource createResource(Object object, String contentType) {
        return INSTANCE._createResource(object, contentType);
    }

    public static Class<? extends Resource> getResourceType(String contentType) {
        return INSTANCE._getResourceType(contentType);
    }

    public static Resource createCollectionResource(Collection<?> components, Class<?> componentType,
        String contentType) {
        return INSTANCE._createCollectionResource(components, componentType, contentType);
    }

    public static Resource createCollectionResource(Collection<?> components, Class<?> componentType,
        String componentRel, String contentType) {
        return INSTANCE._createCollectionResource(components, componentType, componentRel, contentType);
    }

    public static RelationshipDefinition relationships() {
        return INSTANCE.relationshipDefinition;
    }

    public static void relationships(RelationshipDefinition relationships) {
        INSTANCE.relationshipDefinition = relationships;
    }

    public static TokenResolver bind(String token, String value) {
        return INSTANCE._bindToken(token, value);
    }

    public static <T> void tokenBinder(TokenBinder<T> callback) {
        INSTANCE._addTokenBinder(callback);
    }

    public static void clearTokenBindings() {
        INSTANCE._clearTokenBindings();
    }

    private void _registerResourceFactoryStrategy(ResourceFactoryStrategy factoryStrategy, String contentType) {
        resourceFactory.addFactoryStrategy(factoryStrategy, contentType);
    }

    private Resource _createResource(Object object, String contentType) {
        Resource r = resourceFactory.createResource(object, contentType);
        _assignResourceLinks(r, object, (object == null ? null : object.getClass()));

        return r;
    }

    private Class<? extends Resource> _getResourceType(String contentType) {
        return resourceFactory.getResourceType(contentType);
    }

    private Resource _createCollectionResource(Collection<?> components, Class<?> componentType, String contentType) {
        String componentRel = relationshipDefinition.getCollectionRelFor(componentType);

        return _createCollectionResource(components, componentType, componentRel, contentType);
    }

    private Resource _createCollectionResource(Collection<?> components, Class<?> componentType, String componentRel,
        String contentType) {
        Resource root = resourceFactory.createResource(null, contentType);
        Collection<LinkBuilder> templates = relationshipDefinition.getCollectionLinkBuilders(componentType);

        for (Link link : _resolveUrlTokens(templates, null, _acquireTokenResolver())) {
            root.addLink(link, relationshipDefinition.isCollectionArrayRel(componentType, link.getRel()));
        }

        root.addNamespaces(relationshipDefinition.getNamespaces().values());

        Resource childResource = null;

        if (components == null || components.isEmpty()) {
            root.addResources(componentRel, Collections.EMPTY_LIST);
        } else {
            boolean isResourceCollection = false;

            for (Object component : components) {
                if (isResourceCollection || Resource.class.isAssignableFrom(component.getClass())) {
                    isResourceCollection = true;
                    childResource = (Resource) component;
                    _assignResourceLinks(childResource, component, componentType);
                } else {
                    childResource = _createResource(component, contentType);
                }

                root.addResource(componentRel, childResource, true);
            }
        }

        return root;
    }

    private TokenResolver _bindToken(String token, String value) {
        return _acquireTokenResolver().bind(token, value);
    }

    private <T> TokenResolver _addTokenBinder(TokenBinder<T> callback) {
        return _acquireTokenResolver().binder(callback);
    }

    private void _clearTokenBindings() {
        TokenResolver tr = _getTokenResolver();

        if (tr != null) {
            tr.clear();
            tokenResolver.remove();
        }
    }

    private TokenResolver _acquireTokenResolver() {
        TokenResolver tr = _getTokenResolver();

        if (tr == null) {
            tr = builderFactory.newTokenResolver();
            tokenResolver.set(tr);
        }

        return tr;
    }

    private TokenResolver _getTokenResolver() {
        return tokenResolver.get();
    }

    private List<Link> _resolveUrlTokens(Collection<LinkBuilder> linkBuilders, Object object,
        TokenResolver tokenResolver) {
        List<Link> links = new ArrayList<>(linkBuilders.size());

        for (LinkBuilder linkBuilder : linkBuilders) {
            Link link = linkBuilder.build(object, tokenResolver);

            if (link != null) {
                links.add(link);
            }
        }

        return links;
    }

    private void _assignResourceLinks(Resource r, Object object, Class<?> objectType) {
        if (object != null) {
            Collection<LinkBuilder> linkBuilders = relationshipDefinition.getLinkBuilders(objectType);

            for (Link link : _resolveUrlTokens(linkBuilders, object, _acquireTokenResolver())) {
                r.addLink(link, relationshipDefinition.isArrayRel(objectType, link.getRel()));
            }
        }

        r.addNamespaces(relationshipDefinition.getNamespaces().values());
    }
}
