package com.strategicgains.hyperexpress.domain.hal.test;

import java.util.UUID;

public class Blog extends AbstractEntity {
    @Include
    private static final String IGNORED = "ignore this on resource creation";

    @Include
    private static double somethingStatic = 3.14597;

    @Exclude
    private String name;

    @Include
    private String description;
    private UUID ownerId;

    @Include
    private transient int somethingTransient = 5;

    @Include
    private volatile boolean somethingVolatile = true;

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
