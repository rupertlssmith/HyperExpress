package com.strategicgains.hyperexpress;

import com.strategicgains.hyperexpress.domain.Resource;

public class NullResourceFactoryStrategy extends AbstractResourceFactoryStrategy {
    @Override
    public Resource createResource(Object from) {
        Resource to = new AgnosticResource();

        if (from != null) {
            copyProperties(from, to);
        }

        return to;
    }

    @Override
    public Class<? extends Resource> getResourceType() {
        return AgnosticResource.class;
    }
}
