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
package com.strategicgains.hyperexpress.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * @author toddf
 * @since May 21, 2013
 * @deprecated Implement Resource or use HalResource, AtomResource, or HtlResource.
 */
public abstract class AbstractLinkable
implements Linkable
{
	/**
	 * The Link instance associated with the collection.
	 */
	private List<Link> links;

	/**
	 * Returns an unmodifiable List of the links.
	 */
	@Override
	public List<Link> getLinks()
	{
		return Collections.unmodifiableList(links);
	}

	/**
	 * Sets the links of links as a whole on the collection.
	 * 
	 * @param links a list of Link instances to assign to the collection.
	 */
	public void setLinks(List<Link> links)
	{
		this.links = new ArrayList<Link>(links);
	}

	/**
	 * Attach a link to the collection.
	 * 
	 * @param link an Link instance.
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
	 * Attach a Collection of Link instances to this LinkableCollection.
	 * 
	 * @param links a Collection of Link instances.
	 */
	public void addAllLinks(Collection<Link> links)
	{
		for (Link link : links)
		{
			addLink(link);
		}
	}
}
