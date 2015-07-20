package com.strategicgains.hyperexpress.domain.hal;

import java.util.UUID;

public abstract class AbstractEntity {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
