package com.strategicgains.hyperexpress.test;

import com.strategicgains.hyperexpress.AbstractResourceFactoryStrategy;
import com.strategicgains.hyperexpress.domain.Resource;

public class NullResourceFactoryStrategy extends AbstractResourceFactoryStrategy {
    public Resource createResource(Object from) {
        Resource to = new AgnosticResource();

        if (from != null) {
            copyProperties(from, to);
        }

        return to;
    }

    public Class<? extends Resource> getResourceType() {
        return AgnosticResource.class;
    }
}
