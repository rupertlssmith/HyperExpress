package com.strategicgains.hyperexpress.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkDefinition;

/**
 * Build LinkDefinition instances from a URL pattern, binding URL tokens to
 * actual values.
 * 
 * @author toddf
 * @since May 5, 2014
 */
public class LinkBuilder
{
	private static final String REL_TYPE = "rel";
	private static final String TITLE = "title";
	private static final String TYPE = "type";

	private UrlBuilder urlBuilder;
	private Map<String, String> attributes = new HashMap<String, String>();

	/**
	 * A link builder requires, at the very least, a URL
	 * 
	 * @param urlPattern
	 */
	public LinkBuilder(String urlPattern)
	{
		super();
		urlBuilder = new UrlBuilder(urlPattern);
	}

	public LinkBuilder baseUrl(String url)
	{
		urlBuilder.baseUrl(url);
		return this;
	}

	public LinkBuilder addQuery(String query)
	{
		urlBuilder.addQuery(query);
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

	public LinkBuilder bindToken(String token, String value)
	{
		urlBuilder.bindToken(token, value);
		return this;
	}

	/**
	 * 
	 * @return a LinkDefinition instance
	 * @throws LinkBuilderException if the LinkBuilder is in a state to build an invalid
	 * LinkDefintion.
	 */
	public Link build()
	{
		return build(urlBuilder.build());
	}

	public Link build(String url)
	{
		Link link = new LinkDefinition(attributes.get(REL_TYPE), url);

		for (Entry<String, String> entry : attributes.entrySet())
		{
			if (!entry.getKey().equalsIgnoreCase(REL_TYPE))
			{
				link.set(entry.getKey(), entry.getValue());
			}
		}

		return link;
	}

	public Collection<Link> build(String tokenName, String... values)
	{
		return build(tokenName, Arrays.asList(values));
	}

	public Collection<Link> build(String tokenName, Collection<String> values)
	{
		if (values == null) return null;

		Collection<String> urls = urlBuilder.build(tokenName, values);
		Collection<Link> links = new ArrayList<Link>(urls.size());

		for (String url : urls)
		{
			links.add(build(url));
		}

		return links;
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
}
