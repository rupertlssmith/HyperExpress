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

/**
 * @author toddf
 * @since May 2, 2014
 */
public class HalResourceSerializer
extends JsonSerializer<HalResource>
{

	private static final String CURIES = "curies";
	private static final String EMBEDDED = "_embedded";
	private static final String LINKS = "_links";

	/**
	 * Determines whether each rel in the _links property is forced to always use an array, or is dynamically either
	 * a single object or an array.
	 */
	private boolean forceLinkArrays = false;

	public HalResourceSerializer()
	{
		this(false);
	}

	public HalResourceSerializer(boolean shouldForceLinkArrays)
	{
		super();
		this.forceLinkArrays = shouldForceLinkArrays;
	}

	@Override
	public void serialize(HalResource resource, JsonGenerator jgen, SerializerProvider provider)
	throws IOException, JsonProcessingException
	{
		jgen.writeStartObject();
		renderJson(resource, jgen, false);
		jgen.writeEndObject();
	}

	private void renderJson(HalResource resource, JsonGenerator jgen, boolean isEmbedded)
	throws JsonGenerationException, IOException
	{
		writeLinks(resource.getLinks(), resource.getNamespaces(), isEmbedded, jgen);
		writeEmbedded(resource.getResources(), jgen);
		writeProperties(resource.getProperties(), jgen);
	}

	private void writeLinks(List<Link> links, List<Namespace> namespaces, boolean isEmbedded, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		if (links.isEmpty() && (isEmbedded || namespaces.isEmpty())) return;

		jgen.writeObjectFieldStart(LINKS);
		writeCuries(namespaces, isEmbedded, jgen);

		Map<String, List<HalLink>> linksByRel = indexLinksByRel(links);

		for (Entry<String, List<HalLink>> entry : linksByRel.entrySet())
		{
			if (!forceLinkArrays && entry.getValue().size() == 1) // Write single link
			{
				jgen.writeObjectField(entry.getKey(), entry.getValue().iterator().next());
			}
			else // Write link array
			{
				jgen.writeArrayFieldStart(entry.getKey());

				for (HalLink link : entry.getValue())
				{
					link.setTemplated(link.hasTemplate());
					jgen.writeObject(link);
				}

				jgen.writeEndArray();
			}

		}

		jgen.writeEndObject();
	}

	private Map<String, List<HalLink>> indexLinksByRel(List<Link> links)
	{
		Map<String, List<HalLink>> linksByRel = new HashMap<String, List<HalLink>>();

		for (Link link : links)
		{
			List<HalLink> linksForRel = linksByRel.get(link.getRel());

			if (linksForRel == null)
			{
				linksForRel = new ArrayList<HalLink>();
				linksByRel.put(link.getRel(), linksForRel);
			}

			linksForRel.add(new HalLink(link));
		}

		return linksByRel;
	}

	private void writeCuries(List<Namespace> namespaces, boolean isEmbedded, JsonGenerator jgen)
	throws IOException, JsonGenerationException, JsonProcessingException
	{
		if (isEmbedded || namespaces.isEmpty()) return;

		if (namespaces.size() == 1) // Write single namespace
		{
			jgen.writeObjectField(CURIES, namespaces.iterator().next());
		}
		else // Write namespace array
		{
			jgen.writeArrayFieldStart(CURIES);

			for (Namespace ns : namespaces)
			{
				jgen.writeObject(ns);
			}

			jgen.writeEndArray();
		}
	}

	private void writeEmbedded(Map<String, List<Resource>> embedded, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		if (embedded.isEmpty()) return;

		jgen.writeObjectFieldStart(EMBEDDED);

		for (Entry<String, List<Resource>> entry : embedded.entrySet())
		{
			if (entry.getValue().size() == 1)
			{
				jgen.writeObjectFieldStart(entry.getKey());
                renderJson((HalResource) entry.getValue().iterator().next(), jgen, true);
                jgen.writeEndObject();
			}
			else
			{
				jgen.writeArrayFieldStart(entry.getKey());

				for (Resource r : entry.getValue())
				{
					jgen.writeStartObject();
					renderJson((HalResource) r, jgen, true);
					jgen.writeEndObject();
				}

                jgen.writeEndArray();
			}
		}

		jgen.writeEndObject();
	}

	private void writeProperties(Map<String, Object> properties, JsonGenerator jgen)
	throws JsonProcessingException, IOException
	{
		if (properties.isEmpty()) return;

		for (Entry<String, Object> entry : properties.entrySet())
		{
			jgen.writeObjectField(entry.getKey(), entry.getValue());
		}
	}
}
