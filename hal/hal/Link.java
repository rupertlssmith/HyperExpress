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

import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;


/**
 * A HAL Link instance - For expressing 'outbound' hyperlinks to other, related resources.
 * 
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
	 * The relationship from the referencing object to the referenced object
	 * (e.g. rel=self).
	 * 
	 * @see http://www.iana.org/assignments/link-relations/link-relations.xml
	 * 
	 *      atom:link elements MAY have a "rel" attribute that indicates the
	 *      link relation type. If the "rel" attribute is not present, the link
	 *      element MUST be interpreted as if the link relation type is
	 *      "alternate".
	 *      <p/>
	 *      The value of "rel" MUST be a string that is non-empty and matches
	 *      either the "isegment-nz-nc" or the "IRI" production in [RFC3987].
	 *      <p/>
	 *      Note that use of a relative reference other than a simple name is
	 *      not allowed. If a name is given, implementations MUST consider the
	 *      link relation type equivalent to the same name registered within the
	 *      IANA
	 */
	@Required
	private String rel;

	/**
	 * For distinguishing between Resource and Link elements that share the same
	 * rel value. The name attribute SHOULD NOT be used exclusively for
	 * identifying elements within a HAL representation, it is intended only as
	 * a 'secondary key' to a given rel value.
	 */
	private String name;

	/**
	 * The "hreflang" attribute's content describes the language of the resource
	 * pointed to by the href attribute. When used together with the
	 * rel="alternate", it implies a translated version of the entry. Link
	 * elements MAY have an hreflang attribute, whose value MUST be a language
	 * tag [RFC3066].
	 */
	@RegexValidation(pattern = "[A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*", nullable = true, message = "MUST be a language tag [RFC3066]")
	private String hreflang;

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
	 * This attribute SHOULD be present with a boolean value of true when the href of the link contains a URI Template (RFC6570).
	 */
	private Boolean templated;

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getHreflang()
	{
		return hreflang;
	}

	public void setHreflang(String hreflang)
	{
		this.hreflang = hreflang;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Boolean getTemplated()
	{
		return templated;
	}

	public void setTemplated(Boolean templated)
	{
		this.templated = templated;
	}
}
