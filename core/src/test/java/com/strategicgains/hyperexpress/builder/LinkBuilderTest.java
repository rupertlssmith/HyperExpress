/*
    Copyright 2013, Strategic Gains, Inc.

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
package com.strategicgains.hyperexpress.builder;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import com.strategicgains.hyperexpress.RelTypes;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.domain.Link;

/**
 * @author toddf
 * @since Oct 18, 2013
 */
public class LinkBuilderTest
{
	private static final String BASE_URL = "http://localhost:8081";
	private static final String URL_PATTERN = "/{id}";
	private static final String URL_PATTERN2 = "/{rootId}/{secondaryId}/{id}";

	@Test
	public void shouldBuildSimpleMultipleIdTemplate()
	{
		Collection<Link> links = new LinkBuilder(URL_PATTERN)
			.baseUrl(BASE_URL)
			.rel(RelTypes.RELATED)
			.build("id", "42", "22", "4");

		assertEquals(3, links.size());
		int i = 0;

		for (Link link : links)
		{
			if (i == 0)
			{
				assertEquals(BASE_URL + "/42", link.getHref());
			}
			else if (i == 1)
			{
				assertEquals(BASE_URL + "/22", link.getHref());				
			}
			else if (i == 2)
			{
				assertEquals(BASE_URL + "/4", link.getHref());
			}

			assertEquals(RelTypes.RELATED, link.getRel());
			++i;
		}
	}

	@Test
	public void shouldBuildComplexMultipleIdTemplate()
	{
		Collection<Link> links = new LinkBuilder(URL_PATTERN2)
			.baseUrl(BASE_URL)
			.rel(RelTypes.DESCRIBED_BY)
			.bindToken("secondaryId", "second")
			.bindToken("rootId", "first")
			.build("id", "42", "22", "4");


		assertEquals(3, links.size());
		int i = 0;

		for (Link link : links)
		{
			if (i == 0)
			{
				assertEquals(BASE_URL + "/first/second/42", link.getHref());
			}
			else if (i == 1)
			{
				assertEquals(BASE_URL + "/first/second/22", link.getHref());
			}
			else if (i == 2)
			{
				assertEquals(BASE_URL + "/first/second/4", link.getHref());
			}

			assertEquals(RelTypes.DESCRIBED_BY, link.getRel());
			++i;
		}
	}

	@Test
	public void shouldBuildSimpleSingleIdTemplate()
	{
		Link link = new LinkBuilder(URL_PATTERN)
			.baseUrl(BASE_URL)
			.rel(RelTypes.SELF)
			.bindToken("id", "42")
			.build();
		
		assertEquals(BASE_URL + "/42", link.getHref());
		assertEquals(RelTypes.SELF, link.getRel());
	}

	@Test
	public void shouldBuildComplexSingleIdTemplate()
	{
		Link link = new LinkBuilder(URL_PATTERN2)
			.baseUrl(BASE_URL)
			.rel(RelTypes.DESCRIBED_BY)
			.bindToken("secondaryId", "second")
			.bindToken("rootId", "first")
			.bindToken("id", "42")
			.build();
		
		assertEquals(BASE_URL + "/first/second/42", link.getHref());
		assertEquals(RelTypes.DESCRIBED_BY, link.getRel());
	}

	@Test
	public void shouldAllowMissingRel()
	{
		new LinkBuilder(URL_PATTERN).build();
	}
}
