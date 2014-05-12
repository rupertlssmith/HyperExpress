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

/**
 * Defines the interface for a callback to bind instances within a collection resource.
 * A TokenBinder implementation allows HyperExpress to iterate a collection and bind
 * any values from that instance to the TokenResolver before creating links for that 
 * resource.
 * 
 * @author toddf
 * @since May 5, 2014
 */
public interface TokenBinder
{
	/**
	 * Bind instance values from this object into the TokenResolver before creating
	 * links for this instance.
	 * 
	 * @param object an object for which to extract values for URL tokens.
	 * @param resolver a TokenResolver into which token values may be bound.
	 */
	void bind(Object object, TokenResolver resolver);
}
