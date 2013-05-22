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
package com.strategicgains.hyperexpress.domain.htl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author toddf
 * @since May 21, 2013
 */
public abstract class AbstractLinkable
implements Linkable
{

	/**
	 * The Link instances associated with the object.
	 */
	private Map<String, Link> links;

	/**
	 * Returns an unmodifiable List of the links for the object.
	 */
	@Override
	public Map<String, Link> getLinks()
	{
		return Collections.unmodifiableMap(links);
	}

	/**
	 * Sets the links of links as a whole on the collection.
	 * 
	 * @param links a list of XLink instances to assign to the colleciton.
	 */
	@Override
	public void setLinks(Map<String, Link> links)
	{
		this.links = new HashMap<String, Link>(links);
	}

	/**
	 * Attach a link to the object.  Note that only one of each 'rel' type
	 * can be added without using the 'name' property.
	 * 
	 * @param link a Link instance.
	 */
	public void addLink(Link link)
	{
		if (links == null)
		{
			links = new HashMap<String, Link>();
		}
		
		if (link.hasName())
		{
			if (links.containsKey(link.getName()))
			{
				throw new UnsupportedOperationException("Duplicate 'name' property (must be unique): " + link.getName());
			}

			Link copy = new Link(link);
			copy.setName(null);  // Remove the Link's name property as the name is now the key.
			links.put(link.getName(), copy);
		}
		else
		{
			if (links.containsKey(link.getRel()))
			{
				throw new UnsupportedOperationException("Duplicate 'rel' property ('name' required): " + link.getRel());
			}

			links.put(link.getRel(), new Link(link));
		}
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
}
