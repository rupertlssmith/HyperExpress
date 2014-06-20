package com.strategicgains.hyperexpress;

import static com.strategicgains.hyperexpress.RelTypes.NEXT;
import static com.strategicgains.hyperexpress.RelTypes.PREV;
import static com.strategicgains.hyperexpress.RelTypes.SELF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.After;
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
		HyperExpress.relationships()
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

	@After
	public void cleanup()
	{
		HyperExpress.clearTokenBindings();
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
			.bind("selfOffset", "40")
			.bind("selfLimit", "20");
		Resource r = HyperExpress.createCollectionResource(null, Blog.class, "*");
		assertNotNull(r.getLinks());
		assertEquals(1, r.getLinks().size());
		Link link = r.getLinks().iterator().next();
		assertEquals(SELF, link.getRel());
//		assertEquals("/blogs?limit=20&offset=40", link.getHref());
		HyperExpress.clearTokenBindings();
	}

	@Test
	public void shouldContainOptionalLinks()
	{
		HyperExpress
			.bind("nextOffset", "40")
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

	@Test
	public void shouldHandleCollection()
	{
		ArrayList<Blog> blogs = new ArrayList<>();
		Resource r = HyperExpress.createCollectionResource(blogs, Blog.class, "*");
		assertNotNull(r);
	}

	@Test
	public void shouldCreateEmptyResourceFromResource()
	{
		Resource r = HyperExpress.createResource(new AgnosticResource(), "*");
		assertEmptyResource(r);
	}

	@Test
	public void shouldCreateCollectionResourceFromResourceCollection()
	{
		Collection<Resource> resources = new ArrayList<Resource>();
		resources.add(new AgnosticResource());
		Resource r = HyperExpress.createCollectionResource(resources, Blog.class, "blogs", "*");
		assertNotNull(r);
		assertTrue(r.hasNamespaces());
		assertEquals(2, r.getNamespaces().size());
		assertTrue(r.hasLinks());
		assertEquals(1, r.getLinks().size());
		assertEquals("self", r.getLinks().get(0).getRel());
		assertEquals("/blogs", r.getLinks().get(0).getHref());
		assertFalse(r.hasProperties());
		assertTrue(r.hasResources());
		assertEquals(1, r.getResources("blogs").size());
		assertEmptyResource(r.getResources("blogs").get(0));
	}

	private void assertEmptyResource(Resource r)
	{
		assertNotNull(r);
		assertTrue(r.hasNamespaces());
		assertEquals(2, r.getNamespaces().size());
		assertFalse(r.hasLinks());
		assertFalse(r.hasProperties());
		assertFalse(r.hasResources());
	}
}
