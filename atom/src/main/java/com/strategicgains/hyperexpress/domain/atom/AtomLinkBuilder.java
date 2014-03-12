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

import com.strategicgains.hyperexpress.LinkBuilder;

/**
 * @author toddf
 * @since Mar 11, 2014
 */
public class AtomLinkBuilder
extends LinkBuilder
{
	private static final String HREFLANG = "hreflang";
	private static final String LENGTH = "length";

	public AtomLinkBuilder(String urlPattern)
    {
	    super(urlPattern);
    }

	public AtomLinkBuilder length(String length)
	{
		return attribute(LENGTH, length);
	}

	@Override
	public AtomLinkBuilder type(String type)
	{
		return (AtomLinkBuilder) super.type(type);
	}

	@Override
	public AtomLinkBuilder title(String title)
	{
		return (AtomLinkBuilder) super.title(title);
	}

	public AtomLinkBuilder hreflang(String hreflang)
	{
		return attribute(HREFLANG, hreflang);
	}

	@Override
    public AtomLinkBuilder baseUrl(String url)
    {
	    return (AtomLinkBuilder) super.baseUrl(url);
    }

	@Override
    public AtomLinkBuilder rel(String rel)
    {
	    return (AtomLinkBuilder) super.rel(rel);
    }

	@Override
    public AtomLinkBuilder attribute(String name, String value)
    {
	    return (AtomLinkBuilder) super.attribute(name, value);
    }

	@Override
    public AtomLinkBuilder urlParam(String name, String value)
    {
	    return (AtomLinkBuilder) super.urlParam(name, value);
    }
}
