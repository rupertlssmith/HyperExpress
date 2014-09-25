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

import com.strategicgains.hyperexpress.domain.Action;

/**
 * @author toddf
 * @since Sep 12, 2014
 */
public class SirenAction
implements Action
{
	private String name;
	private List<String> classes;
	private String method;
	private String href;
	private String title;
	private String type;
	private List<SirenField> fields;

	public String getName()
	{
		return name;
	}

	public SirenAction setName(String name)
	{
		this.name = name;
		return this;
	}

	public List<String> getClasses()
	{
		return classes;
	}

	public SirenAction setClasses(List<String> classes)
	{
		this.classes = new ArrayList<String>(classes);
		return this;
	}

	public SirenAction addClass(String className)
	{
		if (classes == null)
		{
			this.classes = new ArrayList<String>();
		}

		this.classes.add(className);
		return this;
	}

	public String getMethod()
	{
		return method;
	}

	public SirenAction setMethod(String method)
	{
		this.method = method;
		return this;
	}

	public String getHref()
	{
		return href;
	}

	public SirenAction setHref(String href)
	{
		this.href = href;
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public SirenAction setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public String getType()
	{
		return type;
	}

	public SirenAction setType(String type)
	{
		this.type = type;
		return this;
	}

	public List<SirenField> getFields()
	{
		return fields;
	}

	public SirenAction setFields(List<SirenField> fields)
	{
		this.fields = new ArrayList<SirenField>(fields);
		return this;
	}

	public SirenAction addField(SirenField field)
	{
		if (this.fields == null)
		{
			this.fields = new ArrayList<SirenField>();
		}

		this.fields.add(field);
		return this;
	}
}
