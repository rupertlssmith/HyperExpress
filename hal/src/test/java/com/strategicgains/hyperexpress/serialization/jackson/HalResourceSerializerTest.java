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

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.hal.HalResource;

/**
 * @author toddf
 * @since Aug 6, 2014
 */
public class HalResourceSerializerTest
{
	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		SimpleModule module = new SimpleModule();
		module.addSerializer(HalResource.class, new HalResourceSerializer());
		mapper.registerModule(module);
		mapper
			.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			.setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
			.setVisibility(PropertyAccessor.GETTER, Visibility.NONE)
			.setVisibility(PropertyAccessor.SETTER, Visibility.NONE)
			.setVisibility(PropertyAccessor.IS_GETTER, Visibility.NONE)
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
	}

	@Test
	public void shouldSerializeSingleNamespace()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		r.addNamespace("ns:1", "/namespaces/1");
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"curies\":{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"}}}", json);
	}

	@Test
	public void shouldSerializeNamespaceArray()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		r.addNamespace("ns:1", "/namespaces/1");
		r.addNamespace("ns:2", "/namespaces/2");
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"curies\":[{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"},{\"name\":\"ns:2\",\"href\":\"/namespaces/2\"}]}}", json);
	}

	@Test
	public void shouldSerializeSingleLink()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something").build());
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"self\":{\"href\":\"/something\"}}}", json);
	}

	@Test
	public void shouldSerializeTemplated()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"self\":{\"href\":\"/something/{templated}\",\"templated\":true}}}", json);
	}

	@Test
	public void shouldSerializeTemplatedArray()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
		r.addLink(l.rel("self").urlPattern("/something/not_templated").build());
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"self\":[{\"href\":\"/something/{templated}\",\"templated\":true},{\"href\":\"/something/not_templated\"}]}}", json);
	}

	@Test
	public void shouldSerializeLinkArray()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something").build());
		r.addLink(l.rel("self").urlPattern("/something/else").build());
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"self\":[{\"href\":\"/something\"},{\"href\":\"/something/else\"}]}}", json);
	}

	@Test
	public void shouldSerializeAsLinkArray()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something").build(), true);
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"self\":[{\"href\":\"/something\"}]}}", json);
	}

	@Test
	public void shouldSerializeProperties()
	throws JsonProcessingException
	{
		Resource r = new HalResource();
		r.addProperty("name", "A HAL resource");
		r.addProperty("value", new Integer(42));
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"name\":\"A HAL resource\",\"value\":42}", json);
	}

	@Test
	public void shouldSerializeSingleEmbed()
	throws JsonProcessingException
	{
		Resource r = new HalResource().addProperty("name", "root");
		r.addResource("children", new HalResource().addProperty("name", "child"));
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_embedded\":{\"children\":{\"name\":\"child\"}},\"name\":\"root\"}", json);
	}

	@Test
	public void shouldSerializeEmbedAsArray()
	throws JsonProcessingException
	{
		Resource r = new HalResource().addProperty("name", "root");
		r.addResource("children", new HalResource().addProperty("name", "child"), true);
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_embedded\":{\"children\":[{\"name\":\"child\"}]},\"name\":\"root\"}", json);
	}

	@Test
	public void shouldSerializeEmbeddedArray()
	throws JsonProcessingException
	{
		Resource r = new HalResource().addProperty("name", "root");
		r.addResource("children", new HalResource().addProperty("name", "child 1"));
		r.addResource("children", new HalResource().addProperty("name", "child 2"));
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_embedded\":{\"children\":[{\"name\":\"child 1\"},{\"name\":\"child 2\"}]},\"name\":\"root\"}", json);
	}

	@Test
	public void shouldSerializeResource()
	throws JsonProcessingException
	{
		Resource r = new HalResource().addProperty("name", "root");
		r.addNamespace("ns:1", "/namespaces/1");
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something").build());
		r.addResource("children", new HalResource().addProperty("name", "child"));
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"curies\":{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"},\"self\":{\"href\":\"/something\"}},\"_embedded\":{\"children\":{\"name\":\"child\"}},\"name\":\"root\"}", json);
	}

	@Test
	public void shouldSerializeResourceAsArrays()
	throws JsonProcessingException
	{
		Resource r = new HalResource().addProperty("name", "root");
		r.addNamespace("ns:1", "/namespaces/1");
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something").build(), true);
		r.addResource("children", new HalResource().addProperty("name", "child"), true);
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"curies\":{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"},\"self\":[{\"href\":\"/something\"}]},\"_embedded\":{\"children\":[{\"name\":\"child\"}]},\"name\":\"root\"}", json);
	}

	@Test
	public void shouldSerializeResourceWithArrays()
	throws JsonProcessingException
	{
		Resource r = new HalResource().addProperty("name", "root");
		r.addNamespace("ns:1", "/namespaces/1");
		r.addNamespace("ns:2", "/namespaces/2");
		LinkBuilder l = new LinkBuilder();
		r.addLink(l.rel("self").urlPattern("/something").build());
		r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
		r.addResource("children", new HalResource().addProperty("name", "child 1"));
		r.addResource("children", new HalResource().addProperty("name", "child 2"));
		String json = mapper.writeValueAsString(r);
		assertEquals("{\"_links\":{\"curies\":[{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"},{\"name\":\"ns:2\",\"href\":\"/namespaces/2\"}],\"self\":[{\"href\":\"/something\"},{\"href\":\"/something/{templated}\",\"templated\":true}]},\"_embedded\":{\"children\":[{\"name\":\"child 1\"},{\"name\":\"child 2\"}]},\"name\":\"root\"}", json);
	}
}
