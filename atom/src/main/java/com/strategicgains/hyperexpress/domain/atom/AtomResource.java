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
package com.strategicgains.hyperexpress.domain.atom;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Resource;

/**
 * @author toddf
 * @since Apr 21, 2014
 */
public interface AtomResource
extends Resource
{
	/**
	 * If the resource is a collection, get the items in the underlying collection.
	 * 
	 * @return the items in the collection.
	 */
	Collection<Object> getItems();

	/**
	 * Get embedded resources, indexed by 'rel'.
	 * 
	 * @return a Map of embedded resources by 'rel'. Never null.
	 */
	Map<String, List<Resource>> getEmbedded();

	/**
	 * Get the list of embedded objects for a given 'rel'. Possibly null,
	 * if the rel doesn't have any embedded objects.
	 * 
	 * @param rel the 'rel' type to retrieve embedded objects.
	 * @return a List of embedded objects for the give 'rel'. Or null.
	 */
	List<Resource> getEmbedded(String rel);
}
