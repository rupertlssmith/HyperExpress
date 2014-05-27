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

import com.strategicgains.hyperexpress.exception.ResourceException;

/**
 * An immutable object representing a namespace, containing a name and an href (a URL).
 * 
 * @author toddf
 * @since Apr 8, 2014
 */
public class Namespace
implements Cloneable
{
	private String name;
	private String href;

	/**
	 * Required for deserialization. As there are no setters on this object.
	 */
	public Namespace()
	{
		super();
	}

	/**
	 * Create a new namespace, providing a name and href.
	 * 
	 * @param name the name of the namespace.
	 * @param href the URL for the namespace.
	 */
	public Namespace(String name, String href)
    {
		this();

		if (name == null) throw new ResourceException("Namespace name cannot be null");
		if (href == null) throw new ResourceException("Namespace href cannot be null");

		this.name = name;
		this.href = href;
    }

	/**
	 * Retrieve the namespace name.
	 * 
	 * @return the namespace name.
	 */
	public String name()
	{
		return name;
	}

	/**
	 * Retrieve the URL for the namespace.
	 * 
	 * @return the namespace href.
	 */
	public String href()
	{
		return href;
	}

	/**
	 * Clone this namespace, creating new instance matching this one.
	 * The resulting namespace will contain the same name and href.
	 * 
	 * @return a new namespace instance with matching name and href.
	 */
	public Namespace clone()
	{
		return new Namespace(name, href);
	}

	/**
	 * Return whether another object is 'equal to' this Namespace.
	 * 
	 * @param that another object to check for equality.
	 * @return true if the provided object is a Namespace and contains the same name and href. Otherwise, false.
	 */
	@Override
	public boolean equals(Object that)
	{
		if (this == that) return true;
		if (! (that instanceof Namespace)) return false;

		return equals((Namespace) that);
	}

	/**
	 * Return whether another Namespace instance contains the same name and href.
	 * 
	 * @param that a Namespace instance to check for equality.
	 * @return true if the other namespace contains the same href and name. Otherwise, false.
	 */
	public boolean equals(Namespace that)
	{
		if (that == null) return false;

		return (this.href.equals(that.href) && this.name.equals(that.name));
	}

	/**
	 * Returns a hash code for this Namespace based on the name and href.
	 * 
	 * @return a hash code for this namespace.
	 */
	@Override
	public int hashCode()
	{
		return 42 + name.hashCode() + href.hashCode();
	}
}
