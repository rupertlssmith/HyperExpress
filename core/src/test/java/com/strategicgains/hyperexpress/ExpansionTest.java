package com.strategicgains.hyperexpress;

import com.strategicgains.hyperexpress.expand.Expansion;

import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class ExpansionTest {
    @Test
    public void shouldReturnFalseOnEmptyRels() {
        Expansion expansion = new Expansion("*");
        assertFalse(expansion.contains("anything"));
    }

}
