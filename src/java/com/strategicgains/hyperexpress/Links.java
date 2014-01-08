/*
    Copyright 2013, Strategic Gains, Inc.

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

import java.util.Collection;

import com.strategicgains.hyperexpress.builder.MultipleLinkBuilder;
import com.strategicgains.hyperexpress.builder.SingleLinkBuilder;

/**
 * @author toddf
 * @since Sep 21, 2013
 */
public class Links
{
	public static MultipleLinkBuilder ids(String urlPattern, String rel, String urlParameter, Collection<String> ids)
	{
		return (MultipleLinkBuilder) new MultipleLinkBuilder(urlParameter, ids)
			.href(urlPattern)
			.rel(rel);
	}

	public static MultipleLinkBuilder ids(String parameterName, String... ids)
	{
		return new MultipleLinkBuilder(parameterName, ids);
	}

	public static MultipleLinkBuilder ids(String parameterName, Collection<String> ids)
	{
		return new MultipleLinkBuilder(parameterName, ids);
	}

	public static SingleLinkBuilder id(String parameterName, String id)
	{
		return new SingleLinkBuilder(parameterName, id);
	}
}
