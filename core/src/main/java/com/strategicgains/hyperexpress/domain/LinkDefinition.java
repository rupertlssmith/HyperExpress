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
import java.util.Map.Entry;

/**
 * A LinkImpl is a generic definition of a hypermedia link. It contains attributes
 * than can describe various output link formats such as HAL, Atom, and JSON-LD.
 * 
 * @author toddf
 * @since Oct 17, 2013
 */
public class LinkDefinition
implements Link
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

	public LinkDefinition(LinkDefinition that)
	{
		super();
		
		if (that != null) this.attributes.putAll(that.attributes);
	}

	@Override
    public LinkDefinition clone()
    {
		return new LinkDefinition(this);
    }

	@Override
    public String getHref()
	{
		return get(HREF);
	}

	@Override
    public LinkDefinition setHref(String href)
	{
		set(HREF, href);
		return this;
	}

	@Override
    public String getRel()
	{
		return get(REL_TYPE);
	}

	@Override
    public LinkDefinition setRel(String rel)
	{
		set(REL_TYPE, rel);
		return this;
	}

	@Override
    public LinkDefinition set(String name, String value)
	{
		if (value == null)
		{
			attributes.remove(name);
		}
		else
		{
			attributes.put(name, value);
		}

		return this;
	}

	@Override
    public String get(String name)
	{
		return attributes.get(name);
	}

	@Override
    public boolean has(String name)
	{
		return (get(name) != null);
	}

    @Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(this.getClass().getSimpleName());
		s.append("{");
		boolean isFirst = true;

		for (Entry<String, String> entry : attributes.entrySet())
		{
			if (!isFirst)
			{
				s.append(", ");
			}
			else
			{
				isFirst = false;
			}

			s.append(entry.getKey());
			s.append("=");
			s.append(entry.getValue());
		}

		s.append("}");
		return s.toString();
	}
}
