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
