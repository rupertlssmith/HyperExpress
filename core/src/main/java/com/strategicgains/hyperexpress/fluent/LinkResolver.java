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
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.util.MapStringFormat;

/**
 * @author toddf
 * @since Apr 10, 2014
 */
public class LinkResolver
{
	private static final MapStringFormat FORMATTER = new MapStringFormat();

	private RelationshipDefinition relationshipBuilder;

	public LinkResolver(RelationshipDefinition builder)
    {
		super();
		this.relationshipBuilder = builder;
    }

	public List<Link> resolve(Object resource, TokenResolver idResolver)
	{
		if (resource == null) throw new NullPointerException("Cannot resolve null resource");

		return resolve(resource.getClass(), idResolver);
	}

	public List<Link> resolve(Class<?> forClass, TokenResolver idResolver)
	{
		Collection<Link> templates = relationshipBuilder.getLinkTemplates(forClass).values();

		if (templates.isEmpty()) return Collections.emptyList();

		return resolveUrlTokens(templates, idResolver);
	}

	public List<Link> resolveCollectionOf(Class<?> componentType, TokenResolver idResolver)
	{
		Collection<Link> templates = relationshipBuilder.getCollectionLinkTemplates(componentType).values();

		if (templates.isEmpty()) return Collections.emptyList();

		return resolveUrlTokens(templates, idResolver);
	}

	public Collection<Namespace> getNamespaces()
	{
		return relationshipBuilder.getNamespaces().values();
	}

	protected List<Link> resolveUrlTokens(Collection<Link> templates, TokenResolver idResolver)
    {
	    List<Link> links = new ArrayList<>(templates.size());
		Map<String, String> ids = idResolver.asMap();

		for (Link template : templates)
		{
			Link link = template.clone();
			String href = FORMATTER.format(link.getHref(), ids);
			link.setHref(href);

//			if (link.has("optional") && link.hasTemplate())
//			{
//				// ignore it.
//			}
//			else
//			{
				links.add(link);
//			}
		}

		return links;
    }
}
