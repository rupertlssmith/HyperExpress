package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.strategicgains.hyperexpress.util.MapStringFormat;

public class LinkBuilder
{
	private static final MapStringFormat formatter = new MapStringFormat();

	private static final String REL_TYPE = "rel";
	private static final String TITLE = "title";
	private static final String TYPE = "type";

	private String baseUrl;
	private String urlPattern;
	private Map<String, String> attributes = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();

	public LinkBuilder(String urlPattern)
	{
		super();
		this.urlPattern = urlPattern;
	}

	public LinkBuilder baseUrl(String url)
    {
    	this.baseUrl = url;
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

	public Collection<LinkTemplate> build(String idParameterName, String... ids)
	{
		return build(idParameterName, Arrays.asList(ids));
	}

	public Collection<LinkTemplate> build(String idParameterName, Collection<String> ids)
	{
		if (ids == null) return null;

		Collection<LinkTemplate> r = new ArrayList<LinkTemplate>(ids.size());

		for (String id : ids)
		{
			r.add(build(idParameterName, id));
		}

		return r;
	}
	
	protected LinkTemplate build(String idParameterName, String id)
	{
		LinkTemplate l = new LinkTemplate(attributes.get(REL_TYPE), buildHref(idParameterName, id));
		return l;
	}

	private String buildHref(String idParameterName, String id)
    {
		parameters.put(idParameterName, id.toString());
		String path = formatter.format(urlPattern, parameters);
		return (baseUrl == null ? path : baseUrl + path);
    }
}
