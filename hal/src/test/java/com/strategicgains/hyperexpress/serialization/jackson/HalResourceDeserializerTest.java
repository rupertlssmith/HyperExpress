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

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author azytsev
 * @since Feb 2, 2015
 */
public class HalResourceDeserializerTest
{
	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		SimpleModule module = new SimpleModule();
		module.addDeserializer(HalResource.class, new HalResourceDeserializer());
		mapper.registerModule(module);
		mapper
			.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			.setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
			.setVisibility(PropertyAccessor.GETTER, Visibility.NONE)
			.setVisibility(PropertyAccessor.SETTER, Visibility.NONE)
			.setVisibility(PropertyAccessor.IS_GETTER, Visibility.NONE)
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
	}

//	@Test
//	public void shouldSerializeSingleNamespace()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		r.addNamespace("ns:1", "/namespaces/1");
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"curies\":{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"}}}", json);
//	}
//
//	@Test
//	public void shouldSerializeNamespaceArray()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		r.addNamespace("ns:1", "/namespaces/1");
//		r.addNamespace("ns:2", "/namespaces/2");
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"curies\":[{\"name\":\"ns:1\",\"href\":\"/namespaces/1\"},{\"name\":\"ns:2\",\"href\":\"/namespaces/2\"}]}}", json);
//	}
//
//	@Test
//	public void shouldSerializeSingleLink()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something").build());
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"self\":{\"href\":\"/something\"}}}", json);
//	}
//
//	@Test
//	public void shouldSerializeTemplated()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"self\":{\"href\":\"/something/{templated}\",\"templated\":true}}}", json);
//	}
//
//	@Test
//	public void shouldSerializeTemplatedArray()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something/{templated}").build());
//		r.addLink(l.rel("self").urlPattern("/something/not_templated").build());
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"self\":[{\"href\":\"/something/{templated}\",\"templated\":true},{\"href\":\"/something/not_templated\"}]}}", json);
//	}
//
//	@Test
//	public void shouldSerializeLinkArray()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something").build());
//		r.addLink(l.rel("self").urlPattern("/something/else").build());
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"self\":[{\"href\":\"/something\"},{\"href\":\"/something/else\"}]}}", json);
//	}
//
//	@Test
//	public void shouldSerializeAsLinkArray()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something").build(), true);
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_links\":{\"self\":[{\"href\":\"/something\"}]}}", json);
//	}
//
//	@Test
//	public void shouldSerializeProperties()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource();
//		r.addProperty("name", "A HAL resource");
//		r.addProperty("value", new Integer(42));
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"name\":\"A HAL resource\",\"value\":42}", json);
//	}
//
//	@Test
//	public void shouldSerializeSingleEmbed()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource().addProperty("name", "root");
//		r.addResource("children", new HalResource().addProperty("name", "child"));
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_embedded\":{\"children\":{\"name\":\"child\"}},\"name\":\"root\"}", json);
//	}
//
//	@Test
//	public void shouldSerializeEmbedAsArray()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource().addProperty("name", "root");
//		r.addResource("children", new HalResource().addProperty("name", "child"), true);
//		String json = mapper.writeValueAsString(r);
//		assertEquals("{\"_embedded\":{\"children\":[{\"name\":\"child\"}]},\"name\":\"root\"}", json);
//	}

	@Test
	public void shouldDeserializeEmbeddedArray() throws IOException {
		HalResource resource = whenReadingFromFile("embedded-array.json");

		assertNotNull(resource);
		assertEquals(resource.getProperty("name"),"root");

		List<Resource> childrenList = resource.getResources("children");
		assertNotNull(childrenList);
		assertEquals(childrenList.size(),2);
		assertEquals(childrenList.get(0).getProperty("name"),"child 1");
		assertEquals(childrenList.get(1).getProperty("name"),"child 2");
	}

	private HalResource whenReadingFromFile(String filename) throws IOException {
		try(InputStream inputStream = this.getClass().getResourceAsStream(filename)) {
			return mapper.readValue(inputStream,HalResource.class);
		}
	}

//	@Test
//	public void shouldSerializeResource()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource().addProperty("name", "root");
//		r.addNamespace("ns:1", "/namespaces/1");
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something").build());
//		r.addResource("children", new HalResource().addProperty("name", "child"));
//		String json = mapper.writeValueAsString(r);
//		thenJsonShouldBeEqualTo(json,"resource.json");
//	}
//
//	@Test
//	public void shouldSerializeResourceAsArrays()
//	throws JsonProcessingException
//	{
//		Resource r = new HalResource().addProperty("name", "root");
//		r.addNamespace("ns:1", "/namespaces/1");
//		LinkBuilder l = new LinkBuilder();
//		r.addLink(l.rel("self").urlPattern("/something").build(), true);
//		r.addResource("children", new HalResource().addProperty("name", "child"), true);
//		String json = mapper.writeValueAsString(r);
//		thenJsonShouldBeEqualTo(json,"resource-as-arrays.json");
//	}

	@Test
	public void shouldDeserializeResourceWithArrays() throws IOException {

		HalResource halResource = whenReadingFromFile("resource-with-arrays.json");

		assertNotNull(halResource);
		assertEquals(halResource.getProperty("name"),"root");

		List<Namespace> namespaces = halResource.getNamespaces();
		assertNotNull(namespaces);
		assertEquals(namespaces.size(), 2);
		assertEquals(namespaces.get(0),new Namespace("ns:1", "/namespaces/1"));
		assertEquals(namespaces.get(1),new Namespace("ns:2", "/namespaces/2"));

		List<Link> links = halResource.getLinks();
		assertNotNull(links);
		assertEquals(links.size(),2);
		LinkBuilder l = new LinkBuilder();

		assertEquals(links.get(0),l.rel("self").urlPattern("/something").build());
		assertEquals(links.get(1), l.rel("self").urlPattern("/something/{templated}").set("templated", "true").build());

		List<Resource> childrenList = halResource.getResources("children");
		assertNotNull(childrenList);
		assertEquals(childrenList.size(),2);
		assertEquals(childrenList.get(0).getProperty("name"),"child 1");
		assertEquals(childrenList.get(1).getProperty("name"),"child 2");
	}


}
