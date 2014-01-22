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
package com.strategicgains.hyperexpress.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * A LinkDefinition is a generic definition of a hypermedia link. It contains attributes
 * than can describe various output link formats such as HAL, Atom, and JSON-LD.
 * 
 * @author toddf
 * @since Oct 17, 2013
 */
public class LinkDefinition
{
	private static final String REL_TYPE = "rel";
	private static final String HREF = "href";

	private Map<String, String> attributes = new HashMap<String, String>();

	public LinkDefinition(String rel, String href)
    {
	    super();
	    setRel(rel);
	    setHref(href);
    }

	public String getHref()
	{
		return get(HREF);
	}

	public void setHref(String href)
	{
		set(HREF, href);
	}

	public String getRel()
	{
		return get(REL_TYPE);
	}

	public void setRel(String rel)
	{
		set(REL_TYPE, rel);
	}

	public void set(String name, String value)
	{
		attributes.put(name, value);
	}

	public String get(String name)
	{
		return attributes.get(name);
	}

	public boolean has(String name)
	{
		return (get(name) != null);
	}
}
