package com.strategicgains.hyperexpress.expand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.exception.ExpansionException;

public class Expander {
    private static final Expander INSTANCE = new Expander();

    private Map<String, ExpansionCallback> callbacks = new HashMap<String, ExpansionCallback>();

    private Expander() {
        // prevents external instantiation.
    }

    public static Resource expand(Expansion expansion, Class<?> type, Resource resource) {
        return INSTANCE._expand(expansion, type, resource);
    }

    public static List<Resource> expand(Expansion expansion, Class<?> type, List<Resource> resources) {
        return INSTANCE._expand(expansion, type, resources);
    }

    public static Expander registerCallback(Class<?> type, ExpansionCallback callback) {
        return INSTANCE._registerCallback(type, callback);
    }

    private Expander _registerCallback(Class<?> type, ExpansionCallback callback) {
        if (callbacks.put(type.getName(), callback) != null) {
            throw new ExpansionException("Duplicate expansion callback registered for type: " + type.getName());
        }

        return this;
    }

    private Resource _expand(Expansion expansion, Class<?> type, Resource resource) {
        if (expansion == null) {
            return resource;
        }

        ExpansionCallback callback = callbacks.get(type.getName());

        return (callback == null ? resource : callback.expand(expansion, resource));
    }

    private List<Resource> _expand(Expansion expansion, Class<?> type, List<Resource> resources) {
        if (expansion == null) {
            return resources;
        }

        ExpansionCallback callback = callbacks.get(type.getName());

        if (callback == null) {
            return resources;
        }

        for (Resource resource : resources) {
            callback.expand(expansion, resource);
        }

        return resources;
    }
}
