/*
    Copyright 2012, Strategic Gains, Inc.

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
import java.util.List;

/**
 * A wrapper class that enables attaching links (Link instances) to an Object (such as
 * a domain object or DTO) before serialization to the client.
 * 
 * @author toddf
 * @since Oct 21, 2012
 */
public class LinkableObject<E>
implements Linkable
{
	/**
	 * The object that is being wrapped (that we are associating links to).
	 */
	private E item;
	
	/**
	 * The Link instances associated with the collection.
	 */
	private List<Link> links;

	// Simply here to facilitate JSON marshaling and persistence.
	public LinkableObject()
	{
		super();
	}

	public LinkableObject(E item)
	{
		this();
		setItem(item);
	}

	/**
	 * Returns an unmodifiable List of the links for the object.
	 */
	public List<Link> getLinks()
	{
		return Collections.unmodifiableList(links);
	}

	/**
	 * Sets the links of links as a whole on the collection.
	 * 
	 * @param links a list of XLink instances to assign to the colleciton.
	 */
	public void setLinks(List<Link> links)
	{
		this.links = new ArrayList<Link>(links);
	}

	/**
	 * Attach a link to the object.
	 * 
	 * @param link a Link instance.
	 */
	public void addLink(Link link)
	{
		if (links == null)
		{
			links = new ArrayList<Link>();
		}

		links.add(new Link(link));
	}

	/**
	 * Attached a Collection of Link instances to this object.
	 * 
	 * @param links a Collection of links.
	 */
	public void addAllLinks(Collection<Link> links)
	{
		for (Link link : links)
		{
			addLink(link);
		}
	}

	/**
	 * Get the underlying object.
	 * 
	 * @return the item underlying this LinkableObject instance.
	 */
	public E getItem()
	{
		return item;
	}

	/**
	 * Assigns the underlying item.  Note, there
	 * is no copying occurring in this operation, so it is possible for a caller
	 * to change the underlying object by changing the original instance.
	 * 
	 * @param item
	 */
	private void setItem(E item)
	{
		this.item = item;
	}
}
