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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.ResourceException;
import com.strategicgains.hyperexpress.domain.AbstractResource;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkDefinition;
import com.strategicgains.hyperexpress.domain.Resource;

/**
 * A HAL Resource instance, containing links, CURIEs and embedded resources.
 * 
 * @author toddf
 * @since May 21, 2013
 */
public class HalResourceImpl
extends AbstractResource
implements HalResource
{
    private static final long serialVersionUID = -8818645918407374522L;
	public static final String REL_CURIES = "curies";

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
	private static final String LINKS = "_links";

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
	private static final String EMBEDDED = "_embedded";

	/**
	 * Add a CURIE to this resource's _links collection.
	 * 
	 * CURIEs are established within a HAL document via a set of Link Objects
	 * with the relation type "curies" on the root Resource Object.  These links
	 * contain a URI Template with the token 'rel', and are named via the "name"
	 * property.
	 * 
	 * @param curie a LinkDefinition instance representing a CURIE
	 * @throws ResourceException if the CURIE doesn't have a name
	 * @see http://tools.ietf.org/html/draft-kelly-json-hal-06#page-8
	 */
	@Override
    public void addCurie(Link curie)
    {
		if (curie == null) throw new ResourceException("Cannot add null CURIE");
		assertHasName(curie);

		curie.setRel(null);
		Map<String, Object> _links = acquireLinks();
		Object listOrLink = _links.get(REL_CURIES);

		if (listOrLink == null)
		{
			List<HalLink> list = new ArrayList<HalLink>();
			list.add(new HalLink(curie));
			_links.put(REL_CURIES, list);
		}
		else
		{
			((List<HalLink>) listOrLink).add(new HalLink(curie));
		}
    }

	/**
	 * Add a collection of CURIEs to this resource.
	 * 
	 * @param curies a collection of LinkDefintion instance representing CURIEs
	 */
    @Override
    public void addCuries(Collection<LinkDefinition> curies)
    {
		if (curies == null) return;

		for (Link curie : curies)
		{
			addCurie(curie);
		}
    }

	/**
	 * Add a LinkDefinition to this resource. The LinkDefinition must be valid, in that,
	 * it must include a 'rel' property.
	 * 
	 * @param linkDefinition a populated, valid LinkDefinition
	 * @throws ResourceException if the LinkDefinition does not include a 'rel' property.
	 */
	@Override
	public Resource withLink(LinkDefinition linkDefinition)
	{
		assertValid(linkDefinition);

		Map<String, Object> _links = acquireLinks();
		Object listOrLink = _links.get(linkDefinition.getRel());
		
		if (listOrLink == null)	// Add a single Link
		{
			_links.put(linkDefinition.getRel(), new HalLink(linkDefinition));
		}
		else if (listOrLink.getClass().isAssignableFrom(ArrayList.class))	// Add Link to list.
		{
			((List<HalLink>) listOrLink).add(new HalLink(linkDefinition));
		}
		else // Convert to a list of Links
		{
			List<HalLink> list = new ArrayList<HalLink>();
			list.add((HalLink) listOrLink);
			list.add(new HalLink(linkDefinition));
			_links.put(linkDefinition.getRel(), list);
		}
		
		return this;
	}

	@Override
	public Resource withLinks(Collection<LinkDefinition> links)
	{
		if (links == null) return this;

		for (LinkDefinition defn : links)
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
		return Collections.unmodifiableMap(getLinks0());
	}

	public boolean hasEmbedded()
	{
		return (getEmbedded0() != null);
	}

	@Override
	public Map<String, Object> getEmbedded()
	{
		return Collections.unmodifiableMap(getEmbedded0());
	}

	@Override
    public Resource withResource(String rel, Resource resource)
	{
		if (rel == null) throw new ResourceException("'rel' is required for embedding");
		if (resource == null) throw new ResourceException("Cannot embed null resource");

		Map<String, Object> _embedded = acquireEmbedded();
		Object listOrResource = _embedded.get(rel);
		
		if (listOrResource == null) // Add a single resource.
		{
			_embedded.put(rel, resource);
		}
		else if (listOrResource.getClass().isAssignableFrom(ArrayList.class))	// Add a resource to the list.
		{
			((List<Object>) listOrResource).add(resource);
		}
		else // Convert a single resource to a list of resources
		{
			List<Object> list = new ArrayList<Object>();
			list.add(listOrResource);
			list.add(resource);
			_embedded.put(rel, list);
		}

		return this;
	}

	@Override
    public Resource withCollection(String rel, Collection<? extends Object> resources)
	{
		if (rel == null) throw new ResourceException("'rel' is required for embedding");
		if (resources == null) throw new ResourceException("Cannot embed null collection");

		Map<String, Object> _embedded = acquireEmbedded();
		Object listOrResource = _embedded.get(rel);
		
		if (listOrResource == null) // Create a new list.
		{
			_embedded.put(rel, new ArrayList<Object>(resources));
		}
		else if (listOrResource.getClass().isAssignableFrom(ArrayList.class))	// Add the resources to the list.
		{
			((List<Object>) listOrResource).addAll(resources);
		}
		else // Convert a single resource to a list of resources
		{
			List<Object> list = new ArrayList<Object>();
			list.add(listOrResource);
			list.addAll(resources);
			_embedded.put(rel, list);
		}

		return this;
	}

	@Override
    public Resource withNamespace(String name, String href)
    {
		HalLinkBuilder hlb = new HalLinkBuilder(href).name(name);
		addCurie(hlb.build());
	    return this;
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

	private void assertHasName(Link linkDefinition)
	{
		if (!linkDefinition.has("name")) throw new ResourceException("'name' attribute is required");
	}

	private Map<String, Object> acquireLinks()
    {
		Map<String, Object> _links = getLinks0();

	    if (_links == null)
		{
			_links = new HashMap<String, Object>();
			setLinks0(_links);
		}

		return _links;
    }

	private Map<String, Object> acquireEmbedded()
    {
		Map<String, Object> _embedded = getEmbedded0();

	    if (_embedded == null)
		{
			_embedded = new HashMap<String, Object>();
			setEmbedded0(_embedded);
		}

		return _embedded;
    }

	private Map<String, Object> getLinks0()
	{
		return (Map<String, Object>) get(LINKS);
	}

	private void setLinks0(Map<String, Object> value)
	{
		put(LINKS, value);
	}

	private Map<String, Object> getEmbedded0()
	{
		return (Map<String, Object>) get(EMBEDDED);
	}

	private void setEmbedded0(Map<String, Object> value)
	{
		put(EMBEDDED, value);
	}

	@Override
    public String getNamespace(String name)
    {
		return null;
    }

	@Override
    public Map<String, String> getNamespaces()
    {
		List<Link> curies = getCuries();
		Map<String, String> ns = new HashMap<>();

		for (Link curie : curies)
		{
			ns.put(curie.getRel(), curie.getHref());
		}

	    return ns;
    }

	@Override
    public Object getProperty(String name)
    {
	    return get(name);
    }

	@Override
    public List<Link> getCuries()
	{
		Map<String, Object> _links = acquireLinks();		
		Object listOrLink = _links.get(REL_CURIES);
		
		if (listOrLink != null)
		{
			if (List.class.isAssignableFrom(listOrLink.getClass()))
			{
				return (List<Link>) listOrLink;
			}
			else
			{
				return Arrays.asList((Link) listOrLink);
			}
		}

		return Collections.emptyList();
	}
}
