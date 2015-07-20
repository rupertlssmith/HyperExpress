package com.strategicgains.hyperexpress;

import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.exception.ResourceException;

public class DefaultResourceFactory implements ResourceFactory {
    private Map<String, ResourceFactoryStrategy> factoryStrategies = new HashMap<String, ResourceFactoryStrategy>();

    public Resource createResource(Object object, String contentType) {
        ResourceFactoryStrategy strategy = factoryStrategies.get(contentType);

        if (strategy == null) {
            throw new ResourceException("Cannot create resource for content type: " + contentType);
        }

        return strategy.createResource(object);
    }

    public DefaultResourceFactory addFactoryStrategy(ResourceFactoryStrategy strategy, String contentType) {
        if (factoryStrategies.containsKey(contentType)) {
            throw new ResourceException("Duplicate content type: " + contentType);
        }

        factoryStrategies.put(contentType, strategy);

        return this;
    }

    public Class<? extends Resource> getResourceType(String contentType) {
        ResourceFactoryStrategy strategy = factoryStrategies.get(contentType);

        if (strategy == null) {
            throw new ResourceException("No resource factory for content type: " + contentType);
        }

        return strategy.getResourceType();
    }
}
