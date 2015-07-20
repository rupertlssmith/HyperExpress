package com.strategicgains.hyperexpress;

import com.strategicgains.hyperexpress.domain.Resource;

public interface ResourceFactoryStrategy {
    Resource createResource(Object object);

    Class<? extends Resource> getResourceType();
}
