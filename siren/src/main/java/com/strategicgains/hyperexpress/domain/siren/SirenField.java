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

/**
 * @author toddf
 * @since Sep 12, 2014
 */
public class SirenField
{
	private String name;
	private String type;
	private String value;
	private String title;

	public String getName()
	{
		return name;
	}

	public SirenField setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getType()
	{
		return type;
	}

	public SirenField setType(String type)
	{
		this.type = type;
		return this;
	}

	public String getValue()
	{
		return value;
	}

	public SirenField setValue(String value)
	{
		this.value = value;
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public SirenField setTitle(String title)
	{
		this.title = title;
		return this;
	}
}
