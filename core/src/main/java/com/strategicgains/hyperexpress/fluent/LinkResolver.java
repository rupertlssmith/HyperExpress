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
package com.strategicgains.hyperexpress.fluent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.util.MapStringFormat;

/**
 * @author toddf
 * @since Apr 10, 2014
 */
public class LinkResolver
{
	private static final MapStringFormat FORMATTER = new MapStringFormat();

	private RelationshipBuilder relationshipBuilder;
	private Map<String, String> parameters = new HashMap<>();

	public LinkResolver(RelationshipBuilder builder)
    {
		super();
		this.relationshipBuilder = builder;
    }

	public LinkResolver with(String name, String value)
    {
		parameters.put(name, value);
		return this;
    }

	/**
	 * Clear the parameters for this LinkResolver.
	 */
	public void clear()
	{
		parameters.clear();
	}

	public List<Link> resolve(Object resource)
	{
		if (resource == null) throw new NullPointerException("Cannot resolve null resource");

		return resolve(resource.getClass());
	}

	public List<Link> resolve(Class<?> forClass)
	{
		Collection<Link> templates = relationshipBuilder.getLinkTemplates(forClass).values();

		if (templates == null) return Collections.emptyList();
		
		List<Link> links = new ArrayList<>(templates.size());

		for (Link template : templates)
		{
			Link link = template.clone();
			link.setHref(FORMATTER.format(link.getHref(), parameters));
			links.add(link);
		}

		return links;
	}

	public Collection<Link> getNamespaces()
	{
		return relationshipBuilder.getNamespaces().values();
	}
}
