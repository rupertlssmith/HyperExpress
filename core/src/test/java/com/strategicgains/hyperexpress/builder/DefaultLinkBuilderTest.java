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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.strategicgains.hyperexpress.RelTypes;
import com.strategicgains.hyperexpress.domain.Link;

/**
 * @author toddf
 * @since Oct 18, 2013
 */
public class DefaultLinkBuilderTest
{
	private static final String BASE_URL = "http://localhost:8081";
	private static final String URL_PATTERN = "/{id}";
	private static final String URL_PATTERN2 = "/{rootId}/{secondaryId}/{id}";

//	@Test
//	public void shouldBuildSimpleMultipleIdTemplate()
//	{
//		Collection<Link> links = new DefaultLinkBuilder(URL_PATTERN)
//			.baseUrl(BASE_URL)
//			.rel(RelTypes.RELATED)
//			.build("id", "42", "22", "4");
//
//		assertEquals(3, links.size());
//		int i = 0;
//
//		for (Link link : links)
//		{
//			if (i == 0)
//			{
//				assertEquals(BASE_URL + "/42", link.getHref());
//			}
//			else if (i == 1)
//			{
//				assertEquals(BASE_URL + "/22", link.getHref());				
//			}
//			else if (i == 2)
//			{
//				assertEquals(BASE_URL + "/4", link.getHref());
//			}
//
//			assertEquals(RelTypes.RELATED, link.getRel());
//			++i;
//		}
//	}

//	@Test
//	public void shouldBuildComplexMultipleIdTemplate()
//	{
//		Collection<Link> links = new DefaultLinkBuilder(URL_PATTERN2)
//			.baseUrl(BASE_URL)
//			.rel(RelTypes.DESCRIBED_BY)
//			.build("id", "42", "22", "4");
//
//
//		assertEquals(3, links.size());
//		int i = 0;
//
//		for (Link link : links)
//		{
//			if (i == 0)
//			{
//				assertEquals(BASE_URL + "/first/second/42", link.getHref());
//			}
//			else if (i == 1)
//			{
//				assertEquals(BASE_URL + "/first/second/22", link.getHref());
//			}
//			else if (i == 2)
//			{
//				assertEquals(BASE_URL + "/first/second/4", link.getHref());
//			}
//
//			assertEquals(RelTypes.DESCRIBED_BY, link.getRel());
//			++i;
//		}
//	}

	@Test
	public void shouldBuildSimpleSingleIdTemplate()
	{
		Link link = new DefaultLinkBuilder(URL_PATTERN)
			.baseUrl(BASE_URL)
			.rel(RelTypes.SELF)
			.build(new DefaultTokenResolver()
				.bind("id", "42")
			);
		
		assertEquals(BASE_URL + "/42", link.getHref());
		assertEquals(RelTypes.SELF, link.getRel());
	}

	@Test
	public void shouldBuildComplexSingleIdTemplate()
	{
		TokenResolver r = new DefaultTokenResolver()
			.bind("rootId", "first")
			.bind("secondaryId", "second")
			.bind("id", "42")
			.bind("ignored", "ignored");

		Link link = new DefaultLinkBuilder(URL_PATTERN2)
			.baseUrl(BASE_URL)
			.rel(RelTypes.DESCRIBED_BY)
			.build(r);
		
		assertEquals(BASE_URL + "/first/second/42", link.getHref());
		assertEquals(RelTypes.DESCRIBED_BY, link.getRel());
	}

	@Test
	public void shouldBuildComplexQueryString()
	{
		String expectedUrl = "http://someserver/myapp/report/1234?accountId=400&accountId=401&accountId=402";
		
		  //--- the list of IDs would be variable...
		List<String> accountIds = Arrays.asList("400", "401", "402");
		int i = 0;

		LinkBuilder lb = new DefaultLinkBuilder("/myapp/report/{reportId}")
			.baseUrl("http://someserver");
		TokenResolver r = new DefaultTokenResolver()
			.bind("reportId", "1234");

		for (String accountId : accountIds)
		{
			lb.withQuery("accountId={accountId" + i + "}");
			r.bind("accountId" + i, accountId);
			++i;
		}
	
		
		Link link = lb.build(r);
		assertEquals(expectedUrl, link.getHref());
	}

	@Test
	public void shouldBuildQueryStringWithNoResolver()
	{
		String expectedUrl = "http://someserver/myapp/report/1234?param1=val1";

		Link link = new DefaultLinkBuilder("/myapp/report/1234")
		    .baseUrl("http://someserver")
		    .withQuery("param1=val1")
		    .build();

		assertEquals(expectedUrl, link.getHref());
	}

	@Test
	public void shouldAllowMissingRel()
	{
		new DefaultLinkBuilder(URL_PATTERN).build(new DefaultTokenResolver());
	}
}
