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
 * Build URL strings from a URL pattern, binding URL tokens to actual values.
 *
 * <p/>There is also building of dynamic query strings. By calling addQuery() with a tokenized query-string segment
 * (e.g. "limit={limit}"), the builder will include query-string arguments that get fully populated. If there is no
 * parameter bound to a particular query-string segment, it is not included in the constructed URL string.
 *
 * @author toddf
 * @since  Jan 10, 2014
 */
public interface UrlBuilder extends Cloneable {
    UrlBuilder clone();

    /**
     * Retrieve the URL pattern set on this URL builder.
     *
     * @return the URL pattern or null.
     */
    String urlPattern();

    /**
     * Set the URL pattern for the URL builder.
     *
     * @param  urlPattern a URL path with optional tokens of the form '{tokenName}'
     *
     * @return this UrlBuilder instance to facilitate method chaining.
     */
    UrlBuilder urlPattern(String urlPattern);

    /**
     * Set the prefix portion of the URL which is to be pre-pended to the URL pattern.
     *
     * <p/>Optional, as the URL pattern may contain everything. However, this is provided as a convenience so consumers
     * don't have to perform their own concatenation to pass in the entire URL pattern string.
     *
     * <p/>For example: 'http://www.example.com:8080'
     *
     * @param  baseUrl the string that will prefix the URL pattern
     *
     * @return this UrlBuilder instance to facilitate method chaining.
     */
    UrlBuilder baseUrl(String baseUrl);

    /**
     * Retrieve the base URL set on this URL builder.
     *
     * @return the base URL or null.
     */
    String baseUrl();

    /**
     * Add an optional query-string segment to this UrlBuilder. If all of the tokens in the query-string are bound, the
     * segment is included in the generated URL string during build(). However, if there are unbound tokens in the
     * resulting query-string segment, it is not included in the generated URL string.
     *
     * <p/>Do not include any question mark ("?") or ampersand ("&") in the query-string segment.
     *
     * @param  query a query-string segment to optionally include.
     *
     * @return this UrlBuilder instance to facilitate method chaining.
     */
    UrlBuilder withQuery(String query);

    /** Remove the query-string segments from this UrlBuilder. */
    void clearQueries();

    /**
     * Builds a URL but does not perform any token replacement. Equivalent to (and short-hand for) build(null).
     *
     * @return
     */
    String build();

    /**
     * Build a URL, resolving any tokens using the given TokenResolver.
     *
     * @param  tokenResolver a TokenResolver to perform token substitution.
     *
     * @return a URL string.
     */
    String build(TokenResolver tokenResolver);

    /**
     * Build a URL, resolving any tokens using the given TokenResolver. Additionally, will call any TokenBinders for the
     * given Object instance to bind values from it into the URL.
     *
     * @param  object        an object of which to bind properties into the URL parameters.
     * @param  tokenResolver a TokenResolver to perform token substitution.
     *
     * @return a URL string.
     */
    String build(Object object, TokenResolver tokenResolver);

    /**
     * Build a new URL from the given URL pattern. Optional query-string parameters may be appended. Tokens are resolved
     * using the passed-in TokenResolver.
     *
     * @param  urlPattern    a URL pattern to bind, instead of using the one associated with this builder. May not be
     *                       null.
     * @param  tokenResolver a TokenResolver instance set with bound token values.
     *
     * @return a URL string.
     */
    String build(String urlPattern, TokenResolver tokenResolver);

    /**
     * Build a URL, resolving any tokens using the given TokenResolver. Additionally, will call any TokenBinders for the
     * given Object instance to bind values from it into the URL.
     *
     * @param  urlPattern    a URL pattern, with optional tokens.
     * @param  object        an object of which to bind properties into the URL parameters.
     * @param  tokenResolver a TokenResolver to perform token substitution.
     *
     * @return a URL string.
     */
    String build(String urlPattern, Object object, TokenResolver tokenResolver);
}
