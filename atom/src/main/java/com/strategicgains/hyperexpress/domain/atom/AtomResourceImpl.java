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
package com.strategicgains.hyperexpress.domain.atom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.ResourceException;
import com.strategicgains.hyperexpress.domain.AbstractResource;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Resource;



/**
 * A HAL Resource instance, containing links and embedded resources.
 * 
 * @author toddf
 * @since May 21, 2013
 */
public class AtomResourceImpl
extends AbstractResource
implements AtomResource
{
    private static final long serialVersionUID = 2282309584744435597L;

	/**
	 * The reserved "links" property is OPTIONAL.
	 * 
	 * It is an object whose property names are link relation types (as
	 * defined by [RFC5988]) and values are an array of Link Objects.
	 */
	private static final String LINKS = "links";
	
	/**
	 * The reserved "embedded" property is OPTIONAL.
	 * 
	 * It is a heterogeneous collection of objects, where
	 * each embedded object is named by 'rel'
	 * 
	 * Embedded Resources MAY be a full, partial, or inconsistent version of
	 * the representation.
	 */
	private static final String EMBEDDED = "embedded";

	/**
	 * The reserved "items" property is OPTIONAL
	 * 
	 * If the resource is a collection, its elements are contained here
	 * in the 'items' collection.  Note that each item can be a resource
	 * with links, embedded objects, and items.
	 */
	private static final String ITEMS = "items";

	/*
	 * An internal representation of the links collection, indexed by 'rel'
	 */
	private transient Map<String, Object> linksByRel;
	private transient Map<String, String> namespaces;

	@Override
	public Resource withLink(Link link)
	{
		assertValid(link);
		List<AtomLink> links = acquireLinks();

		if (!hasLinks())
		{
			links = new ArrayList<AtomLink>();
		}

		links.add(new AtomLink(link));
		linksByRel = null; // cause re-indexing.
		return this;
	}

	@Override
	public Resource withLinks(Collection<Link> links)
	{
		if (links == null) return this;

		for (Link defn : links)
		{
			withLink(defn);
		}

		return this;
	}

	public boolean hasLinks()
	{
		return (getLinks0() != null);
	}

	@Override
	public Map<String, Object> getLinks()
	{
		List<AtomLink> links = getLinks0();

		if (links != null && linksByRel == null)
		{
			for (AtomLink link : links)
			{
				linksByRel.put(link.getRel(), link);
			}
		}

		return (linksByRel == null ? Collections.EMPTY_MAP : linksByRel);
	}

	/**
	 * Get the items in the underlying collection.  The returned collection is 
	 * unmodifiable.
	 * 
	 * @return the items in the collection.
	 */
	public Collection<Object> getItems()
	{
		Collection<Object> c = (Collection<Object>) get(ITEMS);
		return (c == null ? c : Collections.unmodifiableCollection(c));
	}

	/**
	 * Assigns the items collection to the underlying 'items' collection.
	 * 
	 * @param items
	 */
	public void setItems(Collection<Object> items)
	{
		put(ITEMS, items);
	}

	@Override
    public Resource withNamespace(String name, String href)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getNamespace(String name)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public Map<String, String> getNamespaces()
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public Object getProperty(String name)
    {
	    return get(name);
    }

	@Override
    public Resource withResource(String name, Resource resource)
    {
		List<Object> embedded = acquireEmbedded(name);
		embedded.add(resource);
	    return this;
    }

	@Override
    public Resource withCollection(String rel, Collection<? extends Object> resources)
    {
		List<Object> embedded = acquireEmbedded(rel);
		embedded.addAll(resources);
	    return this;
    }

	/**
	 * Returns the embedded resources by 'rel'. Never null.
	 * 
	 * @return a Map of embedded resources by 'rel'. Never null.
	 */
	@Override
	public Map<String, List<Object>> getEmbedded()
	{
		Map<String, List<Object>> e = getEmbedded0();
		return (e == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(e));
	}

	/**
	 * Return the embedded resources for a given 'rel'. Possibly null,
	 * if there are no embedded resources for that 'rel'.
	 * 
	 * @return a List of embedded resources, or null.
	 */
	public List<Object> getEmbedded(String rel)
	{
		return getEmbedded().get(rel);
	}

	private List<AtomLink> acquireLinks()
    {
		List<AtomLink> links = getLinks0();

	    if (links == null)
		{
			links = new ArrayList<AtomLink>();
			put(LINKS, links);
		}

		return links;
    }

	private List<AtomLink> getLinks0()
	{
		return (List<AtomLink>) get(LINKS);
	}

	private List<Object> acquireEmbedded(String rel)
    {
		Map<String, List<Object>> embedded = getEmbedded0();

	    if (embedded == null)
		{
			embedded = new HashMap<String, List<Object>>();
			put(EMBEDDED, embedded);
		}

		return embedded.get(rel);
    }

	private Map<String, List<Object>> getEmbedded0()
    {
		return (Map<String, List<Object>>) get(EMBEDDED);
    }

	/**
	 * Asserts that the LinkDefinition is not null and contains a 'rel' property, throwing
	 * ResourceException if not.
	 * 
	 * @param linkDefinition
	 * @throws ResourceException if the LinkDefinition is null or doesn't contain a 'rel' property
	 */
	private void assertValid(Link linkDefinition)
	{
		if (linkDefinition == null) throw new ResourceException("LinkDefinition cannot be null");

		if (!linkDefinition.has("rel")) throw new ResourceException("'rel' attribute is required");
	}
}
