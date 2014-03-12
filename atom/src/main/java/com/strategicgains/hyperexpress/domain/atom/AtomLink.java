/*
 * Copyright 2012 Strategic Gains, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.strategicgains.hyperexpress.domain.atom;

import com.strategicgains.hyperexpress.domain.LinkDefinition;
import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;


/**
 * Bean for general-purpose Atom-style hypermedia link. This Link class is
 * derived from http://www.ietf.org/rfc/rfc4287 (see).
 * 
 * @author toddf
 * @since 19 Sep 2012
 */
public class AtomLink
{
	/**
	 * The "href" attribute contains the link's IRI. atom:link elements MUST
	 * have an href attribute, whose value MUST be a IRI reference [RFC3987].
	 */
	@Required
	private String href;

	/**
	 * The relationship from the referencing object to the referenced object (e.g. rel=self).
	 * @see http://www.iana.org/assignments/link-relations/link-relations.xml
	 * 
	 * atom:link elements MAY have a "rel" attribute that indicates the link
	 * relation type.  If the "rel" attribute is not present, the link
	 * element MUST be interpreted as if the link relation type is "alternate".
	 * <p/>
	 * The value of "rel" MUST be a string that is non-empty and matches
	 * either the "isegment-nz-nc" or the "IRI" production in [RFC3987].
	 * <p/>
	 * Note that use of a relative reference other than a simple name is not
	 * allowed.  If a name is given, implementations MUST consider the link
	 * relation type equivalent to the same name registered within the IANA
	 */
	private String rel;

	/**
	 * On the link element, the "type" attribute's value is an advisory
	 * media type: it is a hint about the type of the representation that is
	 * expected to be returned when the value of the href attribute is
	 * dereferenced.  Note that the type attribute does not override the
	 * actual media type returned with the representation.  Link elements
	 * MAY have a type attribute, whose value MUST conform to the syntax of
	 * a MIME media type [MIMEREG].
	 */
	private String type;
	
	/**
	 * An optional title for the link.
	 * 
	 * The "title" attribute conveys human-readable information about the
	 * link.  The content of the "title" attribute is Language-Sensitive.
	 * Entities such as "&amp;" and "&lt;" represent their corresponding
	 * characters ("&" and "<", respectively), not markup.  Link elements
	 * MAY have a title attribute.
	 */
	private String title;
	
	/**
	 * The "length" attribute indicates an advisory length of the linked
	 * content in octets; it is a hint about the content length of the
	 * representation returned when the IRI in the href attribute is mapped
	 * to a URI and dereferenced.  Note that the length attribute does not
	 * override the actual content length of the representation as reported
	 * by the underlying protocol.  Link elements MAY have a length attribute.
	 */
	private String length;
	
	/**
	 * The "hreflang" attribute's content describes the language of the
	 * resource pointed to by the href attribute.  When used together with
	 * the rel="alternate", it implies a translated version of the entry.
	 * Link elements MAY have an hreflang attribute, whose value MUST be a
	 * language tag [RFC3066].
	 */
	@RegexValidation(pattern="[A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*", nullable=true, message="MUST be a language tag [RFC3066]")
	private String hreflang;

	public AtomLink()
	{
		super();
	}

	public AtomLink(String rel, String href)
	{
		this(rel, href, null);
	}

	public AtomLink(String rel, String href, String title)
	{
		this(rel, href, title, null);
	}

	public AtomLink(String rel, String href, String title, String type)
	{
		super();
		this.href = href;
		this.rel = rel;
		this.title = title;
		this.type = type;
	}

	public AtomLink(LinkDefinition definition)
	{
		this();
		this.setHref(definition.getHref());
		this.setRel(definition.getRel());
		this.setHreflang(definition.get("hreflang"));
		this.setTitle(definition.get("title"));
		this.setType(definition.get("type"));
		this.setLength(definition.get("length"));
	}

	/**
	 * Copy constructor.
	 * 
	 * @param that
	 */
	public AtomLink(AtomLink that)
	{
		this(that.rel, that.href, that.title, that.type);
		this.hreflang = that.hreflang;
		this.length = that.length;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public String getRel()
	{
		return rel;
	}

	public void setRel(String rel)
	{
		this.rel = rel;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getLength()
	{
		return length;
	}

	public void setLength(String length)
	{
		this.length = length;
	}

	public String getHreflang()
	{
		return hreflang;
	}

	public void setHreflang(String hreflang)
	{
		this.hreflang = hreflang;
	}
}
