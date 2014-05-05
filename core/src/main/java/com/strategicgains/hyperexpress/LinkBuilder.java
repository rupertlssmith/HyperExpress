package com.strategicgains.hyperexpress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkImpl;
import com.strategicgains.hyperexpress.util.MapStringFormat;

/**
 * 
 * @author toddf
 * @since May 5, 2014
 */
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

	/**
	 * A link builder requires, at the very least, a URL
	 * 
	 * @param urlPattern
	 */
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
		if (value == null)
		{
			attributes.remove(name);
		}
		else
		{
			attributes.put(name, value);
		}

    	return this;
    }
	
	public LinkBuilder urlParam(String name, String value)
	{
		parameters.put(name, value);
		return this;
	}

	/**
	 * 
	 * @return a LinkDefinition instance
	 * @throws LinkBuilderException if the LinkBuilder is in a state to build an invalid LinkDefintion.
	 */
	public Link build()
	{
		Link link = new LinkImpl(attributes.get(REL_TYPE), buildHref());

		for (Entry<String, String> entry : attributes.entrySet())
		{
			if (!entry.getKey().equalsIgnoreCase(REL_TYPE))
			{
				link.set(entry.getKey(), entry.getValue());
			}
		}

		return link;
	}

	public Collection<Link> build(String idParameterName, String... ids)
	{
		return build(idParameterName, Arrays.asList(ids));
	}

	public Collection<Link> build(String idParameterName, Collection<String> ids)
	{
		if (ids == null) return null;

		Collection<Link> definitions = new ArrayList<Link>(ids.size());

		for (String id : ids)
		{
			parameters.put(idParameterName, id);
			definitions.add(build());
		}

		parameters.remove(idParameterName);
		return definitions;
	}

	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(this.getClass().getSimpleName());
		s.append("{");
		boolean isFirst = true;

		for (Entry<String, String> entry : attributes.entrySet())
		{
			if (!isFirst)
			{
				s.append(", ");
			}
			else
			{
				isFirst = false;
			}

			s.append(entry.getKey());
			s.append("=");
			s.append(entry.getValue());
		}

		s.append("}");
		return s.toString();
	}

	private String buildHref()
    {
		String path = formatter.format(urlPattern, parameters);
		return (baseUrl == null ? path : baseUrl + path);
    }
}
