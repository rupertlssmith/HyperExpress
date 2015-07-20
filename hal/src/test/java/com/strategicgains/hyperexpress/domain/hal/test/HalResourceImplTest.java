package com.strategicgains.hyperexpress.domain.hal.test;

import java.util.List;

import com.strategicgains.hyperexpress.builder.DefaultTokenResolver;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.domain.hal.HalLinkBuilder;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import com.strategicgains.hyperexpress.exception.ResourceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class HalResourceImplTest {
    @Test(expected = ResourceException.class)
    public void shouldThrowOnMissingRel() {
        HalResource r = new HalResource();
        r.addLink(new HalLinkBuilder("/something").build(new DefaultTokenResolver()));
    }

    @Test(expected = ResourceException.class)
    public void shouldThrowOnNullLink() {
        HalResource r = new HalResource();
        r.addLink(null);
    }

    @Test(expected = ResourceException.class)
    public void shouldThrowOnNullEmbed() {
        HalResource r = new HalResource();
        r.addResource("something", null);
    }

    @Test(expected = ResourceException.class)
    public void shouldThrowOnNullEmbedRel() {
        HalResource r = new HalResource();
        r.addResource(null, new HalResource());
    }

    @Test(expected = ResourceException.class)
    public void shouldThrowOnNullCurie() {
        HalResource r = new HalResource();
        r.addNamespace(null);
    }

    @Test(expected = ResourceException.class)
    public void shouldThrowOnNamelessCurie() {
        HalResource r = new HalResource();
        r.addNamespace(new Namespace(null, "/sample/{rel}"));
    }

    @Test
    public void shouldAddCurieRel() {
        HalResource r = new HalResource();
        r.addNamespace(new Namespace("some-name", "/sample/{rel}"));
        r.addNamespace(new Namespace("another-name", "/sample/{rel}"));

        List<Namespace> curies = r.getNamespaces();
        assertNotNull(curies);
        assertEquals(2, curies.size());
    }

    @Test
    public void shouldAddNamespace() {
        HalResource r = new HalResource();
        r.addNamespace("ea:blah", "/sample/{rel}");

        List<Namespace> curies = r.getNamespaces();
        assertNotNull(curies);
        assertEquals(1, curies.size());
        assertEquals("/sample/{rel}", curies.get(0).href());
        assertEquals("ea:blah", curies.get(0).name());
    }
}
