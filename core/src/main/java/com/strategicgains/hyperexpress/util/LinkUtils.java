/*
    Copyright 2010, Strategic Gains, Inc.

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
package com.strategicgains.hyperexpress.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Link;

/**
 * Non-instantiable class with foreign methods to create and manipulate hypermedia Link instances.
 * 
 * @author toddf
 * @since July 29, 2010
 * @deprecated Use LinkBuilder or the specific type builder, AtomLinkBuilder, HalLinkBuilder, in
 * the appropriate sub-module.
 */
public abstract class LinkUtils
{
	private static final MapStringFormat formatter = new MapStringFormat();

	private LinkUtils()
	{
		// This constructor prevents instantiation.
	}
	

	/**
	 * Creates a List of Link instances where the resulting path is the passed-in urlPath suffixed with
	 * one of the ids.
	 *  
	 * @param ids a Collection of identifiers to create Link instances for.
	 * @param rel the Link relationship from the referring object to the referred object (e.g. "self" or "related"). May be null.
	 * @param type the type of the referred object.
	 * @param paramName the URL parameter (e.g. 'personId' for {personId} in the URL) that these identifiers represent.
	 * @param urlPattern the URL to operate on the related object (e.g. READ).  There should be a parameter in it that matches the paramName, above.
	 * @param nameValuePairs is a sequence of name/value pairs, where the name matches parameters in urlPath and the value is what gets substituted.
	 * @return a List of Link instances.
	 */
	public static List<Link> asLinks(Collection<String> ids, String rel, String type, String paramName, String urlPattern, String... nameValuePairs)
	{
		List<Link> results = new ArrayList<Link>(ids.size());
		Map<String, String> parameters = MapStringFormat.toMap(nameValuePairs);

		for (String id : ids)
		{
			parameters.put(paramName, id);
			results.add(new Link(rel, formatter.format(urlPattern, parameters), null, type));
		}

		return results;
	}

	/**
	 * Convenience method to format a URL pattern into a string, substituting the names for
	 * values in the givent nameValuePairs list.
	 * 
	 * @param urlPattern a string containing parameters (e.g. /orders/{orderId})
	 * @param nameValuePairs a list of parameter names and associated values (e.g. "orderId", "1234")
	 * @return a formatted URL (e.g. /orders/1234)
	 */
	public static String formatUrl(String urlPattern, String... nameValuePairs)
	{
		return formatter.format(urlPattern, nameValuePairs);
	}


	/**
	 * Convenience method to format a URL pattern into a string, substituting the names for
	 * values in the givent nameValuePairs list.
	 * 
	 * @param urlPattern a string containing parameters (e.g. /orders/{orderId})
	 * @param nameValuePairs a list of parameter names and associated values (e.g. "orderId", "1234")
	 * @return a formatted URL (e.g. /orders/1234)
	 */
	public static String formatUrl(String urlPattern, Map<String, String> nameValuePairs)
	{
		return formatter.format(urlPattern, nameValuePairs);
	}
}
