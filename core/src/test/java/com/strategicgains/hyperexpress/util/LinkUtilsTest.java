/*
    Copyright 2011, Strategic Gains, Inc.

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
package com.strategicgains.hyperexpress.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.strategicgains.hyperexpress.domain.Link;


/**
 * @author toddf
 * @since Apr 19, 2011
 */
public class LinkUtilsTest
{
	@Test
	public void shouldCreateLinks()
	{
		List<String> ids = Arrays.asList("one", "two", "three");
		List<Link> links = LinkUtils.asLinks(ids, "self", "someType", "getId", "/strings/{getId}", (String[]) null);
		assertNotNull(links);
		assertEquals(3, links.size());
		assertEquals("/strings/one", links.get(0).getHref());
		assertEquals("self", links.get(0).getRel());
		assertEquals("someType", links.get(0).getType());

		assertEquals("/strings/two", links.get(1).getHref());
		assertEquals("self", links.get(1).getRel());
		assertEquals("someType", links.get(1).getType());

		assertEquals("/strings/three", links.get(2).getHref());
		assertEquals("self", links.get(2).getRel());
		assertEquals("someType", links.get(2).getType());
	}

	@Test
	public void shouldCreateLinksUsingPairs()
	{
		List<String> ids = Arrays.asList("one", "two", "three");
		List<Link> links = LinkUtils.asLinks(ids, "related", "anotherType", "getId", "/{prefix}/{node}/{getId}", "prefix", "strings", "node", "simple");
		assertNotNull(links);
		assertEquals(3, links.size());
		assertEquals("/strings/simple/one", links.get(0).getHref());
		assertEquals("related", links.get(0).getRel());
		assertEquals("anotherType", links.get(0).getType());

		assertEquals("/strings/simple/two", links.get(1).getHref());
		assertEquals("related", links.get(1).getRel());
		assertEquals("anotherType", links.get(1).getType());

		assertEquals("/strings/simple/three", links.get(2).getHref());
		assertEquals("related", links.get(2).getRel());
		assertEquals("anotherType", links.get(2).getType());
	}
}
