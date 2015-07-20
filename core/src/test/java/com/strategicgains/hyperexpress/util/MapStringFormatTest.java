package com.strategicgains.hyperexpress.util;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class MapStringFormatTest {
    @Test
    public void shouldFormatWithDefaultDelimiters() {
        MapStringFormat formatter = new MapStringFormat();
        String template = "{last_name}, {first_name} {middle_initial}.";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("first_name", "Todd");
        parameters.put("middle_initial", "A");
        parameters.put("last_name", "Fredrich");

        String result = formatter.format(template, parameters);
        assertEquals("Fredrich, Todd A.", result);
    }

    @Test
    public void shouldFormatWithStringArray() {
        MapStringFormat formatter = new MapStringFormat();
        String template = "{last_name}, {first_name} {middle_initial}.";
        String result =
            formatter.format(template, "first_name", "Todd", "middle_initial", "A", "last_name", "Fredrich");
        assertEquals("Fredrich, Todd A.", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithUnbalancedParameters() {
        MapStringFormat formatter = new MapStringFormat();
        String template = "{last_name}, {first_name} {middle_initial}.";
        formatter.format(template, "first_name", "Todd", "middle_initial", "last_name", "Fredrich");
        fail("Shouldn't get here");
    }

    @Test
    public void shouldFormatWithSpecifiedDelimiters() {
        MapStringFormat formatter = new MapStringFormat("[", "]");
        String template = "[last_name], [first_name] [middle_initial].";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("first_name", "Todd");
        parameters.put("middle_initial", "A");
        parameters.put("last_name", "Fredrich");

        String result = formatter.format(template, parameters);
        assertEquals("Fredrich, Todd A.", result);
    }

    @Test
    public void shouldFormatWithStringDelimiters() {
        MapStringFormat formatter = new MapStringFormat("<start>", "<end>");
        String template = "<start>last_name<end>, <start>first_name<end> <start>middle_initial<end>.";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("first_name", "Todd");
        parameters.put("middle_initial", "A");
        parameters.put("last_name", "Fredrich");

        String result = formatter.format(template, parameters);
        assertEquals("Fredrich, Todd A.", result);
    }

    @Test
    public void shouldConvertNameValuePairsToMap() {
        Map<String, String> pairs = MapStringFormat.toMap("firstName", "Todd", "lastName", "Fredrich");
        assertNotNull(pairs);
        assertEquals(2, pairs.size());
        assertEquals("Todd", pairs.get("firstName"));
        assertEquals("Fredrich", pairs.get("lastName"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnUnmatchedPairs() {
        MapStringFormat.toMap("firstName", "Todd", "lastName");
        fail("Should throw IllegalArgumentException for unmatched pair");
    }

    @Test
    public void shouldHandleNull() {
        Map<String, String> pairs = MapStringFormat.toMap((String[]) null);
        assertNotNull(pairs);
        assertTrue(pairs.isEmpty());
    }
}
