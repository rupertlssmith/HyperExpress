/*
 * Copyright 2014, eCollege, Inc.  All rights reserved.
 */
package com.strategicgains.hyperexpress.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author fredta2
 * @since May 7, 2014
 */
public class PluralizerTest
{
	@Test
	public void shouldPluralize()
	{
		assertEquals("keys", Pluralizer.pluralize("key"));
		assertEquals("words", Pluralizer.pluralize("word"));
		assertEquals("properties", Pluralizer.pluralize("property"));
		assertEquals("buses", Pluralizer.pluralize("bus"));
		assertEquals("crosses", Pluralizer.pluralize("cross"));
		assertEquals("lackeys", Pluralizer.pluralize("lackey"));
		assertEquals("nouns", Pluralizer.pluralize("noun"));
		assertEquals("knives", Pluralizer.pluralize("knife"));
		assertEquals("children", Pluralizer.pluralize("child"));
		assertEquals("people", Pluralizer.pluralize("person"));
		assertEquals("feet", Pluralizer.pluralize("foot"));
		assertEquals("women", Pluralizer.pluralize("woman"));
		assertEquals("series", Pluralizer.pluralize("series"));
		assertEquals("toys", Pluralizer.pluralize("toy"));
		assertEquals("cookies", Pluralizer.pluralize("cookie"));
		assertEquals("wolves", Pluralizer.pluralize("wolf"));
		assertEquals("teeth", Pluralizer.pluralize("tooth"));
		assertEquals("men", Pluralizer.pluralize("man"));
		assertEquals("series", Pluralizer.pluralize("series"));
		assertEquals("analyses", Pluralizer.pluralize("analysis"));
		assertEquals("mice", Pluralizer.pluralize("mouse"));
		assertEquals("titmice", Pluralizer.pluralize("titmouse"));
		assertEquals("moose", Pluralizer.pluralize("moose"));
		assertEquals("oxen", Pluralizer.pluralize("ox"));
		assertEquals("foxes", Pluralizer.pluralize("fox"));
		assertEquals("geese", Pluralizer.pluralize("goose"));
	}
}
