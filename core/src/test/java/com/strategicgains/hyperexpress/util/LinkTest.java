/*
    Copyright 2012, Strategic Gains, Inc.

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

import static org.junit.Assert.*;

import org.junit.Test;

import com.strategicgains.hyperexpress.domain.Link;

/**
 * @author toddf
 * @since Oct 22, 2012
 */
public class LinkTest
{
	@Test
	public void shouldSetAllProperties()
	{
		Link l = new Link("rel", "href", "title", "type");
		assertEquals("rel", l.getRel());
		assertEquals("href", l.getHref());
		assertEquals("title", l.getTitle());
		assertEquals("type", l.getType());
	}

	@Test
	public void shouldCopyAllProperties()
	{
		Link l = new Link(new Link("rel", "href", "title", "type"));
		assertEquals("rel", l.getRel());
		assertEquals("href", l.getHref());
		assertEquals("title", l.getTitle());
		assertEquals("type", l.getType());
	}
}
