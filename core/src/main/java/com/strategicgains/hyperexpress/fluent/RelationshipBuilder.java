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

import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.LinkDefinition;

/**
 * @author toddf
 * @since Apr 8, 2014
 */
public class RelationshipBuilder
{
	private Map<String, Namespace> namespaces = new HashMap<>();
	private Map<String, Map<String, LinkDefinition>> relsByClass = new HashMap<>();
	private Map<String, LinkDefinition> links;
	private LinkDefinition link;

	public void addNamespaces(Namespace[] namespaces)
	{
		for (Namespace namespace : namespaces)
		{
			addNamespace(namespace);
		}
	}

	public void addNamespace(Namespace namespace)
	{
		if (namespaces.containsKey(namespace.name()))
		{
			throw new RelationshipException("Duplicate namespace: " + namespace.name());
		}

		namespaces.put(namespace.name(), namespace);
	}

	public RelationshipBuilder forCollectionOf(Class<?> forClass)
	{
		return forClassName(forClass.getName() + ".Collection");
	}

	public RelationshipBuilder forClass(Class<?> forClass)
	{
		return forClassName(forClass.getName());
	}

	private RelationshipBuilder forClassName(String name)
	{
		links = relsByClass.get(name);

		if (links == null)
		{
			links = new HashMap<>();
			relsByClass.put(name, links);
		}

		return this;
	}

	public RelationshipBuilder rel(String name, String href)
	{
		return this;
	}

	public RelationshipBuilder rel(String name, String href, ParameterResolver resolver)
	{
		return this;
	}

	public RelationshipBuilder title(String title)
	{
		link.set("title", title);
		return this;
	}
}
