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
package com.strategicgains.hyperexpress.domain.hal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.builder.LinkTemplate;



/**
 * A HAL Resource instance - For expressing the embedded nature of a given part of the representation.
 * 
 * @author toddf
 * @since May 21, 2013
 */
public class Resource
{
	/**
	 * The reserved "_links" property is OPTIONAL.
	 * 
	 * It is an object whose property names are link relation types (as
	 * defined by [RFC5988]) and values are either a Link Object or an array
	 * of Link Objects.
	 * 
	 * The relationship from the referencing object to the referenced object (e.g. rel=self).
	 * @see http://www.iana.org/assignments/link-relations/link-relations.xml
	 * 
	 * atom:link elements MAY have a "rel" attribute that indicates the link
	 * relation type.  If the "rel" attribute is not present, the link
	 * element MUST be interpreted as if the link relation type is "alternate".
	 * <p/>
	 * The value of "rel" MUST be a string that is non-empty and matches
	 * either the "isegment-nz-nc" or the "IRI" production in [RFC3987].
	 * <p/>
	 * Note that use of a relative reference other than a simple name is not
	 * allowed.  If a name is given, implementations MUST consider the link
	 * relation type equivalent to the same name registered within the IANA
	 */
	private Map<String, Object> _links;

	/**
	 * The reserved "_embedded" property is OPTIONAL
	 * 
	 * It is an object whose property names are link relation types (as
	 * defined by [RFC5988]) and values are either a Resource Object or an
	 * array of Resource Objects.
	 * 
	 * Embedded Resources MAY be a full, partial, or inconsistent version of
	 * the representation served from the target URI.
	 */
	private Map<String, Object> _embedded;

	public void linkTo(String rel, LinkTemplate linkTemplate)
	{
		if (!hasLinks())
		{
			_links = new HashMap<String, Object>();
		}

		Object listOrLink = _links.get(rel);
		
		if (listOrLink == null)	// Add a single Link
		{
			_links.put(rel, new Link(linkTemplate));
		}
		else if (listOrLink.getClass().isAssignableFrom(ArrayList.class))	// Add Link to list.
		{
			((List<Link>) listOrLink).add(new Link(linkTemplate));
		}
		else // Convert to a list of Links
		{
			List<Link> list = new ArrayList<Link>();
			list.add((Link) listOrLink);
			list.add(new Link(linkTemplate));
			_links.put(rel, list);
		}
	}

	public boolean hasLinks()
	{
		return (_links != null);
	}

	public Map<String, Object> getLinks()
	{
		return Collections.unmodifiableMap(_links);
	}

	public void setLinks(Map<String, Object> links)
	{
		this._links = (links == null ? null : new HashMap<String, Object>(links));
	}

	public Map<String, Object> getEmbedded()
	{
		return Collections.unmodifiableMap(_embedded);
	}

	public void embed(Object resource)
	{
		
	}
}
