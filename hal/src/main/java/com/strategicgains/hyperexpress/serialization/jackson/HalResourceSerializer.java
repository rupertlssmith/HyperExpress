package com.strategicgains.hyperexpress.serialization.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.hal.HalLink;
import com.strategicgains.hyperexpress.domain.hal.HalResource;

public class HalResourceSerializer extends JsonSerializer<HalResource> {
    private static final String CURIES = "curies";
    private static final String EMBEDDED = "_embedded";
    private static final String LINKS = "_links";

    public HalResourceSerializer() {
        super();
    }

    @Override
    public void serialize(HalResource resource, JsonGenerator jgen, SerializerProvider provider) throws IOException,
        JsonProcessingException {
        jgen.writeStartObject();
        renderJson(resource, jgen, false);
        jgen.writeEndObject();
    }

    private void renderJson(HalResource resource, JsonGenerator jgen, boolean isEmbedded)
        throws JsonGenerationException, IOException {
        writeLinks(resource, isEmbedded, jgen);
        writeEmbedded(resource, jgen);
        writeProperties(resource.getProperties(), jgen);
    }

    private void writeLinks(HalResource resource, boolean isEmbedded, JsonGenerator jgen)
        throws JsonGenerationException, IOException {
        List<Link> links = resource.getLinks();
        List<Namespace> namespaces = resource.getNamespaces();

        if (links.isEmpty() && (isEmbedded || namespaces.isEmpty())) {
            return;
        }

        jgen.writeObjectFieldStart(LINKS);
        writeCuries(namespaces, isEmbedded, jgen);

        Map<String, List<HalLink>> linksByRel = indexLinksByRel(resource.getLinks());

        for (Entry<String, List<HalLink>> entry : linksByRel.entrySet()) {
            if (entry.getValue().size() == 1 && !resource.isMultipleLinks(entry.getKey())) // Write single link
            {
                HalLink link = entry.getValue().iterator().next();

                if (null == link.getTemplated()) {
                    link.setTemplated(link.hasTemplate() ? true : null);
                }

                jgen.writeObjectField(entry.getKey(), link);
            } else
            {
                jgen.writeArrayFieldStart(entry.getKey());

                for (HalLink link : entry.getValue()) {
                    if (null == link.getTemplated()) {
                        link.setTemplated(link.hasTemplate() ? true : null);
                    }

                    jgen.writeObject(link);
                }

                jgen.writeEndArray();
            }

        }

        jgen.writeEndObject();
    }

    private Map<String, List<HalLink>> indexLinksByRel(List<Link> links) {
        Map<String, List<HalLink>> linksByRel = new HashMap<String, List<HalLink>>();

        for (Link link : links) {
            List<HalLink> linksForRel = linksByRel.get(link.getRel());

            if (linksForRel == null) {
                linksForRel = new ArrayList<HalLink>();
                linksByRel.put(link.getRel(), linksForRel);
            }

            linksForRel.add(new HalLink(link));
        }

        return linksByRel;
    }

    private void writeCuries(List<Namespace> namespaces, boolean isEmbedded, JsonGenerator jgen) throws IOException,
        JsonGenerationException, JsonProcessingException {
        if (isEmbedded || namespaces.isEmpty()) {
            return;
        }

        if (namespaces.size() == 1) // Write single namespace
        {
            jgen.writeObjectField(CURIES, namespaces.iterator().next());
        } else
        {
            jgen.writeArrayFieldStart(CURIES);

            for (Namespace ns : namespaces) {
                jgen.writeObject(ns);
            }

            jgen.writeEndArray();
        }
    }

    private void writeEmbedded(Resource resource, JsonGenerator jgen) throws JsonGenerationException, IOException {
        Map<String, List<Resource>> embedded = resource.getResources();

        if (embedded.isEmpty()) {
            return;
        }

        jgen.writeObjectFieldStart(EMBEDDED);

        for (Entry<String, List<Resource>> entry : embedded.entrySet()) {
            if (entry.getValue().size() == 1 && !resource.isMultipleResources(entry.getKey())) {
                jgen.writeObjectFieldStart(entry.getKey());
                renderJson((HalResource) entry.getValue().iterator().next(), jgen, true);
                jgen.writeEndObject();
            } else {
                jgen.writeArrayFieldStart(entry.getKey());

                for (Resource r : entry.getValue()) {
                    jgen.writeStartObject();
                    renderJson((HalResource) r, jgen, true);
                    jgen.writeEndObject();
                }

                jgen.writeEndArray();
            }
        }

        jgen.writeEndObject();
    }

    private void writeProperties(Map<String, Object> properties, JsonGenerator jgen) throws JsonProcessingException,
        IOException {
        if (properties.isEmpty()) {
            return;
        }

        for (Entry<String, Object> entry : properties.entrySet()) {
            jgen.writeObjectField(entry.getKey(), entry.getValue());
        }
    }
}
