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
package com.strategicgains.hyperexpress;

import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.util.MapStringFormat;

/**
 * @author toddf
 * @since Jan 10, 2014
 */
public class UrlBuilder
{
	private static final MapStringFormat FORMATTER = new MapStringFormat();

	private String urlPattern;
	private Map<String, String> parameters = new HashMap<String, String>();

	public UrlBuilder(String urlPattern)
	{
		super();
		this.urlPattern = urlPattern;
	}

	public UrlBuilder param(String name, String value)
	{
		if (value == null)
		{
			parameters.remove(name);
		}
		else
		{
			parameters.put(name, value);
		}

		return this;
	}

	public String build()
	{
		return FORMATTER.format(urlPattern, parameters);
	}
}
