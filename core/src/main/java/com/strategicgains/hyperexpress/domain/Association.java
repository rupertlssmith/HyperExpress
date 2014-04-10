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

import java.lang.reflect.Type;

import com.strategicgains.hyperexpress.LinkBuilder;
import com.strategicgains.hyperexpress.ResourceException;


/**
 * @author toddf
 * @since Apr 7, 2014
 */
public class Association
{
	/**
	 * The source type.
	 */
	private Type from;

	/**
	 * The resulting or destination type.
	 */
	private Type to;

	/**
	 * True if the resulting of following the link is a collection of the 'to' type.
	 */
	private boolean isCollection;

	/**
	 * The URL, rel-type, etc. of the link.
	 */
	private LinkBuilder linkBuilder;

	public Association()
	{
		super();
	}

	public Association from(Type type)
	{
		this.from = type;
		return this;
	}

	public Type from()
	{
		return from;
	}

	public Association to(Type type)
	{
		this.to = type;
		return this;
	}

	public Type to()
	{
		return to;
	}

	public Association isCollection(boolean value)
	{
		this.isCollection = value;
		return this;
	}

	public boolean isCollection()
	{
		return isCollection;
	}

	public LinkBuilder link(String rel, String href)
	{
		this.linkBuilder = new LinkBuilder(href).rel(rel);
		return linkBuilder;
	}

	public AssociationIdentifier identifier()
	{
		if (linkBuilder == null)
		{
			throw new ResourceException("Link builder must be defined before identifier() is valid.");
		}

		return new AssociationIdentifier(from, linkBuilder.build().getRel(), to);
	}

	public class AssociationIdentifier
	{
		public String from;
		public String rel;
		public String to;
		
		public AssociationIdentifier(Type from, String rel, Type to)
		{
			super();
			this.from = from.getClass().getSimpleName();
			this.rel = rel;
			this.to = to.getClass().getSimpleName();
		}

		@Override
		public boolean equals(Object that)
		{
			return equals((AssociationIdentifier) that);
		}

		public boolean equals(AssociationIdentifier that)
		{
			return (this.from.equals(that.from) && this.rel.equals(that.rel) && this.to.equals(that.to));
		}

		@Override
		public int hashCode()
		{
			return (from + rel + to).hashCode();
		}

		@Override
		public String toString()
		{
			return "[" + from + "-->" + rel + "-->" + to + "]";
		}
	}
}
