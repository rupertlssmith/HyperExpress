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
import java.util.List;
import java.util.Map;


/**
 * Resource defines an interface for a RESTful resource that contains one or more links to a URL and
 * possibly, other embedded resources.
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
	Resource addNamespace(String name, String href);
	Resource addNamespace(Namespace namespace);
	Resource addNamespaces(Collection<Namespace> namespaces);
	List<Namespace> getNamespaces();
	boolean hasNamespaces();

	/**
	 * Add a single property to this resource. Ensures the property name is unique.
	 * 
	 * @param name the name of the property.
	 * @param value the property's value.
	 * @return this Resource instance (for method chaining).
	 */
	Resource addProperty(String name, Object value);
	Object getProperty(String name);
	Map<String, Object> getProperties();
	boolean hasProperties();

	/**
	 * Define a link relationship from the resource to a URL.
	 * 
	 * @param link an abstraction of a link.
	 */
	Resource addLink(Link link);
	Resource addLink(String rel, String href);

	/**
	 * Define a link relationship from the resource to a URL.
	 * 
	 * @param link an abstraction of a link.
	 */
	Resource addLinks(Collection<Link> links);

	/**
	 * Get a List of links.
	 * 
	 * @return List of links.  Never null;
	 */
	List<Link> getLinks();

	/**
	 * Answer whether links are present.
	 * 
	 * @return true if links are present on the resource. Otherwise, false.
	 */
	boolean hasLinks();

	/**
	 * Embed another resource into this resource instance.
	 * 
	 * @param name
	 * @param resource
	 * @return
	 */
	Resource addResource(String name, Resource resource);

	/**
	 * Embed an entire collection in this resource.
	 * 
	 * @param rel
	 * @param resources
	 * @return
	 */
	Resource addResources(String rel, Collection<Resource> resources);
	Map<String, List<Resource>> getResources();
	List<Resource> getResources(String rel);
	boolean hasResources();
}
