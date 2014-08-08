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

/**
 * A set of requested relationship ('rel') expansions.
 * 
 * @author toddf
 * @since Jun 16, 2014
 */
public class Expansion
implements Iterable<String>
{
	private Set<String> rels;
	private String mediaType;

	public Expansion(String mediaType)
	{
		super();
		this.mediaType = mediaType;
	}

	public Expansion(String mediaType, List<String> rels)
	{
		this(mediaType);
		this.rels = (rels == null ? null : new HashSet<String>(rels));
	}

	public Expansion addExpansion(String expansion)
	{
		if (rels == null)
		{
			rels = new HashSet<String>();
		}

		rels.add(expansion);
		return this;
	}

	public boolean isEmpty()
	{
		return (rels == null || rels.isEmpty());
	}

	public boolean contains(String value)
	{
		return rels.contains(value);
	}

	public Iterator<String> iterator()
	{
		return (isEmpty() ? Collections.<String> emptySet().iterator() : rels.iterator());
	}

	public String getMediaType()
	{
		return mediaType;
	}
}
