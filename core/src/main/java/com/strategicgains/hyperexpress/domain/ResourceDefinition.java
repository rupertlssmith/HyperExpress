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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.ResourceException;


/**
 * @author toddf
 * @since Mar 25, 2014
 */
public class ResourceDefinition
implements Resource
{
	private Map<String, String> namespaces = new HashMap<String, String>();
	private List<LinkDefinition> links = new ArrayList<LinkDefinition>();
	private Map<String, Object> properties = new HashMap<String, Object>();
	private Map<String, Resource> resources = new HashMap<String, Resource>();

	@Override
    public Resource withNamespace(String name, String href)
    {
		if (namespaces.containsKey(name))
		{
			throw new ResourceException("Duplicate namespace: " + name);
		}

		namespaces.put(name, href);
		return this;
    }

	@Override
    public Resource withFields(Object object)
    {
		processFields(object.getClass(), object);
	    return this;
    }

	protected void processFields(Class<?> type, Object object)
    {
		if (type == null) return;

	    Field[] fields = getDeclaredFields(type);
		
	    try
	    {
			for (Field f : fields)
			{
				if ((f.getModifiers() & (Modifier.FINAL | Modifier.TRANSIENT | Modifier.STATIC | Modifier.VOLATILE)) == 0)
				{
					f.setAccessible(true);
					withProperty(f.getName(), f.get(object));
				}
			}
	    }
	    catch (IllegalAccessException e)
	    {
	    	throw new ResourceException(e);
	    }
		
		processFields(type.getSuperclass(), object);
    }

	private Field[] getDeclaredFields(Class<?> type)
	{
		return type.getDeclaredFields();
	}

	@Override
    public Resource withCollection(String name, Collection<? extends Object> collection)
    {
		if (collection == null) return this;

		if (resources.containsKey(name))
		{
			throw new ResourceException("Duplicate resource: " + name);
		}

		Resource root = new ResourceDefinition();
		resources.put(name, root);

		for (Object object : collection)
		{
			Resource embedded = new ResourceDefinition().withFields(object);
			root.withResource(name, embedded);
		}

	    return this;
    }

	@Override
    public Resource withProperty(String name, Object value)
    {
		if (properties.containsKey(name))
		{
			throw new ResourceException("Duplicate property: " + name);
		}

		properties.put(name, value);
	    return this;
    }

	/**
	 * @param name
	 * @param resource
	 */
	@Override
    public Resource withResource(String name, Resource resource)
    {
		if (resources.containsKey(name))
		{
			throw new ResourceException("Duplicate resource: " + name);
		}

		resources.put(name, resource);
		return this;
    }

	@Override
    public Resource withLink(LinkDefinition linkDefinition)
    {
		this.links.add(linkDefinition);
		return this;
    }

	@Override
    public Resource withLinks(Collection<LinkDefinition> linkDefinitions)
    {
		this.links.addAll(linkDefinitions);
		return this;
    }

	@Override
    public Resource withLink(String rel, String href, String title, String type)
    {
		LinkDefinition ld = new LinkDefinition(rel, href);
		ld.set("title", title);
		ld.set("type", type);
		return withLink(ld);
    }
}
