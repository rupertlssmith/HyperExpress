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
package com.strategicgains.hyperexpress;

/**
 * Includes the standard relation types that us REST folks care about.
 * See the standard list at: http://www.iana.org/assignments/link-relations/link-relations.xml
 * 
 * @author toddf
 * @since Oct 22, 2012
 * @see http://www.iana.org/assignments/link-relations/link-relations.xml
 */
public abstract class RelTypes
{
	// SECTION: CONSTANTS

	/**
	 * Refers to a substitute for this context.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-alternate
	 */
	public static final String ALTERNATE = "alternate";

	/**
	 * Refers to an appendix.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String APPENDIX = "appendix";

	/**
	 * Refers to a collection of records, documents, or other materials of historical interest.
	 * @see http://www.w3.org/TR/2011/WD-html5-20110113/links.html#rel-archives
	 */
	public static final String ARCHIVES = "archives";

	/**
	 * Refers to the context's author.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-author
	 */
	public static final String AUTHOR = "author";

	/**
	 * Gives a permanent link to use for bookmarking purposes.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-bookmark
	 */
	public static final String BOOKMARK = "bookmark";

	/**
	 * Designates the preferred version of a resource (the IRI and its contents).
	 * @see http://www.iana.org/go/rfc6596
	 */
	public static final String CANONICAL = "canonical";

	/**
	 * Refers to a chapter in a collection of resources.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String CHAPTER = "chapter";

	/**
	 * The target IRI points to a resource which represents the collection resource for the context IRI.
	 * @see http://www.iana.org/go/rfc6573
	 */
	public static final String COLLECTION = "collection";

	/**
	 * Refers to a table of contents.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String CONTENTS = "contents";

	/**
	 * Refers to a copyright statement that applies to the link's context.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String COPYRIGHT = "copyright";

	/**
	 * Refers to a resource containing the most recent item(s) in a collection of resources.
	 * @see http://www.iana.org/go/rfc5005
	 */
	public static final String CURRENT = "current";

	/**
	 * Refers to a resource providing information about the link's context.
	 * @see http://www.w3.org/TR/powder-dr/#assoc-linking
	 */
	public static final String DESCRIBED_BY = "describedby";

	/**
	 * Refers to a list of patent disclosures made with respect to material for which 'disclosure' relation is specified.
	 * @see http://www.iana.org/go/rfc6579
	 */
	public static final String DISCLOSURE = "disclosure";

	/**
	 * Refers to a resource that can be used to edit the link's context.
	 * @see http://www.iana.org/go/rfc5023
	 */
	public static final String EDIT = "edit";

	/**
	 * Refers to a resource that can be used to edit media associated with the link's context.
	 * @see http://www.iana.org/go/rfc5023
	 */
	public static final String EDIT_MEDIA = "edit-media";

	/**
	 * Identifies a related resource that is potentially large and might require special handling.
	 * @see http://www.iana.org/go/rfc4287
	 */
	public static final String ENCLOSURE = "enclosure";

	/**
	 * An IRI that refers to the furthest preceding resource in a series of resources.
	 * @see http://www.iana.org/go/rfc5988
	 */
	public static final String FIRST = "first";

	/**
	 * Refers to a glossary of terms.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String GLOSSARY = "glossary";

	/**
	 * Refers to context-sensitive help.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-help
	 */
	public static final String HELP = "help";

	/**
	 * Refers to a resource hosted by the server indicated by the link context.
	 * @see http://www.iana.org/go/rfc6690
	 */
	public static final String HOSTS = "hosts";

	/**
	 * Refers to a hub that enables registration for notification of updates to the context.
	 * @see http://pubsubhubbub.googlecode.com/
	 */
	public static final String HUB = "hub";

	/**
	 * Refers to an icon representing the link's context.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-icon
	 */
	public static final String ICON = "icon";

	/**
	 * Refers to an index.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String INDEX = "index";

	/**
	 * The target IRI points to a resource that is a member of the collection represented by the context IRI.
	 * @see http://www.iana.org/go/rfc6573
	 */
	public static final String ITEM = "item";

	/**
	 * An IRI that refers to the furthest following resource in a series of resources.
	 * @see http://www.iana.org/go/rfc5988
	 */
	public static final String LAST = "last";

	/**
	 * Points to a resource containing the latest (e.g., current) version of the context.
	 * @see http://www.iana.org/go/rfc5829
	 */
	public static final String LATEST_VERSION = "latest-version";

