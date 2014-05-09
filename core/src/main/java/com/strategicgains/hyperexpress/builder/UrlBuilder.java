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
package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.util.MapStringFormat;

/**
 * Build URL strings from a URL pattern, binding URL tokens to actual values.
 * This class is not thread safe, as the token bindings are fully mutable.
 * 
 * @author toddf
 * @since Jan 10, 2014
 */
public class UrlBuilder
{
	private static final MapStringFormat FORMATTER = new MapStringFormat();

	private String baseUrl;
	private String urlPattern;
	private Map<String, String> tokenBindings = new HashMap<String, String>();

	/**
	 * Create a new UrlBuilder, passing in the URL pattern in which to
	 * substitute tokens. This URL pattern may represent the entire URL
	 * or just the path portion (relative path).
	 * <p/>
	 * The URL pattern is templated, in that it contains tokens to be
	 * later substitued for actual values. The tokens are delimited with
	 * beginning and trailing curly-braces (e.g. '{token}').
	 * <p/>
	 * If used in conjunction with baseUrl(), this URL pattern must be
	 * just the path portion of the URL and should be prefixed with a leading
	 * slash ('/').
	 * <p/>
	 * For example: '/users/{userId}' or 'http://www.example.com/api/users/{userId}'
	 * 
	 * @param urlPattern
	 */
	public UrlBuilder(String urlPattern)
	{
		super();
		this.urlPattern = urlPattern;
	}

	/**
	 * Set the prefix portion of the URL which is to be pre-pended to the URL pattern.
	 * <p/>
	 * Optional, as the URL pattern may contain everything. However, this is provided
	 * as a convenience so consumers don't have to perform their own concatenation
	 * to pass in the entire URL pattern string.
	 * <p/>
	 * For example: 'http://www.example.com:8080'
	 * 
	 * @param baseUrl the string that will prefix the URL pattern
	 * @return this UrlBuilder instance to facilitate method chaining.
	 */
	public UrlBuilder baseUrl(String baseUrl)
    {
    	this.baseUrl = baseUrl;
    	return this;
    }

	/**
	 * Set a value to be substituted for a token in the URL pattern. While
	 * tokens in the URL pattern are delimited with curly-braces, the token
	 * name does not contain the braces.  The value is any URL-safe string
	 * value.
	 * 
	 * @param tokenName the name of a token in the URL pattern.
	 * @param value the URL-safe string value to substitute for the token name in the URL pattern.
	 * @return this UrlBuilder instance to facilitate method chaining.
	 */
	public UrlBuilder bindToken(String tokenName, String value)
	{
		if (value == null)
		{
			tokenBindings.remove(tokenName);
		}
		else
		{
			tokenBindings.put(tokenName, value);
		}

		return this;
	}

	/**
	 * 'Unbind' a substitution value from a token name.
	 * 
	 * @param tokenName the name of a previously-bound token name.
	 */
	public void removeBinding(String tokenName)
	{
		tokenBindings.remove(tokenName);
	}

	/**
	 * Build a new URL from the URL pattern, utilizing the current token bindings.
	 * If any tokens are not bound, they will not be substituted, remaining as
	 * URL template parameters in the output string.
	 * 
	 * @return a URL string utilizing the current token bindings.
	 */
	public String build()
	{
		return build(buildFullUrlPattern());
	}

	/**
	 * Build a new URL from the give URL pattern, utilizing the current token bindings.
	 * 
	 * @param urlPattern a URL pattern to bind, instead of using the one associated with
	 * this builder. May not be null.
	 * @return a URL string.
	 */
	public String build(String urlPattern)
	{
		return FORMATTER.format(urlPattern, tokenBindings);
	}

	/**
	 * Sometimes, it is useful to bind *most* (all but one) of the tokens in a URL
	 * then, given a collection of IDs, build a collection of URLs for each of those
	 * IDs, binding them in individually.
	 * <p/>
	 * This method builds a collection of URL strings, one for each ID given in the
	 * 'ids' array.
	 * 
	 * @param tokenName the name of a token in the URL pattern.
	 * @param ids variable-length array of URL-safe string values to substitute for the token name in the URL pattern.
	 * @return a Collection of URL strings, matching the size of the 'ids' array.
	 */
	public Collection<String> build(String tokenName, String... ids)
	{
		return build(tokenName, Arrays.asList(ids));
	}

	/**
	 * This method builds a collection of URL strings, one for each ID given in the
	 * 'ids' collection.
	 * 
	 * @param tokenName the name of a token in the URL pattern.
	 * @param ids variable-length array of URL-safe string values to substitute for the token name in the URL pattern.
	 * @return a Collection of URL strings, matching the size of the 'ids' collection.
	 */
	public Collection<String> build(String tokenName, Collection<String> ids)
	{
		if (ids == null) return null;

		Collection<String> urls = new ArrayList<String>(ids.size());
		String href = buildFullUrlPattern();

		for (String id : ids)
		{
			bindToken(tokenName, id);
			urls.add(build(href));
		}

		removeBinding(tokenName);
		return urls;
	}

	private String buildFullUrlPattern()
	{
		String path = FORMATTER.format(urlPattern, tokenBindings);
		return (baseUrl == null ? path : baseUrl + path);
	}
}
