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
package com.strategicgains.hyperexpress.builder;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.exception.RelationshipException;

/**
 * @author toddf
 * @since Apr 8, 2014
 */
public class RelationshipDefinition
{
    private static final String OPTIONAL = "optional";
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
	private Map<String, Map<String, LinkBuilder>> relsByClass = new LinkedHashMap<>();
	private Map<String, LinkBuilder> linkBuilders;
	private LinkBuilder linkBuilder;

	/**
	 * Adds one or more namespaces to this relationship builder.
	 * 
	 * @param namespaces one or more Namespace instances.
	 * @return
	 */
	public RelationshipDefinition addNamespaces(Namespace... namespaces)
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
	public RelationshipDefinition addNamespace(Namespace namespace)
	{
		if (namespace == null) return this;

		if (namespaces.containsKey(namespace.name()))
		{
			throw new RelationshipException("Duplicate namespace: " + namespace.name());
		}

		namespaces.put(namespace.name(), namespace.clone());
		return this;
	}

	public RelationshipDefinition forCollectionOf(Class<?> forClass)
	{
		if (forClass == null) return this;

		return forClassName(forClass.getName() + ".Collection");
	}

	public RelationshipDefinition forClass(Class<?> forClass)
	{
		if (forClass == null) return this;

		return forClassName(forClass.getName());
	}

	private RelationshipDefinition forClassName(String name)
	{
		if (name == null) return this;

		linkBuilders = relsByClass.get(name);

		if (linkBuilders == null)
		{
			linkBuilders = new HashMap<>();
			relsByClass.put(name, linkBuilders);
		}

		return this;
	}

	public RelationshipDefinition rel(String name, String href)
	{
		return rel(name, new LinkBuilder(href));
	}

	public RelationshipDefinition rel(String name, LinkBuilder builder)
	{
		linkBuilder = builder;
		linkBuilder.rel(name);
		linkBuilders.put(name, linkBuilder);
		return this;
	}

	public RelationshipDefinition title(String title)
	{
		return attribute(TITLE, title);
	}

	public RelationshipDefinition hreflang(String value)
	{
		return attribute(HREFLANG, value);
	}

	public RelationshipDefinition type(String type)
    {
    	return attribute(TYPE, type);
    }

	/**
	 * HAL-specific.
	 * 
	 * @param name
	 * @return
	 */
	public RelationshipDefinition name(String name)
	{
		return attribute(NAME, name);
	}

	/**
	 * HAL-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipDefinition templated(boolean value)
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
	public RelationshipDefinition deprecation(String value)
	{
		return attribute(DEPRECATION, value);
	}

	/**
	 * HAL-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipDefinition profile(String value)
	{
		return attribute(PROFILE, value);
	}

	/**
	 * Atom-specific.
	 * 
	 * @param value
	 * @return
	 */
	public RelationshipDefinition length(String value)
	{
		return attribute(LENGTH, value);
	}

	/**
	 * Indicates that the link should not be included in a response if it would be marked 'templated'.
	 * This is useful for links that are 'pagination' related, or otherwise conditional in the response.
	 * 
	 * @return
	 */
	public RelationshipDefinition optional()
	{
		return attribute(OPTIONAL, Boolean.TRUE.toString());
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
	public RelationshipDefinition attribute(String name, String value)
    {
		if (linkBuilder == null) throw new RelationshipException("Attempt to set attribute on null link: " + name + ". Call 'rel()' first.");

		linkBuilder.set(name, value);
    	return this;
    }

	/**
	 * Add an optional query-string argument to the latest rel(). If the query-string segment contains
	 * tokens and is not fully populate, it will not appear in the generated link.
	 * 
	 * @param querySegment an optional query-string segment. Optionally contains tokens.
	 * @return this RelationshipDefinition to facilitate method chaining.
	 */
	public RelationshipDefinition withQuery(String querySegment)
	{
		if (linkBuilder == null) throw new RelationshipException("Attempt to set query-string segment on null link: " + querySegment + ". Call 'rel()' first.");

		linkBuilder.withQuery(querySegment);
		return this;
	}

	public Map<String, LinkBuilder> getLinkBuilders(Class<?> forClass)
	{
		if (forClass == null) return Collections.emptyMap();

		if (forClass.isArray())
		{
			return getLinkBuildersForName(forClass.getComponentType().getName() + ".Collection");
		}

		return getLinkBuildersForName(forClass.getName());
	}

	public Map<String, LinkBuilder> getCollectionLinkBuilders(Class<?> componentType)
	{
		if (componentType == null) return Collections.emptyMap();

		return getLinkBuildersForName(componentType.getName() + ".Collection");
	}

	public Map<String, Namespace> getNamespaces()
	{
		return Collections.unmodifiableMap(namespaces);
	}

	@SuppressWarnings("unchecked")
    private Map<String, LinkBuilder> getLinkBuildersForName(String className)
	{
		Map<String, LinkBuilder> builders = relsByClass.get(className);
		
		return (builders != null ? Collections.unmodifiableMap(builders) : Collections.EMPTY_MAP);
	}
}
