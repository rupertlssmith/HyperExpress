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
import java.util.Collection;
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
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.siren.SirenAction;
import com.strategicgains.hyperexpress.domain.siren.SirenField;
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
	private static final String NAME = "name";
	private static final String METHOD = "method";
	private static final String HREF = "href";
	private static final String TYPE = "type";
	private static final String FIELDS = "fields";

	public SirenResourceSerializer()
	{
		super();
	}

	@Override
	public void serialize(SirenResource resource, JsonGenerator jgen, SerializerProvider provider)
	throws IOException, JsonProcessingException
	{
		jgen.writeStartObject();
		renderJson(resource, jgen);
		jgen.writeEndObject();
	}

	private void renderJson(SirenResource resource, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		writeClass(resource.getClasses(), jgen);
		writeTitle(resource.getTitle(), jgen);
		writeLinks(resource, jgen);
		writeEntities(resource, jgen);
		writeProperties(resource.getProperties(), jgen);
		writeActions(resource.getActions(), jgen);
	}

	private void writeClass(Collection<String> classes, JsonGenerator jgen)
	throws IOException
    {
		if (classes != null && !classes.isEmpty())
		{
			jgen.writeArrayFieldStart(CLASS);
			jgen.writeObject(classes);
			jgen.writeEndArray();
		}
    }

	private void writeTitle(String title, JsonGenerator jgen)
	throws IOException
    {
		if (title != null && !title.isEmpty())
		{
			jgen.writeObjectFieldStart(TITLE);
			jgen.writeObject(title);
			jgen.writeEndObject();
		}
    }

	private void writeLinks(SirenResource resource, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		List<Link> links = resource.getLinks();

		if (links.isEmpty()) return;

		jgen.writeArrayFieldStart(LINKS);
		Map<String, SirenLink> linksByRel = indexLinksByHref(resource.getLinks());

		for (SirenLink link : linksByRel.values())
		{
			jgen.writeObject(link);
		}

		jgen.writeEndArray();
	}

	/**
	 * Index the links by href, adding rels to the links with matching hrefs.
	 * 
	 * @param links
	 * @return
	 */
	private Map<String, SirenLink> indexLinksByHref(List<Link> links)
	{
		Map<String, SirenLink> linksByHref = new HashMap<String, SirenLink>();

		for (Link link : links)
		{
			SirenLink linkForHref = linksByHref.get(link.getHref());

			if (linkForHref == null)
			{
				linkForHref = new SirenLink(link);
				linksByHref.put(link.getHref(), linkForHref);
			}
			else
			{
				linkForHref.addRel(link.getRel());
			}
		}

		return linksByHref;
	}

	private void writeEntities(Resource resource, JsonGenerator jgen)
	throws JsonGenerationException, IOException
	{
		Map<String, List<Resource>> entities = resource.getResources();

		if (entities == null || entities.isEmpty()) return;

		jgen.writeArrayFieldStart(ENTITIES);

		for (Entry<String, List<Resource>> entry : entities.entrySet())
		{
			for (Resource r : entry.getValue())
			{
				jgen.writeStartObject();
				jgen.writeArrayFieldStart("rel");
				jgen.writeString(entry.getKey());
				jgen.writeEndArray();
				renderJson((SirenResource) r, jgen);
				jgen.writeEndObject();
			}
		}

		jgen.writeEndArray();
	}

	private void writeProperties(Map<String, Object> properties, JsonGenerator jgen)
	throws JsonProcessingException, IOException
	{
		if (properties == null || properties.isEmpty()) return;

		jgen.writeObjectFieldStart(PROPERTIES);

		for (Entry<String, Object> entry : properties.entrySet())
		{
			jgen.writeObjectField(entry.getKey(), entry.getValue());
		}

		jgen.writeEndObject();
	}

	private void writeActions(Collection<SirenAction> actions, JsonGenerator jgen)
	throws IOException
    {
		if (actions == null || actions.isEmpty()) return;
		
		jgen.writeArrayFieldStart(ACTIONS);

		for (SirenAction action : actions)
		{
			writeAction(action, jgen);
		}

		jgen.writeEndArray();
    }

	private void writeAction(SirenAction action, JsonGenerator jgen)
	throws IOException
    {
		if (action == null) return;

		jgen.writeStartObject();
		writeClass(action.getClasses(), jgen);
		jgen.writeStringField(NAME, action.getName());
		writeOptionalField("title", action.getTitle(), jgen);
		writeOptionalField(METHOD, action.getMethod(), jgen);
		jgen.writeStringField(HREF, action.getHref());
		writeOptionalField(TYPE, action.getType(), jgen);
		writeFields(action.getFields(), jgen);
		jgen.writeEndObject();
    }

	private void writeFields(List<SirenField> fields, JsonGenerator jgen)
	throws IOException
    {
		if (fields == null) return;

		jgen.writeArrayFieldStart(FIELDS);

		for (SirenField field : fields)
		{
			jgen.writeObject(field);
		}

		jgen.writeEndArray();
    }

	private void writeOptionalField(String name, String value, JsonGenerator jgen)
	throws IOException
	{
		if (value != null)
		{
			jgen.writeStringField(name, value);
		}
	}
}
