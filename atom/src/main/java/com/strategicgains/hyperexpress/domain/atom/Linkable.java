/*
    Copyright 2012, Strategic Gains, Inc.

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

import java.util.Collection;
import java.util.List;

/**
 * Interface defining a hypermedia-linkable object.
 * 
 * @author toddf
 * @since Oct 19, 2012
 * @deprecated Use AtomResource
 */
public interface Linkable
{
	public List<AtomLink> getLinks();
	public void setLinks(List<AtomLink> links);
	public void addLink(AtomLink link);
	public void addAllLinks(Collection<AtomLink> links);
}
