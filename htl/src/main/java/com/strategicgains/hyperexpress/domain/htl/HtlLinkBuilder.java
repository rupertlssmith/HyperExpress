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
package com.strategicgains.hyperexpress.domain.htl;

import com.strategicgains.hyperexpress.LinkBuilder;

/**
 * @author toddf
 * @since Jan 10, 2014
 */
public class HtlLinkBuilder
extends LinkBuilder
{
	private static final String TEMPLATED = "templated";
	private static final String DEPRECATION = "deprecation";
	private static final String NAME = "name";
	private static final String PROFILE = "profile";
	private static final String HREFLANG = "hreflang";
	private static final String CURIES = "curies";

	public HtlLinkBuilder(String urlPattern)
    {
	    super(urlPattern);
    }

	//TODO: figure out curies...
	public HtlLinkBuilder curie(String curies)
	{
		return (HtlLinkBuilder) attribute(CURIES, curies);
	}

	public HtlLinkBuilder templated(boolean value)
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
	public HtlLinkBuilder type(String type)
	{
		return (HtlLinkBuilder) super.type(type);
	}

	public HtlLinkBuilder deprecation(String deprecation)
	{
		return (HtlLinkBuilder) attribute(DEPRECATION, deprecation);
	}

	public HtlLinkBuilder name(String name)
	{
		return (HtlLinkBuilder) attribute(NAME, name);
	}

	public HtlLinkBuilder profile(String profile)
	{
		return (HtlLinkBuilder) attribute(PROFILE, profile);
	}

	@Override
	public HtlLinkBuilder title(String title)
	{
		return (HtlLinkBuilder) super.title(title);
	}

	public HtlLinkBuilder hreflang(String hreflang)
	{
		return (HtlLinkBuilder) attribute(HREFLANG, hreflang);
	}

	@Override
    public HtlLinkBuilder baseUrl(String url)
    {
	    return (HtlLinkBuilder) super.baseUrl(url);
    }

	@Override
    public HtlLinkBuilder rel(String rel)
    {
	    return (HtlLinkBuilder) super.rel(rel);
    }

	@Override
    public HtlLinkBuilder attribute(String name, String value)
    {
	    return (HtlLinkBuilder) super.attribute(name, value);
    }

	@Override
    public HtlLinkBuilder urlParam(String name, String value)
    {
	    return (HtlLinkBuilder) super.urlParam(name, value);
    }
}
