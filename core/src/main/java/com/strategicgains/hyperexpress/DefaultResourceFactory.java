/*
    Copyright 2014, Strategic Gains, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.strategicgains.hyperexpress;

import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.exception.ResourceException;

/**
 * A ResourceFactory implementation that has no functionality on its own, but must have ResourceFactoryStrategy
 * implementations added to it, associating the strategy to a given content type.</p>
 *
 * Usage: ResourceFactory rf = new DefaultResourceFactory() .addStrategy(new HalResourceFactoryStrategy(),
 * "application/json") .addStrategy(new AtomResourceFactoryStrategy(), "application/xml");</p>
 *
 * Resource resource = rf.createResource(object, response.getContentType());
 *
 * @author toddf
 * @since  Apr 7, 2014
 */
public class DefaultResourceFactory implements ResourceFactory {
    private Map<String, ResourceFactoryStrategy> factoryStrategies = new HashMap<String, ResourceFactoryStrategy>();

    /**
     * Create a Resource using data from the given object for the requested content type.
     *
     * @param  object      the object to use as a source of properties (data).
     * @param  contentType the content type to use when creating the new resource.
     *
     * @return a concrete implementation of Resource with properties copied from object.
     *
     * @throws ResourceException if no factory exists for the contentType
     */
    @Override
    public Resource createResource(Object object, String contentType) {
        ResourceFactoryStrategy strategy = factoryStrategies.get(contentType);

        if (strategy == null) {
            throw new ResourceException("Cannot create resource for content type: " + contentType);
        }

        return strategy.createResource(object);
    }

    /**
     * Add a ResourceFactoryStrategy to create resource instances for the given content type.
     *
     * @param  strategy    the ResourceFactoryStrategy to use when creating new instances.
     * @param  contentType the content type the strategy should be invoked for.
     *
     * @return this DefaultResourceFactory to facilitate method chaining.
     *
     * @throws ResourceException if a strategy for a duplicate content type is added.
     */
    public DefaultResourceFactory addFactoryStrategy(ResourceFactoryStrategy strategy, String contentType) {
        if (factoryStrategies.containsKey(contentType)) {
            throw new ResourceException("Duplicate content type: " + contentType);
        }

        factoryStrategies.put(contentType, strategy);

        return this;
    }

    /**
     * Answer the concrete Resource implementation type. Create a Resource using data from the given object for the
     * requested content type.
     *
     * @param  object      the object to use as a source of properties (data).
     * @param  contentType the content type to use when creating the new resource.
     *
     * @return the Class of the concrete Resource implementation.
     *
     * @throws ResourceException if no factory exists for the contentType
     */
    @Override
    public Class<? extends Resource> getResourceType(String contentType) {
        ResourceFactoryStrategy strategy = factoryStrategies.get(contentType);

        if (strategy == null) {
            throw new ResourceException("No resource factory for content type: " + contentType);
        }

        return strategy.getResourceType();
    }
}
