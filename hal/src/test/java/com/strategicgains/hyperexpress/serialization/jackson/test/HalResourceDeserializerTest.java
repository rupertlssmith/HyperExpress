package com.strategicgains.hyperexpress.serialization.jackson.test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.builder.DefaultLinkBuilder;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.hal.HalResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.strategicgains.hyperexpress.serialization.jackson.HalResourceDeserializer;
import org.junit.BeforeClass;
import org.junit.Test;

public class HalResourceDeserializerTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(HalResource.class, new HalResourceDeserializer());
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, Visibility.NONE)
            .setVisibility(PropertyAccessor.SETTER, Visibility.NONE)
            .setVisibility(PropertyAccessor.IS_GETTER, Visibility.NONE)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
    }

    @Test
    public void shouldDeserializeLinkArray() throws IOException {
        String json =
            "{\"SessionId\":\"9d1b29fc-4240-4993-82c5-a42d00900324\",\"_links\":{\"self\":{\"href\":\"/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/scandata\"}," +
            "\"orderstate\":[  {\"href\":\"/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Accepted\",\"title\":\"Accept\"}," +
            "{\"href\":\"/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Declined\",\"title\":\"Decline\"} ]}}";
        HalResource hal = mapper.readValue(json, HalResource.class);
        assertTrue(hal.hasLinks());
        assertTrue(hal.hasProperties());
        assertEquals("9d1b29fc-4240-4993-82c5-a42d00900324", hal.getProperty("SessionId"));
        assertTrue(hal.getLinksByRel().containsKey("self"));
        assertTrue(hal.getLinksByRel().containsKey("orderstate"));

        List<Link> links = hal.getLinksByRel().get("orderstate");
        assertNotNull(links);
        assertEquals(2, links.size());
        assertEquals("/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Accepted",
            links.get(0).getHref());
        assertEquals("/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Declined",
            links.get(1).getHref());
    }

    @Test
    public void shouldDeserializeResourceWithArrays() throws IOException {
        HalResource halResource = whenReadingFromFile("resource-with-arrays.json");

        assertNotNull(halResource);
        assertEquals(halResource.getProperty("name"), "root");

        List<Namespace> namespaces = halResource.getNamespaces();
        assertNotNull(namespaces);
        assertEquals(namespaces.size(), 2);
        assertEquals(namespaces.get(0), new Namespace("ns:1", "/namespaces/1"));
        assertEquals(namespaces.get(1), new Namespace("ns:2", "/namespaces/2"));

        List<Link> links = halResource.getLinks();
        assertNotNull(links);
        assertEquals(links.size(), 2);

        LinkBuilder l = new DefaultLinkBuilder();

        assertEquals(links.get(0), l.rel("self").urlPattern("/something").build());
        assertEquals(links.get(1), l.rel("self").urlPattern("/something/{templated}").set("templated", "true").build());

        List<Resource> childrenList = halResource.getResources("children");
        assertNotNull(childrenList);
        assertEquals(childrenList.size(), 2);
        assertEquals(childrenList.get(0).getProperty("name"), "child 1");
        assertEquals(childrenList.get(1).getProperty("name"), "child 2");
    }

    private HalResource whenReadingFromFile(String filename) throws IOException {
        try(InputStream inputStream = this.getClass().getResourceAsStream(filename)) {
            return mapper.readValue(inputStream, HalResource.class);
        }
    }

}
