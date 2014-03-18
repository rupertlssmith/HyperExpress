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
package com.strategicgains.hyperexpress.domain.hal;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.strategicgains.hyperexpress.ResourceException;

/**
 * @author toddf
 * @since Mar 18, 2014
 */
public class HalResourceImplTest
{
	@Test(expected=ResourceException.class)
	public void shouldThrowOnMissingRel()
	{
		HalResource r = new HalResourceImpl();
		r.addLink(new HalLinkBuilder("/something").build());
	}

	@Test(expected=ResourceException.class)
	public void shouldThrowOnNullLink()
	{
		HalResource r = new HalResourceImpl();
		r.addLink(null);
	}

	@Test(expected=ResourceException.class)
	public void shouldThrowOnNullEmbed()
	{
		HalResource r = new HalResourceImpl();
		r.embed("something", null);
	}

	@Test(expected=ResourceException.class)
	public void shouldThrowOnNullEmbedRel()
	{
		HalResource r = new HalResourceImpl();
		r.embed(null, new HalResourceImpl());
	}

	@Test(expected=ResourceException.class)
	public void shouldThrowOnNullCurie()
	{
		HalResource r = new HalResourceImpl();
		r.addCurie(null);
	}

	@Test(expected=ResourceException.class)
	public void shouldThrowOnNamelessCurie()
	{
		HalResource r = new HalResourceImpl();
		r.addCurie(new HalLinkBuilder("/sample/{rel}").build());
	}

	@Test
	public void shouldAddCurieRel()
	{
		HalResource r = new HalResourceImpl();
		r.addCurie(new HalLinkBuilder("/sample/{rel}")
			.name("some-name")
			.rel("a-rel")
			.build());
		r.addCurie(new HalLinkBuilder("/sample/{rel}")
			.name("another-name")
			.rel("another-rel")
			.build());

		Map<String, Object> l = r.getLinks();
		assertEquals(1, l.size());
		List<HalLink> curies = (List<HalLink>) l.get("curies");
		assertNotNull(curies);
		assertEquals(2, curies.size());
	}
}
