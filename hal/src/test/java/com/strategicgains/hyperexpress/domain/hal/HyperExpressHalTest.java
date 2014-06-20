package com.strategicgains.hyperexpress.domain.hal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.Resource;

public class HyperExpressHalTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		HyperExpress.registerResourceFactoryStrategy(new HalResourceFactory(), "*");
	}

	@Test
	public void shouldCreateResourceFromNull()
	{
		Resource r = HyperExpress.createResource(null, "*");
		assertNotNull(r);
		assertFalse(r.hasLinks());
		assertFalse(r.hasNamespaces());
		assertFalse(r.hasProperties());
		assertFalse(r.hasResources());
	}

	@Test
	public void shouldCreateCollectionResourceFromNullWithRel()
	{
		Resource r = HyperExpress.createCollectionResource(null, Blog.class, "blogs", "*");
		assertNotNull(r);
		assertFalse(r.hasLinks());
		assertFalse(r.hasNamespaces());
		assertFalse(r.hasProperties());
		assertTrue(r.hasResources());
		assertTrue(r.getResources("blogs").isEmpty());
	}

	@Test
	public void shouldCreateCollectionResourceFromNullWithoutRel()
	{
		Resource r = HyperExpress.createCollectionResource(null, Blog.class, "*");
		assertNotNull(r);
		assertFalse(r.hasLinks());
		assertFalse(r.hasNamespaces());
		assertFalse(r.hasProperties());
		assertTrue(r.hasResources());
		assertTrue(r.getResources("blogs").isEmpty());
	}
}
