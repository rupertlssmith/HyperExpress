package com.strategicgains.hyperexpress;

import com.strategicgains.hyperexpress.domain.Resource;

public interface ResourceFactory {
    Resource createResource(Object object, String contentType);

    Class<? extends Resource> getResourceType(String contentType);
}