	/**
	 * Refers to a license associated with this context.
	 * @see http://www.iana.org/go/rfc4946
	 */
	public static final String LICENSE = "license";

	/**
	 * Refers to a resource that can be used to monitor changes in an HTTP resource.
	 * @see http://www.iana.org/go/rfc5989
	 */
	public static final String MONITOR = "monitor";

	/**
	 * Refers to a resource that can be used to monitor changes in a specified group of HTTP resources.
	 * @see http://www.iana.org/go/rfc5989
	 */
	public static final String MONITOR_GROUP = "monitor-group";

	/**
	 * Indicates that the link's context is a part of a series, and that the next in the series is the link target.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-next
	 */
	public static final String NEXT = "next";

	/**
	 * Refers to the immediately following archive resource.
	 * @see http://www.iana.org/go/rfc5005
	 */
	public static final String NEXT_ARCHIVE = "next-archive";

	/**
	 * Indicates a resource where payment is accepted.
	 * @see http://www.iana.org/go/rfc5988
	 */
	public static final String PAYMENT = "payment";

	/**
	 * Points to a resource containing the predecessor version in the version history.
	 * @see http://www.iana.org/go/rfc5829
	 */
	public static final String PREDECESSOR_VERSION = "predecessor-version";

	/**
	 * Indicates that the link's context is a part of a series, and that the previous in the series is the link target.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-prev
	 */
	public static final String PREV = "prev";

	/**
	 * Refers to the previous resource in an ordered series of resources. Synonym for "prev".
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String PREVIOUS = "previous";

	/**
	 * Refers to the immediately preceding archive resource.
	 * @see http://www.iana.org/go/rfc5005
	 */
	public static final String PREV_ARCHIVE = "prev-archive";

	/**
	 * Identifies a related resource.
	 * @see http://www.iana.org/go/rfc4287
	 */
	public static final String RELATED = "related";

	/**
	 * Identifies a resource that is a reply to the context of the link.
	 * @see http://www.iana.org/go/rfc4685
	 */
	public static final String REPLIES = "replies";

	/**
	 * Refers to a resource that can be used to search through the link's context and related resources.
	 * @see http://www.opensearch.org/Specifications/OpenSearch/1.1
	 */
	public static final String SEARCH = "search";

	/**
	 * Refers to a section in a collection of resources.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String SECTION = "section";

	/**
	 * Conveys an identifier for the link's context.
	 * @see http://www.iana.org/go/rfc4287
	 */
	public static final String SELF = "self";

	/**
	 * Indicates a URI that can be used to retrieve a service document.
	 * @see http://www.iana.org/go/rfc5023
	 */
	public static final String SERVICE = "service";

	/**
	 * Refers to the first resource in a collection of resources.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String START = "start";

	/**
	 * Refers to a stylesheet.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-stylesheet
	 */
	public static final String STYLE_SHEET = "stylesheet";

	/**
	 * Refers to a resource serving as a subsection in a collection of resources.
	 * @see http://www.w3.org/TR/1999/REC-html401-19991224
	 */
	public static final String SUB_SECTION = "subsection";

	/**
	 * Points to a resource containing the successor version in the version history.
	 * @see http://www.iana.org/go/rfc5829
	 */
	public static final String SUCCESSOR_VERSION = "successor-version";

	/**
	 * Gives a tag (identified by the given address) that applies to the current document.
	 * @see http://www.w3.org/TR/html5/links.html#link-type-tag
	 */
	public static final String TAG = "tag";

	/**
	 * Points to a resource containing the version history for the context.
	 * @see http://www.iana.org/go/rfc5829
	 */
	public static final String VERSION_HISTORY = "version-history";

	/**
	 * Refers to a parent document in a hierarchy of documents.
	 * @see http://www.iana.org/go/rfc5988
	 */
	public static final String UP = "up";

	/**
	 * Identifies a resource that is the source of the information in the link's context.
	 * @see http://www.iana.org/go/rfc4287
	 */
	public static final String VIA = "via";

	/**
	 * Points to a working copy for this resource.
	 * @see http://www.iana.org/go/rfc5829
	 */
	public static final String WORKING_COPY = "working-copy";


	// SECTION: CONSTRUCTORS - PRIVATE

	/**
	 * Points to the versioned resource from which this working copy was obtained.
	 * @see http://www.iana.org/go/rfc5829
	 */
	public static final String WORKING_COPY_OF = "working-copy-of";

	private RelTypes()
	{
		// prevents instantiation.
	}
}
