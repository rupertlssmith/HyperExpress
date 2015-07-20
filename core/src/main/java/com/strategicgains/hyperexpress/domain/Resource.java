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
 * Resource defines an interface for a RESTful resource that contains one or more links to a URL and possibly, other
 * embedded resources.
 *
 * @author toddf
 * @since  Jan 10, 2014
 */
public interface Resource {
    /**
     * Add a namespace to the resource.
     *
     * @param  name the name of the namespace
     * @param  href the URL for documentation about the namespace
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addNamespace(String name, String href);

    /**
     * Add a namespace to the resource.
     *
     * @param  namespace the namespace to add to this resource
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addNamespace(Namespace namespace);

    /**
     * Add a collection of namespaces to the resource.
     *
     * @param  namespaces the collection of namespaces to add to this resource.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addNamespaces(Collection<Namespace> namespaces);

    /**
     * Get the list of namespaces attached to this resource.
     *
     * @return a list of Namespace instances. Possibly empty. Never null.
     */
    List<Namespace> getNamespaces();

    /**
     * Returns
     *
     * @return
     */
    boolean hasNamespaces();

    /**
     * Add a single property to this resource. Ensures the property name is unique.
     *
     * @param  name  the name of the property.
     * @param  value the property's value.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addProperty(String name, Object value);

    /**
     * Get a named property value from this resource.
     *
     * @param  name the name of the property to retrieve.
     *
     * @return a property value (as an Object), or null.
     */
    Object getProperty(String name);

    /**
     * Answer whether the property name is present on this resource.
     *
     * @param  name the name of the property.
     *
     * @return true if the property exists on the resource. Otherwise, false.
     */
    boolean hasProperty(String name);

    /**
     * Set a property value on this resource. If the property already exists on the resource, it is overwritten.
     *
     * @param  name  the name of the property.
     * @param  value the property's value.
     *
     * @return this Resource instance (for method chaining).
     *
     * @see    addProperty(String, Object)
     */
    Resource setProperty(String name, Object value);

    /**
     * Remove a property from this resource, returning its previous value, if it existed.
     *
     * @param  name the name of the property.
     *
     * @return the value of the property if it existed on the resource. Otherwise, false.
     */
    Object removeProperty(String name);

    /**
     * Get all the properties on this resource as a Map. The returned map is unmodifiable.
     *
     * @return the properties on this resource as name/value pairs in a Map implementer. Possibly empty. Never null.
     */
    Map<String, Object> getProperties();

    /**
     * Return whether this resource has properties on it.
     *
     * @return true if the resource contains properties. Otherwise, false.
     */
    boolean hasProperties();

    /**
     * Define a link relationship from the resource to a URL.
     *
     * @param  link       an abstraction of a link.
     * @param  isMultiple denote the rel as a list or array of links on rendering, if applicable.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addLink(Link link);

    /**
     * Define a link relationship from the resource to a URL, denoting the relationship as a collection.
     *
     * @param  link       an abstraction of a link.
     * @param  isMultiple denote the rel as a list or array of links on rendering, if applicable.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addLink(Link link, boolean isMultiple);

    /**
     * Define a link relationship from the resource to an href with the given relationship name.
     *
     * @param  rel  the name of the relationship, or 'rel type'
     * @param  href the URL to the resulting resource.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addLink(String rel, String href);

    /**
     * Define a link relationship from the resource to an href with the given relationship name, implying that the
     * relationship is a collection.
     *
     * <p/>As some media types (e.g. HAL) output different results depending on whether the link collection size is one,
     * or multiple, this method 'hints' to the serializer that this relationship should ALWAYS be an array (in the case
     * of JSON) or list. However, if the output media type doesn't support the concept, then this method and
     * addLink(String, String) have no difference.
     *
     * @param  rel        the name of the relationship, or 'rel type'
     * @param  href       the URL to the resulting resource.
     * @param  isMultiple denote the rel as a list or array of links on rendering, if applicable.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addLink(String rel, String href, boolean isMultiple);

    /**
     * Define link relationships from the resource to other URLs.
     *
     * <p/>Note that this method is not able to denote links for a given rel type as 'multiple' links. Output rendering
     * is therefore strictly dependent on how many links exist in the rel.
     *
     * @param  links a collection of link abstractions.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addLinks(Collection<Link> links);

    /**
     * Get a List of links from this resource to other URLs
     *
     * @return List of links. Possibly empty. Never null;
     */
    List<Link> getLinks();

    /**
     * Answer whether links are present on this resource.
     *
     * @return true if links are present on the resource. Otherwise, false.
     */
    boolean hasLinks();

    /**
     * Embed a single resource into this resource instance.
     *
     * @param  rel      the name of the relationship between this resource and the embedded resource.
     * @param  resource the resource instance to embed.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addResource(String rel, Resource resource);

    /**
     * Embed another resource into this resource instance. implying that the output is a collection of resources.
     *
     * <p/>As some media types (e.g. HAL) output different results depending on whether the collection size is one, or
     * multiple, this method 'hints' to the serializer that this relationship should ALWAYS be an array (in the case of
     * JSON) or list. However, if the output media type doesn't support the concept, then this method and
     * addResource(String, Resource) have no difference.
     *
     * @param  rel        the name of the relationship between this resource and the embedded resource.
     * @param  resource   the resource instance to embed.
     * @param  isMultiple denote the rel as a list or array of resources on rendering, if applicable.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addResource(String rel, Resource resource, boolean isMultiple);

    /**
     * Embed an entire collection of resources in this resource.
     *
     * @param  rel       the name of the relationship between this resource and the embedded resources.
     * @param  resources the collection of resources to embed.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource addResources(String rel, Collection<Resource> resources);

    /**
     * Get the embedded resources from this resource as a Map of name/value pairs. The value portion is defined as a
     * list, in case there are more-than one value associated with that rel name.
     *
     * @return a map containing the embedded resources, by relationship name (e.g. 'rel type'). Possibly empty. Never
     *         null.
     */
    Map<String, List<Resource>> getResources();

    /**
     * Get the embedded resource from this resource for the give relationship name.
     *
     * @param  rel the relationship name for which to retrieve embedded resources.
     *
     * @return a list of embedded resource instance. Possibly empty. Never null.
     */
    List<Resource> getResources(String rel);

    /**
     * Answer whether this resource has embedded resources or not.
     *
     * @return true if the resource has embedded resources. Otherwise, false.
     */
    boolean hasResources();

    /**
     * Answer whether this resource has an embedded resource for the given relationship name.
     *
     * @param  rel the relationship name for which to check for embedded resources.
     *
     * @return true if the resource has embedded resources for the given relationship. Otherwise, false.
     */
    boolean hasResources(String rel);

    /**
     * Initialize this resource from the contents of another.
     *
     * @param  from the Resource instance from which to copy.
     *
     * @return this Resource instance (for method chaining).
     */
    Resource from(Resource from);

    /**
     * Answers whether the rel is marked for multiple links when serialized (rendered). Put another way, this method
     * answers whether addLink(..., true) was called for that rel type.
     *
     * @param  rel
     *
     * @return true if the rel is supposed to be rendered as an array or list of links (instead of a single object).
     */
    boolean isMultipleLinks(String rel);

    /**
     * Answers whether the rel is marked for multiple resources when serialized (rendered). Put another way, this method
     * answers whether addResource(..., true) was called for that rel type.
     *
     * @param  rel
     *
     * @return true if the rel is supposed to be rendered as an array or list of resources (instead of a single object).
     */
    boolean isMultipleResources(String rel);
}
