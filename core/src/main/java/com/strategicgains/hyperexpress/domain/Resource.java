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

import java.util.Collection;
import java.util.Map;


/**
 * Resource defines an interface for a RESTful resource that contains one or more links to a URL.
 * 
 * @author toddf
 * @since Jan 10, 2014
 */
public interface Resource
{
	/**
	 * Add a namespace to the resource.
	 * 
	 * @param name the name of the namespace
	 * @param href the templated URL for documentation about the namespace
	 * @return this Resource instance (for method chaining).
	 */
	public Resource withNamespace(String name, String href);
	public String getNamespace(String name);
	public Map<String, String> getNamespaces();

	/**
	 * Add a single property to this resource.
	 * 
	 * @param name the name of the property.
	 * @param value the property's value.
	 * @return this Resource instance (for method chaining).
	 */
	public Resource withProperty(String name, Object value);
	public Object getProperty(String name);

	/**
	 * Embed another resource into this resource instance.
	 * 
	 * @param name
	 * @param resource
	 * @return
	 */
	public Resource withResource(String name, Resource resource);

	/**
	 * Define a link relationship from the resource to a URL.
	 * 
	 * @param linkDefinition an abstraction of a link as a LinkDefinition instance.
	 */
	public Resource withLink(LinkDefinition linkDefinition);

	/**
	 * Get a Map of links by Rel.
	 * 
	 * @return Map of links by Rel name.  Never null;
	 */
	public Map<String, Object> getLinks();

	/**
	 * Answer whether links are present.
	 * 
	 * @return true if links are present on the resource. Otherwise, false.
	 */
	public boolean hasLinks();

	/**
	 * Define a link relationship from the resource to a URL.
	 * 
	 * @param linkDefinition an abstraction of a link as a LinkDefinition instance.
	 */
	public Resource withLinks(Collection<LinkDefinition> links);

	/**
	 * Embed an entire collection in this resource.
	 * 
	 * @param rel
	 * @param resources
	 * @return
	 */
	Resource withCollection(String rel, Collection<? extends Object> resources);
}
