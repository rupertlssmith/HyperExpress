package com.strategicgains.hyperexpress.util.test;

import com.strategicgains.hyperexpress.util.Strings;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StringsTest {
    @Test
    public void shouldPluralize() {
        Assert.assertEquals("keys", Strings.pluralize("key"));
        assertEquals("words", Strings.pluralize("word"));
        assertEquals("properties", Strings.pluralize("property"));
        assertEquals("buses", Strings.pluralize("bus"));
        assertEquals("crosses", Strings.pluralize("cross"));
        assertEquals("lackeys", Strings.pluralize("lackey"));
        assertEquals("nouns", Strings.pluralize("noun"));
        assertEquals("knives", Strings.pluralize("knife"));
        assertEquals("children", Strings.pluralize("child"));
        assertEquals("people", Strings.pluralize("person"));
        assertEquals("feet", Strings.pluralize("foot"));
        assertEquals("women", Strings.pluralize("woman"));
        assertEquals("series", Strings.pluralize("series"));
        assertEquals("toys", Strings.pluralize("toy"));
        assertEquals("cookies", Strings.pluralize("cookie"));
        assertEquals("wolves", Strings.pluralize("wolf"));
        assertEquals("teeth", Strings.pluralize("tooth"));
        assertEquals("men", Strings.pluralize("man"));
        assertEquals("series", Strings.pluralize("series"));
        assertEquals("analyses", Strings.pluralize("analysis"));
        assertEquals("mice", Strings.pluralize("mouse"));
        assertEquals("titmice", Strings.pluralize("titmouse"));
        assertEquals("moose", Strings.pluralize("moose"));
        assertEquals("oxen", Strings.pluralize("ox"));
        assertEquals("foxes", Strings.pluralize("fox"));
        assertEquals("geese", Strings.pluralize("goose"));
    }
}
