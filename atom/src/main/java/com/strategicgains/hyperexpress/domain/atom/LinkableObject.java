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
package com.strategicgains.hyperexpress.domain.atom;


/**
 * A wrapper class that enables attaching links (Link instances) to an Object (such as
 * a domain object or DTO) before serialization to the client.
 * 
 * @author toddf
 * @since Oct 21, 2012
 * @deprecated Use AtomResource
 */
public class LinkableObject<E>
extends AbstractLinkable
{
	/**
	 * The object that is being wrapped (that we are associating links to).
	 */
	private E item;

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
