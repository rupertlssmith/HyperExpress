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
package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author toddf
 * @since Sep 21, 2013
 */
public class MultipleLinkBuilder
extends LinkBuilder
{
	private Collection<String> ids;

	public MultipleLinkBuilder(String parameterName, String... ids)
	{
		this(parameterName, Arrays.asList(ids));
	}

	public MultipleLinkBuilder(String parameterName, Collection<String> ids)
	{
		super(parameterName);
		this.ids = new ArrayList<String>(ids);
	}

	public Collection<LinkTemplate> build()
	{
		if (ids == null) return null;

		Collection<LinkTemplate> r = new ArrayList<LinkTemplate>(ids.size());

		for (String id : ids)
		{
			r.add(build(id));
		}

		return r;
	}
}
