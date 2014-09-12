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
package com.strategicgains.hyperexpress.serialization.siren.jackson;

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
import com.strategicgains.hyperexpress.domain.siren.SirenLink;
import com.strategicgains.hyperexpress.domain.siren.SirenResource;

/**
 * @author toddf
 * @since Sep 12, 2014
 */
public class SirenResourceSerializer
extends JsonSerializer<SirenResource>
{
	private static final String CLASS = "class";
	private static final String TITLE = "title";
	private static final String PROPERTIES = "properties";
	private static final String ENTITIES = "entities";
	private static final String LINKS = "links";
	private static final String ACTIONS = "actions";

	public SirenResourceSerializer()
	{
		super();
	}

	@Override
	public void serialize(SirenResource resource, JsonGenerator jgen, SerializerProvider provider)
	throws IOException, JsonProcessingException
	{
		jgen.writeStartObject();
		renderJson(resource, jgen, false);
		jgen.writeEndObject();
	}

	private void renderJson(SirenResource resource, JsonGenerator jgen, boolean isEmbedded)
	throws JsonGenerationException, IOException
	{
		writeLinks(resource, isEmbedded, jgen);
		writeEmbedded(resource, jgen);
		writeProperties(resource.getProperties(), jgen);
	}

	private void writeLinks(SirenResource resource, boolean isEmbedded, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		List<Link> links = resource.getLinks();
		List<Namespace> namespaces = resource.getNamespaces();
		if (links.isEmpty() && (isEmbedded || namespaces.isEmpty())) return;

		jgen.writeObjectFieldStart(LINKS);

		Map<String, List<SirenLink>> linksByRel = indexLinksByRel(resource.getLinks());

		for (Entry<String, List<SirenLink>> entry : linksByRel.entrySet())
		{
			if (entry.getValue().size() == 1 && !resource.isMultipleLinks(entry.getKey())) // Write single link
			{
				SirenLink link = entry.getValue().iterator().next();
				jgen.writeObjectField(entry.getKey(), link);
			}
			else // Write link array
			{
				jgen.writeArrayFieldStart(entry.getKey());

				for (SirenLink link : entry.getValue())
				{
					jgen.writeObject(link);
				}

				jgen.writeEndArray();
			}

		}

		jgen.writeEndObject();
	}

	private Map<String, List<SirenLink>> indexLinksByRel(List<Link> links)
	{
		Map<String, List<SirenLink>> linksByRel = new HashMap<String, List<SirenLink>>();

		for (Link link : links)
		{
			List<SirenLink> linksForRel = linksByRel.get(link.getRel());

			if (linksForRel == null)
			{
				linksForRel = new ArrayList<SirenLink>();
				linksByRel.put(link.getRel(), linksForRel);
			}

			linksForRel.add(new SirenLink(link));
		}

		return linksByRel;
	}

	private void writeEmbedded(Resource resource, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		Map<String, List<Resource>> embedded = resource.getResources();

		if (embedded.isEmpty()) return;

		jgen.writeObjectFieldStart(ENTITIES);

		for (Entry<String, List<Resource>> entry : embedded.entrySet())
		{
			if (entry.getValue().size() == 1 && !resource.isMultipleResources(entry.getKey()))
			{
				jgen.writeObjectFieldStart(entry.getKey());
                renderJson((SirenResource) entry.getValue().iterator().next(), jgen, true);
                jgen.writeEndObject();
			}
			else
			{
				jgen.writeArrayFieldStart(entry.getKey());

				for (Resource r : entry.getValue())
				{
					jgen.writeStartObject();
					renderJson((SirenResource) r, jgen, true);
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
