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
package com.strategicgains.hyperexpress.domain.siren;

import java.util.ArrayList;
import java.util.List;

import com.strategicgains.hyperexpress.domain.Link;

/**
 * A Siren Link Object represents a hyperlink from the containing resource to a
 * URI.
 * 
 * @author toddf
 * @since Sep 12, 2014
 */
public class SirenLink
{
	// The attributes from 'LinkDefinition' that a Siren link cares about.
	public static final String TYPE = "type";
	public static final String TITLE = "title";
	public static final String HREF = "href";
	public static final String REL = "rel";

	/**
	 * The "rel" property is REQUIRED.
	 * 
	 * Defines the relationship of the link to its entity, per Web Linking (RFC5899). MUST be an array of strings.
	 */
	private List<String> rel = new ArrayList<String>(1);

	/**
	 * The "href" property is REQUIRED.
	 * 
	 * The URI of the linked resource.
	 */
	private String href;

/**
	 * The "title" property is OPTIONAL.
	 * 
	 * Its value is a string and is intended for labeling the link with a
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

	public SirenLink()
	{
		super();
	}

	public SirenLink(Link that)
	{
		this();
		this.addRel(that.getRel());
		this.setHref(that.getHref());
		this.setTitle(that.get(TITLE));
		this.setType(that.get(TYPE));
	}

	public List<String> getRel()
	{
		return rel;
	}

	public void addRel(String rel)
	{
		this.rel.add(rel);
	}

	public String getHref()
	{
		return href;
	}

	public SirenLink setHref(String href)
	{
		this.href = href;
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public SirenLink setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public String getType()
	{
		return type;
	}

	public SirenLink setType(String type)
	{
		this.type = type;
		return this;
	}
}
