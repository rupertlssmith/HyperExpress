package com.strategicgains.hyperexpress.serialization.jackson;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.strategicgains.hyperexpress.BuilderFactory;
import com.strategicgains.hyperexpress.builder.DefaultBuilderFactory;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.domain.hal.HalResource;

public class HalResourceDeserializer extends JsonDeserializer<HalResource> {
    private static final String LINKS = "_links";
    private static final String CURIES = "curies";
    private static final String EMBEDDED = "_embedded";
    private static final Set<String> RESERVED_PROPERTIES = new HashSet<>(Arrays.asList(LINKS, EMBEDDED));

    private BuilderFactory factory;

    public HalResourceDeserializer() {
        super();
        factory = new DefaultBuilderFactory();
    }

    public HalResourceDeserializer(BuilderFactory factory) {
        super();
        this.factory = factory;
    }

    public HalResource deserialize(JsonParser jp, DeserializationContext context) throws IOException,
        JsonProcessingException {
        ObjectCodec oc = jp.getCodec();

        return deserializeResource((JsonNode) jp.readValueAsTree(), oc);
    }

    private HalResource deserializeResource(JsonNode root, ObjectCodec oc) throws JsonProcessingException, IOException {
        HalResource resource = new HalResource();
        processLinks(root.get(LINKS), resource, oc);
        processEmbedded(root.get(EMBEDDED), resource, oc);
        processProperties(root, resource);

        return resource;
    }

    private void processLinks(JsonNode links, HalResource resource, ObjectCodec oc) throws JsonProcessingException,
        IOException {
        if (links == null) {
            return;
        }

        processCuries(links.get(CURIES), resource, oc);

        Iterator<Entry<String, JsonNode>> fields = links.fields();

        while (fields.hasNext()) {
            Entry<String, JsonNode> field = fields.next();

            if (CURIES.equals(field.getKey())) {
                continue;
            }

            if (field.getValue().isArray()) {
                addAllLinks(resource, field);
            } else {
                addLink(resource, field);
            }
        }
    }

    private void processCuries(JsonNode curies, HalResource resource, ObjectCodec oc) throws JsonProcessingException,
        IOException {
        if (curies == null) {
            return;
        }

        if (curies.isArray()) {
            resource.addNamespaces(Arrays.asList(curies.traverse(oc).readValueAs(Namespace[].class)));
        } else {
            resource.addNamespace(curies.traverse(oc).readValueAs(Namespace.class));
        }
    }

    private void addAllLinks(HalResource resource, Entry<String, JsonNode> field) {
        LinkBuilder lb = factory.newLinkBuilder();
        Iterator<JsonNode> values = field.getValue().elements();

        while (values.hasNext()) {
            lb.rel(field.getKey());

            JsonNode value = values.next();
            processLinkProperties(lb, value);
            resource.addLink(lb.build());
            lb.clearAttributes();
        }
    }

    private void addLink(HalResource resource, Entry<String, JsonNode> field) {
        LinkBuilder lb = factory.newLinkBuilder();
        lb.rel(field.getKey());
        processLinkProperties(lb, field.getValue());
        resource.addLink(lb.build());
    }

    private void processLinkProperties(LinkBuilder lb, JsonNode value) {
        Iterator<Entry<String, JsonNode>> elements = value.fields();

        while (elements.hasNext()) {
            Entry<String, JsonNode> element = elements.next();

            if ("href".equals(element.getKey())) {
                lb.urlPattern(element.getValue().asText());
            } else {
                lb.set(element.getKey(), element.getValue().asText());
            }
        }
    }

    private void processEmbedded(JsonNode embedded, HalResource resource, ObjectCodec oc)
        throws JsonProcessingException, IOException {
        if (embedded == null) {
            return;
        }

        Iterator<Map.Entry<String, JsonNode>> fields = embedded.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> fieldEntry = fields.next();

            if (fieldEntry.getValue().isArray()) {
                Iterator<JsonNode> values = fieldEntry.getValue().elements();

                while (values.hasNext()) {
                    JsonNode value = values.next();
                    resource.addResource(fieldEntry.getKey(), deserializeResource(value, oc));
                }
            } else {
                resource.addResource(fieldEntry.getKey(), deserializeResource(fieldEntry.getValue(), oc));
            }
        }
    }

    private void processProperties(JsonNode root, HalResource resource) {
        Iterator<Entry<String, JsonNode>> fields = root.fields();

        while (fields.hasNext()) {
            Entry<String, JsonNode> fieldEntry = fields.next();

            if (!RESERVED_PROPERTIES.contains(fieldEntry.getKey())) {
                resource.setProperty(fieldEntry.getKey(), fieldEntry.getValue().asText());
            }
        }
    }
}
