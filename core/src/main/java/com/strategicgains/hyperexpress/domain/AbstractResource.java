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
package com.strategicgains.hyperexpress.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.exception.ResourceException;

/**
 * An abstract implementation of the Resource interface.
 * 
 * @author toddf
 * @since Apr 7, 2014
 */
public abstract class AbstractResource
implements Resource
{
	private List<Namespace> namespaces = new ArrayList<Namespace>();
	private List<Link> links = new ArrayList<Link>();
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	private HashMap<String, List<Resource>> resources = new HashMap<String, List<Resource>>();

	/**
	 * Initialize the contents of this resource from another. The contents
	 * from the other resource are not copied, they are referenced (to
	 * reduce GC impacts).
	 * 
	 * @param that the resource from which to copy contents.
	 * @return this Resource instance (for method chaining).
	 */
	public Resource initialize(Resource that)
	{
		addNamespaces(that.getNamespaces());
		addLinks(that.getLinks());

		for (Map.Entry<String, Object> entry : that.getProperties().entrySet())
		{
			this.addProperty(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, List<Resource>> entry : that.getResources().entrySet())
		{
			this.addResources(entry.getKey(), entry.getValue());
		}

		return this;
	}

	/**
	 * Adds a property to this resource, ensuring the property name is unique.
	 * 
	 * @param name the property name
	 * @param value the value of the named property
	 * @throws ResourceException if the name of the property is not unique.
	 */
	@Override
	public Resource addProperty(String name, Object value)
	{
		if (properties.containsKey(name))
		{
			throw new ResourceException("Duplicate property: " + name);
		}

		properties.put(name, value);
		return this;
	}

	@Override
	public Object getProperty(String key)
	{
		return properties.get(key);
	}

	@Override
	public Resource setProperty(String key, Object value)
	{
		properties.put(key, value);
		return this;
	}

	@Override
	public Resource addLink(String rel, String url)
	{
		return addLink(new LinkDefinition(rel, url));
	}

	@Override
	public Resource addNamespace(String name, String href)
	{
		return addNamespace(new Namespace(name, href));
	}

	@Override
    public Resource addNamespace(Namespace namespace)
    {
		if (namespace == null) throw new ResourceException("Cannot add null namespace to resource");

		if (!namespaces.contains(namespace))
		{
			namespaces.add(namespace);
		}

	    return this;
    }

	@Override
	public Resource addNamespaces(Collection<Namespace> values)
	{
		if (values == null) return this;

		for (Namespace ns : values)
		{
			addNamespace(ns);
		}

		return this;
	}

	@Override
	public List<Namespace> getNamespaces()
	{
		return Collections.unmodifiableList(namespaces);
	}

	@Override
	public boolean hasNamespaces()
	{
		return !namespaces.isEmpty();
	}

	@Override
	public Map<String, Object> getProperties()
	{
		return Collections.unmodifiableMap(properties);
	}

	@Override
	public boolean hasProperties()
	{
		return (!properties.isEmpty());
	}

	@Override
	public Resource addLink(Link link)
	{
		if (link == null) throw new ResourceException("Cannot add null link to resource");

		this.links.add(link);
		return this;
	}

	@Override
	public Resource addLinks(Collection<Link> links)
	{
		if (links == null) throw new ResourceException("Cannot add null links collection to resource");

		this.links.addAll(links);
		return this;
	}

	@Override
	public List<Link> getLinks()
	{
		return Collections.unmodifiableList(links);
	}

	@Override
	public boolean hasLinks()
	{
		return (!links.isEmpty());
	}

	@Override
	public Resource addResource(String rel, Resource resource)
	{
		if (rel == null) throw new ResourceException("Cannot embed resource using null 'rel'");
		if (resource == null) throw new ResourceException("Cannot embed null resource");

		List<Resource> forRel = acquireResourcesForRel(rel);
		forRel.add(resource);
		return this;
	}

	@Override
	public Resource addResources(String rel, Collection<Resource> collection)
	{
		List<Resource> forRel = acquireResourcesForRel(rel);
		forRel.addAll(collection);
		return this;
	}

	@Override
	public Map<String, List<Resource>> getResources()
	{
		return Collections.unmodifiableMap(resources);
	}

	/**
	 * Get the embedded resources for a given relation type. The returned list is unmodifiable. Returns an
	 * empty list if there are no embedded resources.
	 * 
	 * @param rel the name of the relation for which to retrieve embedded resources.
	 * @return a list of embedded resources. Never null. 
	 */
    @Override
    @SuppressWarnings("unchecked")
	public List<Resource> getResources(String rel)
	{
		List<Resource> result = resources.get(rel);
		return (result == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(result));
	}

	/**
	 * Returns whether this resource has embedded resources or not.
	 * 
	 * @return true if this resource has embedded resources. Otherwise, false.
	 */
	@Override
	public boolean hasResources()
	{
		return (!resources.isEmpty());
	}

	private List<Resource> acquireResourcesForRel(String rel)
    {
	    List<Resource> forRel = resources.get(rel);

		if (forRel == null)
		{
			forRel = new ArrayList<Resource>();
			resources.put(rel, forRel);
		}

	    return forRel;
    }
}
