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
package com.strategicgains.hyperexpress.expand;

import com.strategicgains.hyperexpress.domain.Resource;

/**
 * Defines the interface that Expander uses for its callbacks to implement link expansion or {@link Resource}
 * enrichment/augmentation.
 *
 * <p/>These callbacks are registered with the Expander using
 * {@code Expander.registerCallback(Class<?>, ExpansionCallback)} and are called via
 * {@code Expander.expand(Expansion, Class<?>, Resource)} or
 * {@code Expander.expand(Expansion, Class<?>, List<Resource>)} methods after the Resource is created and links (if
 * appropriate) are added.
 *
 * <p/>The callbacks are called whether or not the Expansion instance contains any relation names or not. This provides
 * the opportunity to augment the Resource instance in any way, such as adding/removing properties, looking up other
 * resources in a database and embedding them, etc.
 *
 * @author toddf
 * @since  Aug 8, 2014
 */
public interface ExpansionCallback {
    Resource expand(Expansion expansion, Resource resource);
}
