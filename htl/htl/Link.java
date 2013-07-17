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

import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;

/**
 * @author toddf
 * @since May 21, 2013
 */
public class Link
{
	/**
	 * The "href" attribute contains the link's IRI. atom:link elements MUST
	 * have an href attribute, whose value MUST be a IRI reference [RFC3987].
	 */
	@Required
	private String href;

	/**
	 * This attribute SHOULD be present with a boolean value of true when the
	 * href of the link contains a URI Template (such as that specified by
	 * RFC6570).
	 */
	private Boolean templated;

	/**
	 * Optional property for distinguishing between Resource and Link elements
	 * that share the same 'rel' value. The name attribute SHOULD NOT be used
	 * exclusively for identifying elements within a HTL representation, it is
	 * intended only as a 'secondary key' to a given 'rel' value.
	 */
	private String name;

	/**
	 * On the link element, the "type" attribute's value is an advisory media
	 * type: it is a hint about the type of the representation that is expected
	 * to be returned when the value of the href attribute is dereferenced. Note
	 * that the type attribute does not override the actual media type returned
	 * with the representation. Link elements MAY have a type attribute, whose
	 * value MUST conform to the syntax of a MIME media type [MIMEREG].
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
	 * The "hreflang" attribute's content describes the language of the resource
	 * pointed to by the href attribute. When used together with the
	 * rel="alternate", it implies a translated version of the entry. Link
	 * elements MAY have an hreflang attribute, whose value MUST be a language
	 * tag [RFC3066].
	 */
	@RegexValidation(pattern = "[A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*", nullable = true, message = "MUST be a language tag [RFC3066]")
	private String hreflang;

	public Link()
	{
		super();
	}

	public Link(String href)
	{
		this(href, null);
	}

	public Link(String href, String title)
	{
		this(href, title, null);
	}

	public Link(String href, String title, String type)
	{
		super();
		this.href = href;
		this.title = title;
		this.type = type;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param that
	 */
	public Link(Link that)
	{
		this(that.href, that.title, that.type);
		this.hreflang = that.hreflang;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
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

	public String getHreflang()
	{
		return hreflang;
	}

	public void setHreflang(String hreflang)
	{
		this.hreflang = hreflang;
	}

	public Boolean getTemplated()
	{
		return templated;
	}

	public void setTemplated(Boolean templated)
	{
		this.templated = templated;
	}

	public String getName()
	{
		return name;
	}
	
	public boolean hasName()
	{
		return (name != null);
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
