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

import com.strategicgains.hyperexpress.domain.LinkDefinition;
import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;

/**
 * A Link Object represents a hyperlink from the containing resource to a
 * URI.
 * 
 * @author toddf
 * @since May 21, 2013
 */
public class HtlLink
{
	/**
	 * The "href" property is REQUIRED.
	 * 
	 * Its value is either a URI [RFC3986] or a URI Template [RFC6570].
	 * 
	 * If the value is a URI Template then the Link Object SHOULD have a
	 * "templated" attribute whose value is true.
	 */
	@Required
	private String href;

	/**
	 * The "name" property is OPTIONAL.
	 * 
	 * Its value MAY be used as a secondary key for selecting Link Objects which
	 * share the same relation type.
	 * 
	 * For distinguishing between Resource and Link elements that share the same
	 * rel value. The name attribute SHOULD NOT be used exclusively for
	 * identifying elements within a HAL representation, it is intended only as
	 * a 'secondary key' to a given rel value.
	 */
	private String name;

	/**
	 * The "hreflang" property is OPTIONAL.
	 * 
	 * Its value is a string and is intended for indicating the language of the
	 * target resource (as defined by [RFC5988]).
	 * 
	 * The "hreflang" attribute's content describes the language of the resource
	 * pointed to by the href attribute. When used together with the
	 * rel="alternate", it implies a translated version of the entry. Link
	 * elements MAY have an hreflang attribute, whose value MUST be a language
	 * tag [RFC3066].
	 */
	@RegexValidation(pattern = "[A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*", nullable = true, message = "MUST be a language tag [RFC3066]")
	private String hreflang;

/**
	 * The "title" property is OPTIONAL.
	 * 
	 * Its value is a string and is intended for labelling the link with a
	 * human-readable identifier (as defined by [RFC5988]).
	 * 
	 * The "title" attribute conveys human-readable information about the
	 * link.  The content of the "title" attribute is Language-Sensitive.
	 * Entities such as "&amp;" and "&lt;" represent their corresponding
	 * characters ("&" and "<", respectively), not markup.  Link elements
	 * MAY have a title attribute.
	 */
	private String title;

	/**
	 * The "templated" property is OPTIONAL.
	 * 
	 * Its value is boolean and SHOULD be true when the Link Object's "href"
	 * property is a URI Template.
	 * 
	 * Its value SHOULD be considered false if it is undefined or any other
	 * value than true.
	 */
	private Boolean templated;

	/**
	 * The "type" property is OPTIONAL.
	 * 
	 * Its value is a string used as a hint to indicate the media type expected
	 * when dereferencing the target resource.
	 * 
	 * On the link element, the "type" attribute's value is an advisory media
	 * type: it is a hint about the type of the representation that is expected
	 * to be returned when the value of the href attribute is dereferenced. Note
	 * that the type attribute does not override the actual media type returned
	 * with the representation. Link elements MAY have a type attribute, whose
	 * value MUST conform to the syntax of a MIME media type [MIMEREG].
	 */
	private String type;

	/**
	 * The "deprecation" property is OPTIONAL.
	 * 
	 * Its presence indicates that the link is to be deprecated (i.e. removed)
	 * at a future date. Its value is a URL that SHOULD provide further
	 * information about the deprecation.
	 * 
	 * A client SHOULD provide some notification (for example, by logging a
	 * warning message) whenever it traverses over a link that has this
	 * property. The notification SHOULD include the deprecation property's
	 * value so that a client maintainer can easily find information about the
	 * deprecation.
	 */
	private String deprecation;

	/**
	 * The "profile" property is OPTIONAL.
	 * 
	 * Its value is a string which is a URI that hints about the profile (as
	 * defined by [I-D.wilde-profile-link]) of the target resource.
	 */
	private String profile;

	public HtlLink()
	{
		super();
	}

	public HtlLink(LinkDefinition definition)
	{
		this();
		this.setHref(definition.getHref());
		this.setDeprecation(definition.get("deprecation"));
		this.setHreflang(definition.get("hreflang"));
		this.setName(definition.get("name"));
		this.setProfile(definition.get("profile"));
		this.setTemplated(Boolean.valueOf(definition.get("templated")));
		this.setTitle(definition.get("title"));
		this.setType(definition.get("type"));
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDeprecation()
	{
		return deprecation;
	}

	public void setDeprecation(String deprecation)
	{
		this.deprecation = deprecation;
	}

	public String getProfile()
	{
		return profile;
	}

	public void setProfile(String profile)
	{
		this.profile = profile;
	}
}
