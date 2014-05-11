package com.strategicgains.hyperexpress.builder;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class TokenResolverTest
{
	private static final String[] URLS = {
		"/a/{a}/b/{b}",
		"/c/{c}/d/{d}/e/{e}",
		"{f}"
	};

	private TokenResolver resolver = new TokenResolver()
		.bind("a", "a")
		.bind("b", "b")
		.bind("c", "c")
		.bind("d", "d")
		.addTokenBinder(new TokenBinder()
		{
			@Override
			public void bind(Object object)
			{
				resolver.bind("e", String.valueOf(((Resolvable) object).e));
			}
		});

	@Test
	public void shouldHandlNullObject()
	{
		Collection<String> urls = resolver.resolve(Arrays.asList(URLS));
		assertNotNull(urls);
		verifyUrls(urls, "/a/a/b/b", "/c/c/d/d/e/{e}", "{f}");
	}

	@Test
	public void shouldResolveFromObject()
	{
		Resolvable r = new Resolvable();
		r.e = 42;
		Collection<String> urls = resolver.resolve(Arrays.asList(URLS), r);
		assertNotNull(urls);
		verifyUrls(urls, "/a/a/b/b", "/c/c/d/d/e/42", "{f}");
	}

	@Test
	public void shouldResolveAdditionalToken()
	{
		Resolvable r = new Resolvable();
		r.e = 13;
		resolver.bind("f", "toddf");
		Collection<String> urls = resolver.resolve(Arrays.asList(URLS), r);
		resolver.remove("f");
		assertNotNull(urls);
		verifyUrls(urls, "/a/a/b/b", "/c/c/d/d/e/13", "toddf");

		urls = resolver.resolve(Arrays.asList(URLS), r);
		assertNotNull(urls);
		verifyUrls(urls, "/a/a/b/b", "/c/c/d/d/e/13", "{f}");
	}

	private void verifyUrls(Collection<String> actual, String... expected)
    {
		assertEquals(expected.length, actual.size());
	    int i = 0;

		for (String url : actual)
		{
			assertEquals(expected[i++], url);
		}
    }

	private class Resolvable
	{
		public int e;
	}
}
