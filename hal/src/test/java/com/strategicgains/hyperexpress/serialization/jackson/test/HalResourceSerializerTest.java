package com.strategicgains.hyperexpress.serialization.jackson.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.io.CharStreams;
import com.strategicgains.hyperexpress.builder.DefaultLinkBuilder;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.hal.HalResource;

import static org.junit.Assert.assertThat;

import com.strategicgains.hyperexpress.serialization.jackson.HalResourceSerializer;
import org.junit.BeforeClass;
import org.junit.Test;

import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

public class HalResourceSerializerTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        SimpleModule module = new SimpleModule();
        module.addSerializer(HalResource.class, new HalResourceSerializer());
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
    public void shouldSerializeSingleNamespace() throws JsonProcessingException {
        Resource r = new HalResource();
        r.addNamespace("ns:1", "/namespaces/1");

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "single-namespace.json");
    }

    @Test
    public void shouldSerializeNamespaceArray() throws JsonProcessingException {
        Resource r = new HalResource();
        r.addNamespace("ns:1", "/namespaces/1");
        r.addNamespace("ns:2", "/namespaces/2");

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "namespace-array.json");
    }

    @Test
    public void shouldSerializeSingleLink() throws JsonProcessingException {
        Resource r = new HalResource();
        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something").build());

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "single-link.json");
    }

    @Test
    public void shouldSerializeTemplated() throws JsonProcessingException {
        Resource r = new HalResource();
        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something/{templated}").build());

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "templated.json");
    }

    @Test
    public void shouldSerializeTemplatedArray() throws JsonProcessingException {
        Resource r = new HalResource();
        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
        r.addLink(l.rel("self").urlPattern("/something/not_templated").build());

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "templated-array.json");
    }

    @Test
    public void shouldSerializeLinkArray() throws JsonProcessingException {
        Resource r = new HalResource();
        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something").build());
        r.addLink(l.rel("self").urlPattern("/something/else").build());

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "link-array2.json");
    }

    @Test
    public void shouldSerializeAsLinkArray() throws JsonProcessingException {
        Resource r = new HalResource();
        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something").build(), true);

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "link-array.json");
    }

    @Test
    public void shouldSerializeProperties() throws JsonProcessingException {
        Resource r = new HalResource();
        r.addProperty("name", "A HAL resource");
        r.addProperty("value", new Integer(42));

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "properties.json");
    }

    @Test
    public void shouldSerializeSingleEmbed() throws JsonProcessingException {
        Resource r = new HalResource().addProperty("name", "root");
        r.addResource("children", new HalResource().addProperty("name", "child"));

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "single-embed.json");
    }

    @Test
    public void shouldSerializeEmbedAsArray() throws JsonProcessingException {
        Resource r = new HalResource().addProperty("name", "root");
        r.addResource("children", new HalResource().addProperty("name", "child"), true);

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "embed-as-array.json");
    }

    @Test
    public void shouldSerializeEmbeddedArray() throws JsonProcessingException {
        Resource r = new HalResource().addProperty("name", "root");
        r.addResource("children", new HalResource().addProperty("name", "child 1"));
        r.addResource("children", new HalResource().addProperty("name", "child 2"));

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "embedded-array.json");
    }

    @Test
    public void shouldSerializeResource() throws JsonProcessingException {
        Resource r = new HalResource().addProperty("name", "root");
        r.addNamespace("ns:1", "/namespaces/1");

        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something").build());
        r.addResource("children", new HalResource().addProperty("name", "child"));

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "resource.json");
    }

    @Test
    public void shouldSerializeResourceAsArrays() throws JsonProcessingException {
        Resource r = new HalResource().addProperty("name", "root");
        r.addNamespace("ns:1", "/namespaces/1");

        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something").build(), true);
        r.addResource("children", new HalResource().addProperty("name", "child"), true);

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "resource-as-arrays.json");
    }

    @Test
    public void shouldSerializeResourceWithArrays() throws JsonProcessingException {
        Resource r = new HalResource().addProperty("name", "root");
        r.addNamespace("ns:1", "/namespaces/1");
        r.addNamespace("ns:2", "/namespaces/2");

        LinkBuilder l = new DefaultLinkBuilder();
        r.addLink(l.rel("self").urlPattern("/something").build());
        r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
        r.addResource("children", new HalResource().addProperty("name", "child 1"));
        r.addResource("children", new HalResource().addProperty("name", "child 2"));

        String json = mapper.writeValueAsString(r);
        thenJsonShouldBeEqualTo(json, "resource-with-arrays.json");
    }

    public String fileContent(String filename) throws IOException {
        try(InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(filename))) {
            return CharStreams.toString(reader);
        }
    }

    protected void thenJsonShouldBeEqualTo(String checked, String filePath) {
        try {
            assertThat(checked, sameJSONAs(fileContent(filePath)).allowingExtraUnexpectedFields());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
