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
package com.strategicgains.hyperexpress.domain.hal;

import com.strategicgains.hyperexpress.builder.LinkBuilder;

/**
 * @author toddf
 * @since Jan 10, 2014
 */
public class HalLinkBuilder
extends LinkBuilder
{
	private static final String TEMPLATED = "templated";
	private static final String DEPRECATION = "deprecation";
	private static final String NAME = "name";
	private static final String PROFILE = "profile";
	private static final String HREFLANG = "hreflang";
	private static final String CURIES = "curies";

	public HalLinkBuilder(String urlPattern)
    {
	    super(urlPattern);
    }

	//TODO: figure out curies...
	public HalLinkBuilder curies(String curies)
	{
		return (HalLinkBuilder) attribute(CURIES, curies);
	}

	public HalLinkBuilder templated(boolean value)
	{
		if (value == false)
		{
			attribute(TEMPLATED, null);
		}
		else
		{
			attribute(TEMPLATED, Boolean.TRUE.toString());
		}

		return this;
	}

	@Override
	public HalLinkBuilder type(String type)
	{
		return (HalLinkBuilder) type(type);
	}

	public HalLinkBuilder deprecation(String deprecation)
	{
		return (HalLinkBuilder) attribute(DEPRECATION, deprecation);
	}

	public HalLinkBuilder name(String name)
	{
		return (HalLinkBuilder) attribute(NAME, name);
	}

	public HalLinkBuilder profile(String profile)
	{
		return (HalLinkBuilder) attribute(PROFILE, profile);
	}

	@Override
	public HalLinkBuilder title(String title)
	{
		return (HalLinkBuilder) title(title);
	}

	public HalLinkBuilder hreflang(String hreflang)
	{
		return (HalLinkBuilder) attribute(HREFLANG, hreflang);
	}

	@Override
    public HalLinkBuilder baseUrl(String url)
    {
	    return (HalLinkBuilder) super.baseUrl(url);
    }

	@Override
    public HalLinkBuilder rel(String rel)
    {
	    return (HalLinkBuilder) super.rel(rel);
    }

	@Override
    public HalLinkBuilder attribute(String name, String value)
    {
	    return (HalLinkBuilder) super.attribute(name, value);
    }

	@Override
    public HalLinkBuilder urlParam(String name, String value)
    {
	    return (HalLinkBuilder) super.urlParam(name, value);
    }
}
