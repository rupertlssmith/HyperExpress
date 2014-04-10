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

import com.strategicgains.hyperexpress.domain.Resource;

/**
 * @author toddf
 * @since Apr 7, 2014
 */
public class DefaultResourceFactory
implements ResourceFactory
{
	private Map<String, ResourceStrategy> strategies = new HashMap<String, ResourceStrategy>();

	@Override
	public Resource createResource(Object object, String contentType)
	{
		ResourceStrategy strategy = strategies.get(contentType);
		
		if (strategy == null)
		{
			throw new ResourceException("Cannot create resource for content type: " + contentType);
		}

		return strategy.createResource(object);
	}

	public DefaultResourceFactory addStrategy(ResourceStrategy strategy, String contentType)
	{
		if (strategies.containsKey(contentType))
		{
			throw new ResourceException("Duplicate content type: " + contentType);
		}

		strategies.put(contentType, strategy);
		return this;
	}
}
