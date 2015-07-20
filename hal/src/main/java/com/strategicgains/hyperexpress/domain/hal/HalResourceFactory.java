package com.strategicgains.hyperexpress.domain.hal;

import com.strategicgains.hyperexpress.AbstractResourceFactoryStrategy;
import com.strategicgains.hyperexpress.domain.Resource;

public class HalResourceFactory extends AbstractResourceFactoryStrategy {
    public Resource createResource(Object object) {
        Resource r = new HalResource();

        if (object != null) {
            copyProperties(object, r);
        }

        return r;
    }

    public Class<? extends Resource> getResourceType() {
        return HalResource.class;
    }
}
