package com.strategicgains.hyperexpress.domain.hal;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import com.strategicgains.hyperexpress.ResourceFactoryStrategy;
import com.strategicgains.hyperexpress.domain.Resource;

public class HalResourceFactoryTest
{
	@Test
	public void shouldCreateBlogResourceWithDefaultFields()
	{
		HalResourceFactory factory = new HalResourceFactory();
		Blog b = new Blog();
		UUID blogId = UUID.randomUUID();
		UUID ownerId = UUID.randomUUID();
		b.setDescription("Blog Description");
		b.setId(blogId);
		b.setName("Blog Name");
		b.setOwnerId(ownerId);
		Resource r = factory.createResource(b);
		System.out.println(r.toString());
		assertEquals("Blog Description", r.getProperty("description"));
		assertEquals("Blog Name", r.getProperty("name"));
		assertEquals(blogId, r.getProperty("id"));
		assertEquals(ownerId, r.getProperty("ownerId"));
		assertNull(r.getProperty("somethingTransient"));
		assertNull(r.getProperty("somethingVolatile"));
		assertNull(r.getProperty("somethingStatic"));
		assertNull(r.getProperty("IGNORED"));
	}

	@Test
	public void shouldCreateBlogResourceWithAnnotatedFields()
	{
		ResourceFactoryStrategy factory = new HalResourceFactory()
			.includeAnnotations(Include.class)
			.excludeAnnotations(Exclude.class);
		Blog b = new Blog();
		UUID blogId = UUID.randomUUID();
		UUID ownerId = UUID.randomUUID();
		b.setDescription("Blog Description");
		b.setId(blogId);
		b.setName("Blog Name");
		b.setOwnerId(ownerId);
		Resource r = factory.createResource(b);
		System.out.println(r.toString());
		assertEquals("Blog Description", r.getProperty("description"));
		assertNull(r.getProperty("name"));
		assertEquals(blogId, r.getProperty("id"));
		assertEquals(ownerId, r.getProperty("ownerId"));
		assertNotNull(r.getProperty("somethingTransient"));
		assertNotNull(r.getProperty("somethingVolatile"));
		assertNotNull(r.getProperty("somethingStatic"));
		assertNotNull(r.getProperty("IGNORED"));
	}
}
