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

import com.strategicgains.hyperexpress.ResourceException;

/**
 * @author toddf
 * @since Apr 7, 2014
 */
public class ResourceImpl
implements Resource
{
	private List<Namespace> namespaces = new ArrayList<Namespace>();
	private List<Link> links = new ArrayList<Link>();
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	private HashMap<String, List<Resource>> resources = new HashMap<String, List<Resource>>();

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

	protected Object getProperty(String key)
	{
		return properties.get(key);
	}

	protected Object putProperty(String key, Object value)
	{
		return properties.put(key, value);
	}

	public Resource addLink(String rel, String url)
	{
		return addLink(new LinkImpl(rel, url));
	}

	@Override
	public Resource addNamespace(String name, String href)
	{
		return addNamespace(new Namespace(name, href));
	}

	@Override
    public Resource addNamespace(Namespace namespace)
    {
		if (!namespaces.contains(namespace))
		{
			namespaces.add(namespace);
		}

	    return this;
    }

	@Override
	public List<Namespace> getNamespaces()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNamespaces()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasProperties()
	{
		return (!properties.isEmpty());
	}

	@Override
	public Resource addLink(Link link)
	{
		links.add(link);
		return this;
	}

	@Override
	public Resource addLinks(Collection<Link> links)
	{
		links.addAll(links);
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
