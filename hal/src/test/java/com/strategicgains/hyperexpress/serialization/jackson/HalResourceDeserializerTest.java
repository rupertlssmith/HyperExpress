/*
    Copyright 2015, Strategic Gains, Inc.

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.hal.HalResource;

/**
 * @author toddf
 * @since Feb 6, 2015
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

	@Test
	public void shouldDeserializeLinkArray()
	throws IOException
	{
		String json = "{\"SessionId\":\"9d1b29fc-4240-4993-82c5-a42d00900324\",\"_links\":{\"self\":{\"href\":\"/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/scandata\"},"
		+ "\"orderstate\":[  {\"href\":\"/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Accepted\",\"title\":\"Accept\"},"
		+ "{\"href\":\"/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Declined\",\"title\":\"Decline\"} ]}}";
		HalResource hal = mapper.readValue(json, HalResource.class);
		assertTrue(hal.hasLinks());
		assertTrue(hal.hasProperties());
		assertEquals("9d1b29fc-4240-4993-82c5-a42d00900324", hal.getProperty("SessionId"));
		assertTrue(hal.getLinksByRel().containsKey("self"));
		assertTrue(hal.getLinksByRel().containsKey("orderstate"));
		List<Link> links = hal.getLinksByRel().get("orderstate");
		assertNotNull(links);
		assertEquals(2, links.size());
		assertEquals("/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Accepted", links.get(0).getHref());
		assertEquals("/api/orders/9d1b29fc-4240-4993-82c5-a42d00900324/orderstate?orderState=Declined", links.get(1).getHref());
	}
}
