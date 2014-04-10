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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.strategicgains.hyperexpress.domain.LinkDefinition;

/**
 * @author toddf
 * @since Apr 10, 2014
 */
public class LinkResolver
{
	private RelationshipBuilder relationshipBuilder;

	public LinkResolver(RelationshipBuilder builder)
    {
		super();
		this.relationshipBuilder = builder;
    }

	public LinkResolver with(String string, String string2)
    {
		return this;
    }

	public List<LinkDefinition> resolve(Object resource)
	{
		if (resource == null) throw new NullPointerException("Null resource");

		return resolve(resource.getClass());
	}

	public List<LinkDefinition> resolve(Class<?> forClass)
	{
		Map<String, LinkDefinition> templates = relationshipBuilder.getLinkTemplates(forClass);

		if (templates == null) return Collections.emptyList();

		for (Entry<String, LinkDefinition> template : templates.entrySet())
		{
			
		}

		return null;
	}
}
