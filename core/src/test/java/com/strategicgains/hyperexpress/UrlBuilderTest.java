package com.strategicgains.hyperexpress;

import static org.junit.Assert.*;

import org.junit.Test;

public class UrlBuilderTest
{
	private static final String URL_PATTERN = "/{id}";
	private static final String URL_PATTERN2 = "/{rootId}/{secondaryId}/{id}";

	@Test
	public void shouldBuildSimpleUrl()
	{
		assertEquals("/todd:was,here", new UrlBuilder(URL_PATTERN)
			.param("id", "todd:was,here")
			.build());
	}

	@Test
	public void shouldBuildComplexUrl()
	{
		assertEquals("/something/else/12345", new UrlBuilder(URL_PATTERN2)
			.param("rootId", "something")
			.param("secondaryId", "else")
			.param("id", "12345")
			.build());	
	}

	@Test
	public void shouldBuildMultipleUrls()
	{
		UrlBuilder b = new UrlBuilder(URL_PATTERN2);
		assertEquals("/something/else/12345", b
			.param("rootId", "something")
			.param("secondaryId", "else")
			.param("id", "12345")
			.build());	

		assertEquals("/anything/maybe/54321", b
			.param("rootId", "anything")
			.param("secondaryId", "maybe")
			.param("id", "54321")
			.build());	

		assertEquals("/anything/wonderful/54321", b
			.param("secondaryId", "wonderful")
			.build());	
	}
}
