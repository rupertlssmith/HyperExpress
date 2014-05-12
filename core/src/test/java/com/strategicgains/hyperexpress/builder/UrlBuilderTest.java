package com.strategicgains.hyperexpress.builder;

import static org.junit.Assert.*;

import org.junit.Test;

import com.strategicgains.hyperexpress.builder.UrlBuilder;

public class UrlBuilderTest
{
	private static final String URL_PATTERN = "/{id}";
	private static final String URL_PATTERN2 = "/{rootId}/{secondaryId}/{id}";

	@Test
	public void shouldBuildSimpleUrl()
	{
		assertEquals("/todd:was,here", new UrlBuilder(URL_PATTERN)
			.bind("id", "todd:was,here")
			.build());
	}

	@Test
	public void shouldBuildComplexUrl()
	{
		assertEquals("/something/else/12345", new UrlBuilder(URL_PATTERN2)
			.bind("rootId", "something")
			.bind("secondaryId", "else")
			.bind("id", "12345")
			.build());	
	}

	@Test
	public void shouldBuildMultipleUrls()
	{
		UrlBuilder b = new UrlBuilder(URL_PATTERN2);
		assertEquals("/something/else/12345", b
			.bind("rootId", "something")
			.bind("secondaryId", "else")
			.bind("id", "12345")
			.build());	

		assertEquals("/anything/maybe/54321", b
			.bind("rootId", "anything")
			.bind("secondaryId", "maybe")
			.bind("id", "54321")
			.build());	

		assertEquals("/anything/wonderful/54321", b
			.bind("secondaryId", "wonderful")
			.build());	
	}

	@Test
	public void shouldExcludeQueryString()
	{
		assertEquals("/something/else/12345", new UrlBuilder(URL_PATTERN2)
			.withQuery("limit={selfLimit}")
			.withQuery("offset={selfOffset}")
			.bind("rootId", "something")
			.bind("secondaryId", "else")
			.bind("id", "12345")
			.build());	
	}

	@Test
	public void shouldIncludeEntireQueryString()
	{
		assertEquals("/something/else/12345?limit=20&offset=40", new UrlBuilder(URL_PATTERN2)
			.withQuery("limit={selfLimit}")
			.withQuery("offset={selfOffset}")
			.bind("rootId", "something")
			.bind("secondaryId", "else")
			.bind("id", "12345")
			.bind("selfLimit", "20")
			.bind("selfOffset", "40")
			.build());	
	}

	@Test
	public void shouldIncludeQueryString()
	{
		assertEquals("/something/else/12345?offset=40", new UrlBuilder(URL_PATTERN2)
			.withQuery("limit={selfLimit}")
			.withQuery("offset={selfOffset}")
			.bind("rootId", "something")
			.bind("secondaryId", "else")
			.bind("id", "12345")
			.bind("selfOffset", "40")
			.build());	
	}

	@Test
	public void shouldUseExternalTokenResolver()
	{
		assertEquals("/something/else/12345?limit=20&offset=40", new UrlBuilder(URL_PATTERN2)
			.withQuery("limit={selfLimit}")
			.withQuery("offset={selfOffset}")
			.tokenResolver(new TokenResolver()
				.bind("rootId", "something")
				.bind("secondaryId", "else")
				.bind("id", "12345")
				.bind("selfLimit", "20")
				.bind("selfOffset", "40"))
			.build());	
	}
}
