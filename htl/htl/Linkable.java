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
package com.strategicgains.hyperexpress.domain.htl;

import java.util.Collection;
import java.util.Map;

/**
 * Interface defining a hypermedia-linkable object.
 * 
 * @author toddf
 * @since May 21, 2013
 */
public interface Linkable
{
	public Map<String, Link> getLinks();
	public void setLinks(Map<String, Link> links);
	public void addLink(Link link);
	public void addAllLinks(Collection<Link> links);
}
