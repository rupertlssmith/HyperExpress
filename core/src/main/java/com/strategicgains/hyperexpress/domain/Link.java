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

/**
 * Defines an interface for a generalized hypermedia link, primarily containing a 'rel' (relationship name) and
 * 'href' (a URL to the related resource). Additionally, it implements Cloneable and can contain arbitrary
 * string-value properties. Hence, the 'generalized' term in use above.
 * 
 * @author toddf
 * @since Apr 3, 2014
 */
public interface Link
extends Cloneable
{
	/**
	 * Retrieve the 'href' value from this link.
	 * 
	 * @return the 'href' value for this link, or null.
	 */
	public String getHref();

	/**
	 * Set the 'href' value on this link.
	 * 
	 * @param href the URL or 'href' value to set on this link.
	 * @return this Link instance (for method chaining).
	 */
	public Link setHref(String href);

	/**
	 * Retrieve the 'rel' or relationship name of this link.
	 * 
	 * @return the 'rel' value for this link, or null.
	 */
	public String getRel();

	/**
	 * Set the 'rel' value (or relationship name) on this link.
	 * 
	 * @param rel the relationship name to set on this link. 
	 * @return this Link instance (for method chaining).
	 */
	public Link setRel(String rel);

	/**
	 * Set the value of an arbitrary property on this link by name. As different linking
	 * styles support different properties, this 'generalized' link interface supports
	 * them all by allowing the consumer to set any property name to a string value.
	 * <p/>
	 * If the property name is already present the value will be overwritten.
	 * 
	 * @param name the property name to set.
	 * @param value the string value of the property.
	 * @return this Link instance (for method chaining).
	 */
	public Link set(String name, String value);

	/**
	 * Retrieve the value of an arbitrary property from this link. Or null, if the property
	 * is not present.
	 * 
	 * @param name the name of the property for which to retrieve a value.
	 * @return the value of the property, or null.
	 */
	public String get(String name);

	/**
	 * Answer whether this link has a value set for the given property name.
	 * 
	 * @param name the name of the property for which to check for a value.
	 * @return true if the named property has been set on this link.
	 */
	public boolean has(String name);

	/**
	 * Create a matching copy of this link instance. The returned instance is new, with copies
	 * of the properties.
	 * 
	 * @return a new Link instance containing the same properties and values.
	 */
	public Link clone();
}