package com.strategicgains.hyperexpress.expand;

import com.strategicgains.hyperexpress.domain.Resource;

public interface ExpansionCallback {
    Resource expand(Expansion expansion, Resource resource);
}
