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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkImpl;
import com.strategicgains.hyperexpress.domain.Namespace;

/**
 * @author toddf
 * @since Apr 8, 2014
 */
public class RelationshipBuilder
{
	private static final String TITLE = "title";
	private static final String TYPE = "type";
	private static final String HREFLANG = "hreflang";

	// HAL-specific
	private static final String TEMPLATED = "templated";

	// HAL-specific
	private static final String DEPRECATION = "deprecation";

	// HAL-Specific
	private static final String NAME = "name";

	// HAL-Specific
	private static final String PROFILE = "profile";

	// Atom-specific
	private static final String LENGTH = "length";

	private Map<String, Namespace> namespaces = new LinkedHashMap<>();
	private Map<String, Map<String, Link>> relsByClass = new LinkedHashMap<>();
	private Map<String, Link> links;
	private Link link;

	/**
	 * Adds one or more namespaces to this relationship builder.
	 * 
	 * @param namespaces one or more Namespace instances.
	 * @return
	 */
	public RelationshipBuilder addNamespaces(Namespace... namespaces)
	{
		if (namespaces == null) return this;

		for (Namespace namespace : namespaces)
		{
			addNamespace(namespace);
		}

		return this;
	}

	/**
	 * Add a single Namespace to this relationship builder.
	 * 
	 * @param namespace a Namespace. Cannot be null.
	 * @return
	 */
	public RelationshipBuilder addNamespace(Namespace namespace)
	{
		if (namespace == null) return this;

		if (namespaces.containsKey(namespace.name()))
		{
			throw new RelationshipException("Duplicate namespace: " + namespace.name());
		}

		namespaces.put(namespace.name(), namespace.clone());
		return this;
	}

	public RelationshipBuilder forCollectionOf(Class<?> forClass)
	{
		if (forClass == null) return this;

		return forClassName(forClass.getName() + ".Collection");
	}

	public RelationshipBuilder forClass(Class<?> forClass)
	{
		if (forClass == null) return this;

		return forClassName(forClass.getName());
	}

	private RelationshipBuilder forClassName(String name)
	{
		if (name == null) return this;

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
		link = new LinkImpl(name, href);
		links.put(name, link);
		return this;
	}

	public RelationshipBuilder title(String title)
	{
		return attribute(TITLE, title);
	}

	public RelationshipBuilder hreflang(String value)
	{
		return attribute(HREFLANG, value);
	}

	public RelationshipBuilder type(String type)
    {
    	return attribute(TYPE, type);
    }

	/**
	 * HAL-specific.
	 * 
	 * @param name
	 * @return
	 */
	public RelationshipBuilder name(String name)
	{
		return attribute(NAME, name);
	}

	/**
	 * HAL-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipBuilder templated(boolean value)
	{
		if (value)
		{
			return attribute(TEMPLATED, Boolean.TRUE.toString());
		}

		return attribute(TEMPLATED, null);
	}

	/**
	 * HAL-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipBuilder deprecation(String value)
	{
		return attribute(DEPRECATION, value);
	}

	/**
	 * HAL-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipBuilder profile(String value)
	{
		return attribute(PROFILE, value);
	}

	/**
	 * Atom-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipBuilder length(String value)
	{
		return attribute(LENGTH, value);
	}

	/**
	 * General-purpose. Can be used to set arbitrary string-value properties on the link definition.
	 * May not actually show up in the output depending on whether the out-bound link format supports
	 * the attribute.
	 * </p>
	 * Must call 'rel()' before attribute() is called to create a new link.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public RelationshipBuilder attribute(String name, String value)
    {
		if (link == null) throw new RelationshipException("Attempt to set attribute on null link: " + name + ". Call 'rel()' first.");

		link.set(name, value);
    	return this;
    }

	public LinkResolver createResolver()
	{
		return new LinkResolver(this);
	}

	public Map<String, Link> getLinkTemplates(Class<?> forClass)
	{
		if (forClass == null) return Collections.emptyMap();

		if (forClass.isArray())
		{
			return getLinkTemplatesForName(forClass.getComponentType().getName() + ".Collection");
		}

		return getLinkTemplatesForName(forClass.getName());
	}

	public Map<String, Link> getCollectionLinkTemplates(Class<?> componentType)
	{
		if (componentType == null) return Collections.emptyMap();

		return getLinkTemplatesForName(componentType.getName() + ".Collection");
	}

	public Map<String, Namespace> getNamespaces()
	{
		return Collections.unmodifiableMap(namespaces);
	}

	private Map<String, Link> getLinkTemplatesForName(String className)
	{
		Map<String, Link> templates = relsByClass.get(className);
		
		return (templates != null ? Collections.unmodifiableMap(templates) : Collections.EMPTY_MAP);
	}
}
