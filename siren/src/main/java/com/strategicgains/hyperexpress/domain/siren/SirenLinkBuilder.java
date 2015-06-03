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
package com.strategicgains.hyperexpress.domain.siren;

import com.strategicgains.hyperexpress.builder.DefaultConditionalLinkBuilder;

/**
 * A convenience class for building SirenLink instances.
 * 
 * @author toddf
 * @since Sep 12, 2014
 */
public class SirenLinkBuilder
extends DefaultConditionalLinkBuilder
{
	public SirenLinkBuilder(String urlPattern)
    {
	    super(urlPattern);
    }

	@Override
	public SirenLinkBuilder type(String type)
	{
		return (SirenLinkBuilder) super.type(type);
	}

	@Override
	public SirenLinkBuilder title(String title)
	{
		return (SirenLinkBuilder) super.title(title);
	}

	@Override
    public SirenLinkBuilder baseUrl(String url)
    {
	    return (SirenLinkBuilder) super.baseUrl(url);
    }

	@Override
    public SirenLinkBuilder rel(String rel)
    {
	    return (SirenLinkBuilder) super.rel(rel);
    }

	@Override
    public SirenLinkBuilder set(String name, String value)
    {
	    return (SirenLinkBuilder) super.set(name, value);
    }
}
