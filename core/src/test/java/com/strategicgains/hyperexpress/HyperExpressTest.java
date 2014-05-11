package com.strategicgains.hyperexpress;

import static com.strategicgains.hyperexpress.RelTypes.NEXT;
import static com.strategicgains.hyperexpress.RelTypes.PREV;
import static com.strategicgains.hyperexpress.RelTypes.SELF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.strategicgains.hyperexpress.domain.Blog;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Namespace;
import com.strategicgains.hyperexpress.domain.Resource;

public class HyperExpressTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		HyperExpress.registerResourceFactoryStrategy(new NullResourceFactoryStrategy(), "*");
		HyperExpress.defineRelationships()
		.addNamespaces(
			new Namespace("ea", "http://namespaces.example.com/{rel}"),
			new Namespace("blog", "http://namespaces.example.com/{rel}")
		)

		.forCollectionOf(Blog.class)
			.rel(SELF, "/blogs")
//				.query("limit={selfLimit}")
//				.query("offset={selfOffset}")
			.rel(NEXT, "/blogs?limit={nextLimit}&offset={nextOffset}").optional()
			.rel(PREV, "/blogs?limit={prevLimit}&offset={prevOffset}").optional();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Test
	public void shouldIgnoreOptionalLinks()
	{
		Resource r = HyperExpress.createCollectionResource(Collections.EMPTY_LIST, Blog.class, "*");
		assertNotNull(r.getLinks());
		assertEquals(1, r.getLinks().size());
		Link link = r.getLinks().iterator().next();
		assertEquals(SELF, link.getRel());
		assertEquals("/blogs", link.getHref());
	}

	@Test
	public void shouldContainOptionalQueryStringParameters()
	{
		HyperExpress
			.bindToken("selfOffset", "40")
			.bind("selfLimit", "20");
		Resource r = HyperExpress.createCollectionResource(null, Blog.class, "*");
		assertNotNull(r.getLinks());
		assertEquals(1, r.getLinks().size());
		Link link = r.getLinks().iterator().next();
		assertEquals(SELF, link.getRel());
//		assertEquals("/blogs?limit=20&offset=40", link.getHref());
	}

	@Test
	public void shouldContainOptionalLinks()
	{
		HyperExpress
			.bindToken("nextOffset", "40")
			.bind("nextLimit", "20");
		Resource r = HyperExpress.createCollectionResource(null, Blog.class, "*");
		assertNotNull(r.getLinks());
		assertEquals(2, r.getLinks().size());
		Iterator<Link> iter = r.getLinks().iterator();

		while(iter.hasNext())
		{
			Link link = iter.next();

			switch (link.getRel())
            {
				case SELF:
					assertEquals("/blogs", link.getHref());
					break;

				case NEXT:
					assertEquals("/blogs?limit=20&offset=40", link.getHref());
					break;

				default:
					fail("Unexpected 'rel' " + link.getRel());
					break;
			}
		}
	}
}
