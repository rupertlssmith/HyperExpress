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
package com.strategicgains.hyperexpress.domain.hal;

import java.util.Collection;

/**
 * A HAL Resource instance - For expressing the embedded nature of a given part of the representation.
 * 
 * @author toddf
 * @since May 21, 2013
 */
public class Resource
{
	private Collection<Link> _links;
	private Collection<Resource> _embedded;
}
