package com.strategicgains.hyperexpress.builder;

import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.util.MapStringFormat;

public abstract class LinkBuilder
{
	private static final MapStringFormat formatter = new MapStringFormat();

	private static final String REL_TYPE = "rel";
	private static final String TITLE = "title";
	private static final String TYPE = "type";

	private String baseUrl;
	private String urlPattern;
	private String idParameterName;
	private Map<String, String> attributes = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();

	public LinkBuilder(String idParameterName)
	{
		super();
		this.idParameterName = idParameterName;
	}

	public LinkBuilder baseUrl(String url)
    {
    	this.baseUrl = url;
    	return this;
    }

	public LinkBuilder href(String pattern)
    {
    	this.urlPattern = pattern;
    	return this;
    }

	public LinkBuilder rel(String rel)
    {
    	return attribute(REL_TYPE, rel);
    }

	public LinkBuilder title(String title)
    {
    	return attribute(TITLE, title);
    }

	public LinkBuilder type(String type)
    {
    	return attribute(TYPE, type);
    }

	public LinkBuilder attribute(String name, String value)
    {
    	attributes.put(name, value);
    	return this;
    }
	
	public LinkBuilder urlParam(String name, String value)
	{
		parameters.put(name, value);
		return this;
	}
	
	protected LinkTemplate build(String id)
	{
		LinkTemplate l = new LinkTemplate(attributes.get(REL_TYPE), buildHref(id));
		return l;
	}

	private String buildHref(String id)
    {
		parameters.put(idParameterName, id.toString());
		String path = formatter.format(urlPattern, parameters);
		return (baseUrl == null ? path : baseUrl + path);
    }
}
