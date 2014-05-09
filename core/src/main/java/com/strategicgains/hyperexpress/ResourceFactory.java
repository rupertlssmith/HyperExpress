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

import com.strategicgains.hyperexpress.domain.Resource;


/**
 * @author toddf
 * @since Apr 7, 2014
 */
public interface ResourceFactory
{
	/**
	 * Construct a Resource from the given object for the provided content type.
	 * 
	 * @param object a populated model from which to construct a resource.
	 * @param contentType the content-type to use in determining which resource type to construct.
	 * @return a Resource instance.
	 */
	Resource createResource(Object object, String contentType);
}
