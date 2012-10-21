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
package com.strategicgains.hyperexpress.domain;

/**
 * Bean for general-purpose hypermedia link.
 * 
 * @author toddf
 * @since 19 Sep 2012
 */
public class Link
{
	/**
	 * The URL to operate on the related object.
	 */
	private String href;

	/**
	 * The relationship from the referencing object to the referenced object (e.g. rel=self).
	 */
	private String rel;

	/**
	 * The type of the referred-to object.
	 */
	private String type;
	
	public Link(String rel, String href)
	{
		this(rel, href, null);
	}

	public Link(String rel, String href, String type)
	{
		super();
		this.href = href;
		this.rel = rel;
		this.type = type;
	}
	
	/**
	 * Copy constructor to facilitate immutability in Link domain.
	 * 
	 * @param that
	 */
	public Link(Link that)
	{
		this(that.rel, that.href, that.type);
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}