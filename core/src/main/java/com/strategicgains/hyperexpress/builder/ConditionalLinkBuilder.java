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

import com.strategicgains.hyperexpress.domain.Link;

/**
 * Extends LinkBuilder, adding a 'conditional' flag, where the value can either be "true" or
 * a token (e.g. "{role}").  If the flag is set to true and the resulting Link that is built
 * contains a token (a URL token is not bound), then build() returns null.
 * <p/>
 * Otherwise, if the flag is set to a token and that token doesn't get bound from the object
 * during build(), then the resulting link is not returned (it is null).
 * 
 * @author toddf
 * @since Jul 9, 2014
 */
public class ConditionalLinkBuilder
extends LinkBuilder
{
	private String conditional;

	public ConditionalLinkBuilder()
    {
	    super();
    }

	public ConditionalLinkBuilder(String urlPattern)
    {
	    super(urlPattern);
    }

	/**
	 * Copy-constructor from LinkBuilder.
	 * 
	 * @param builder a LinkBuilder instance. Never null;
	 */
	public ConditionalLinkBuilder(LinkBuilder builder)
	{
		super(builder);
	}

	/**
	 * Copy-constructor from OptionalLinkBuilder.
	 * 
	 * @param builder an OptionalLinkBuilder instance. Never null.
	 */
	public ConditionalLinkBuilder(ConditionalLinkBuilder builder)
	{
		super(builder);
		this.conditional = builder.conditional;
	}

	void optional()
	{
		conditional = Boolean.TRUE.toString();
	}

	void ifBound(String token)
	{
		if (token == null)
		{
			conditional = null;
			return;
		}

		if (token.startsWith("{") && token.endsWith("}"))
		{
			conditional = token;
		}
		else
		{
			conditional = "{" + token + "}";
		}
	}

	void ifNotBound(String token)
	{
		if (token == null)
		{
			conditional = null;
			return;
		}

		if (token.startsWith("{") && token.endsWith("}"))
		{
			conditional = "!" + token;
		}
		else
		{
			conditional = "!{" + token + "}";
		}
	}

	public String getConditional()
	{
		return conditional;
	}

	@Override
	public Link build(Object object, TokenResolver tokenResolver)
    {
		Link link = super.build(object, tokenResolver);

		if (conditional != null)
		{
			if (conditional.trim().equalsIgnoreCase(Boolean.TRUE.toString()) && link.hasToken())
			{
				return null;
			}
			else
			{
				String value = tokenResolver.resolve(conditional, object);

				// not bound or bound to 'false'
				if (value.startsWith("{") || value.trim().equalsIgnoreCase(Boolean.FALSE.toString()))
				{
					return null;
				}
				// desire not bound
				else if (value.startsWith("!"))
				{
					if (!(value.startsWith("!{") && value.endsWith("}"))
						&& !value.substring(1).trim().equalsIgnoreCase(Boolean.FALSE.toString()))
					{
						return null;
					}
				}
			}
		}

		return link;
    }

	@Override
	public Link build()
	{
		Link link = super.build();

		if (conditional != null)
		{
			if (conditional.trim().equalsIgnoreCase(Boolean.TRUE.toString()) && link.hasToken())
			{
				return null;
			}
		}

		return link;
	}

	@Override
	public Link build(TokenResolver tokenResolver)
	{
		Link link = super.build(tokenResolver);

		if (conditional != null)
		{
			if (conditional.trim().equalsIgnoreCase(Boolean.TRUE.toString()) && link.hasToken())
			{
				return null;
			}
		}

		return link;
	}
}
