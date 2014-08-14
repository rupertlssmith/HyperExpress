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
package com.strategicgains.hyperexpress.expand;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.strategicgains.hyperexpress.domain.Resource;

/**
 * A set of requested relationship ('rel') expansions.
 * This class encompasses both the relation names to expand (presumably parsed from 
 * a request query-string) and the desired output media type (useful for calls
 * to HyperExpress.createResource() that might be made in the callbacks).
 * 
 * @author toddf
 * @since Jun 16, 2014
 */
public class Expansion
implements Iterable<String>
{
	/**
	 * The relation names (e.g. link rels) that were selected for link expansion.
	 */
	private Set<String> rels;

	/**
	 * The desired media type for newly-created {@link Resource} instances.
	 */
	private String mediaType;

	/**
	 * Construct a new Expansion instance using the given media type.
	 * 
	 * @param mediaType the desired media type for newly-created {@link Resource} instances.
	 */
	public Expansion(String mediaType)
	{
		super();
		this.mediaType = mediaType;
	}

	/**
	 * Construct a new Expansion instance at once, using the given media type
	 * and list of relationships.
	 * 
	 * @param mediaType the desired media type for newly-created {@link Resource} instances.
	 * @param rels a List of relationship names to expand. Possibly null.
	 */
	public Expansion(String mediaType, List<String> rels)
	{
		this(mediaType);
		this.rels = (rels == null ? null : new HashSet<String>(rels));
	}

	/**
	 * Add a relationship name to expand.
	 * 
	 * @param rel the name of a relationship that is desired to expand.
	 * @return this Expansion to facilitate method-chaining.
	 */
	public Expansion addExpansion(String rel)
	{
		if (rels == null)
		{
			rels = new HashSet<String>();
		}

		rels.add(rel);
		return this;
	}

	/**
	 * Answer whether this Expansion contains any relationship names.
	 * 
	 * @return true if this Expansion contains relationship names. Otherwise, false.
	 */
	public boolean isEmpty()
	{
		return (rels == null || rels.isEmpty());
	}

	/**
	 * Answer whether this Expansion contains the given relationship name.
	 * 
	 * @param rel the name of a relationship to check.
	 * @return true if the given relationship name is present. Otherwise, false.
	 */
	public boolean contains(String rel)
	{
		return rels.contains(rel);
	}

	/**
	 * Iterate over the relationship names contained in this Expansion.
	 * 
	 * @return an Iterator over the relationship names. Never null.
	 */
	public Iterator<String> iterator()
	{
		return (isEmpty() ? Collections.<String> emptySet().iterator() : rels.iterator());
	}

	/**
	 * Returns the desired media type for newly-created {@link Resource} instances.
	 * 
	 * @return the desired media type for newly-created {@link Resource} instances.
	 */
	public String getMediaType()
	{
		return mediaType;
	}
}
